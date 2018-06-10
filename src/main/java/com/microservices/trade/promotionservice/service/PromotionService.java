package com.microservices.trade.promotionservice.service;

import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 12:22.
 * Desc:
 * ==================================
 */

@Component
public interface PromotionService {
    PromotionVO savePromotion(PromotionVO promotionVO);
    List<PromotionVO> queryAvailablePromotions(Long userId, Long productId);
}
