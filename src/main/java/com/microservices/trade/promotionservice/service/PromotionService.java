package com.microservices.trade.promotionservice.service;

import com.microservices.trade.promotionservice.dao.PromotionDAO;
import com.microservices.trade.promotionservice.dao.VoucherDAO;
import com.microservices.trade.promotionservice.domain.DO.PromotionDO;
import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:54.
 * Desc:
 * ==================================
 */
@Slf4j
@Component
public class PromotionService {
    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private VoucherDAO voucherDAO;

    public PromotionVO savePromotionVO(PromotionVO promotionVO){
        if (promotionVO == null){
            return null;
        }
        PromotionDO promotionDO = new PromotionDO();
    }
}
