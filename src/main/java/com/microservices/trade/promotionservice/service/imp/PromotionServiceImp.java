package com.microservices.trade.promotionservice.service.imp;

import com.microservices.trade.promotionservice.dao.PromotionDAO;
import com.microservices.trade.promotionservice.dao.VoucherDAO;
import com.microservices.trade.promotionservice.domain.DO.PromotionDO;
import com.microservices.trade.promotionservice.domain.DO.VoucherDO;
import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
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
    private VoucherDAO voucherDAO;

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
    public List<PromotionVO> queryAvailablePromotions(Long userId, Long productId) {
        return null;
    }
}
