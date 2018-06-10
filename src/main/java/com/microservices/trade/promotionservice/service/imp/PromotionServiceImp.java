package com.microservices.trade.promotionservice.service.imp;

import com.microservices.trade.promotionservice.dao.PromotionDAO;
import com.microservices.trade.promotionservice.dao.PromotionUserDAO;
import com.microservices.trade.promotionservice.dao.VoucherDAO;
import com.microservices.trade.promotionservice.domain.DO.PromotionDO;
import com.microservices.trade.promotionservice.domain.DO.PromotionUserDO;
import com.microservices.trade.promotionservice.domain.DO.VoucherDO;
import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.domain.module.PromotionLimitTypeEnum;
import com.microservices.trade.promotionservice.domain.module.PromotionStatusEnum;
import com.microservices.trade.promotionservice.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:54.
 * Desc:
 * ==================================
 */
@Slf4j
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

        //当前时间戳
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date day = calendar.getTime();
        Long timestamp = day.getTime();

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
}
