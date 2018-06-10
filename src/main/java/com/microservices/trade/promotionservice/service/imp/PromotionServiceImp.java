package com.microservices.trade.promotionservice.service.imp;

import com.microservices.trade.promotionservice.dao.PromotionDAO;
import com.microservices.trade.promotionservice.dao.PromotionUserDAO;
import com.microservices.trade.promotionservice.domain.DO.PromotionDO;
import com.microservices.trade.promotionservice.domain.DO.PromotionUserDO;
import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import com.microservices.trade.promotionservice.domain.module.PromotionLimitTypeEnum;
import com.microservices.trade.promotionservice.domain.module.PromotionStatusEnum;
import com.microservices.trade.promotionservice.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:54.
 * Desc:
 * ==================================
 */
@Slf4j
@Component
public class PromotionServiceImp implements PromotionService {
    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private PromotionUserDAO promotionUserDAO;

    @Override
    public PromotionVO savePromotion(PromotionVO promotionVO){
        if (promotionVO == null){
            return null;
        }
        PromotionDO promotionDO = new PromotionDO();
        BeanUtils.copyProperties(promotionVO, promotionDO);
        promotionDO = promotionDAO.save(promotionDO);
        BeanUtils.copyProperties(promotionDO, promotionVO);
        log.info("PromotionService save Promotion: {}",promotionVO);
        return promotionVO;
    }

    @Override
    public List<PromotionVO> queryAvailablePromotions(Long userId, Long productId, Long amount) {
        if (userId==null ||productId == null||amount ==null){
            return null;
        }
        List<PromotionDO> promotionDOS = promotionDAO.findAllByProductIdAndLimitMoneyLessThanEqual
                (productId, amount);
        log.info("查询到相关优惠, userId:{}, productId: {}, result: {}", userId, productId, promotionDOS
                .toString());

        Long timestamp = getCorrectDayTimeStamp();
        //过滤不可用优惠
        List<PromotionVO> promotionVOS = new ArrayList<>();
        promotionDOS.stream().forEach(promotionDO -> {
            PromotionLimitTypeEnum typeEnum = PromotionLimitTypeEnum.getPromotionLimitTypeByValue
                    (promotionDO.getLimitType());
            PromotionVO promotionVO;
            List<PromotionUserDO> promotionUserDOS;
            switch (typeEnum){
                case free:
                    promotionVO = new PromotionVO();
                    BeanUtils.copyProperties(promotionDO, promotionVO);
                    promotionVOS.add(promotionVO);
                    break;
                case limitAllTime:
                    promotionUserDOS = promotionUserDAO
                            .findAllByUserIdAndPromotionIdAndPromotionStatus(userId, promotionDO
                                    .getId(), PromotionStatusEnum.used.getValue());
                    if(promotionUserDOS.size() < promotionDO.getLimitCount()) {
                        promotionVO = new PromotionVO();
                        BeanUtils.copyProperties(promotionDO,promotionVO);
                        promotionVOS.add(promotionVO);
                    }else{
                        log.info("优惠限制使用 {} 次, userId:{} promotionDO: {}",promotionDO
                                .getLimitCount(), userId, promotionDO.toString());
                    }
                    break;
                case limitByDay:
                    promotionUserDOS = promotionUserDAO
                            .findAllByUserIdAndPromotionIdAndUseTimeStampGreaterThanEqualAndPromotionStatus
                                    (userId, promotionDO.getId(), timestamp, PromotionStatusEnum.used.getValue());
                    if(promotionUserDOS.size() < promotionDO.getLimitCount()) {
                        promotionVO = new PromotionVO();
                        BeanUtils.copyProperties(promotionDO, promotionVO);
                        promotionVOS.add(promotionVO);
                    } else {
                        log.info("优惠限制每天使用 {} 次, userId:{} promotionDO: {}",promotionDO
                                .getLimitCount(), userId, promotionDO.toString());
                    }
                    break;
                default:
                    log.error("未知限制类型: {}", typeEnum);
                    break;
            }
        });
        log.info("queryAvailablePromotions userId:{}, productId:{},get Promotion Number:{} And " +
                "result:{}", userId, productId, promotionDOS.size(), promotionVOS.toString());
        return promotionVOS;
    }

    @Override
    public Boolean modifyPromotion(Long userId, Long promotionMapId, Integer status) {
        if(userId==null||promotionMapId==null||status==null){
            return false;
        }

        Optional<PromotionUserDO> promotionUserDOOptional = promotionUserDAO.findById(promotionMapId);
        if(!promotionUserDOOptional.isPresent()){
            return false;
        }
        PromotionUserDO promotionUserDO = promotionUserDOOptional.get();
        if(promotionUserDO.getPromotionStatus() == status){
            return true;
        }
        //update
        Long updateCount = promotionUserDAO.modifyStatus(promotionMapId, userId, status);
        if (updateCount != 1) {
            log.error("Modify Promotion Status Error update Count: {}, userId: {}, " +
                    "promotionMapId: {}", updateCount, userId, promotionMapId);
            return false;
        }
        return true;
    }

    @Override
    public PromotionUserDO verifyPromotion(Long userId, Long promotionId) {
        if(userId==null||promotionId == null){
            return null;
        }
        Optional<PromotionDO> promotionDO =promotionDAO.findById(promotionId);
        if(!promotionDO.isPresent()){
            return null;
        }

        PromotionUserDO promotionUserDO = buildPromotionDO(userId,promotionId);
        promotionUserDO = promotionUserDAO.save(promotionUserDO);
        return promotionUserDO;
    }

    private PromotionUserDO buildPromotionDO(Long userId, Long promotionId){
        PromotionUserDO promotionUserDO = new PromotionUserDO();
        promotionUserDO.setUserId(userId);
        promotionUserDO.setPromotionId(promotionId);
        promotionUserDO.setPromotionStatus(PromotionStatusEnum.used.getValue());
        promotionUserDO.setUseTimeStamp(new Date().getTime());
        return promotionUserDO;
    }

    private Long getCorrectDayTimeStamp(){
        //当天时间戳
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }
}
