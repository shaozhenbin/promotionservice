package com.microservices.trade.promotionservice.controller;

import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.microservices.trade.promotionservice.configuration.WebConfiguration.BASIC_URL;
import static com.microservices.trade.promotionservice.configuration.WebConfiguration.UPDATE_PROMOTION;

/**
 * Created by Space
 * Date: 2018/6/9 0009.
 * Time: 19:53.
 * Desc:
 * ==================================
 */
@Slf4j
@RestController
@RequestMapping(BASIC_URL+UPDATE_PROMOTION)
public class PromotionCreateController {

    @Resource
    private PromotionService promotionService;

    @ResponseBody
    @RequestMapping(value = "/create/promotion", method = RequestMethod.POST)
    public Long CreatePromotion(@RequestBody PromotionVO promotionVO){
        log.info("Create promotion:{}",promotionVO);
        Long promotionId = promotionService.savePromotion(promotionVO);
        return promotionId;
    }

    @ResponseBody
    @RequestMapping(value = "/create/voucher", method = RequestMethod.POST)
    public Long CreateVoucher(@RequestBody VoucherVO voucherVO){
        Long vourcherId = promotionService.saveVouchar(voucherVO);
        return vourcherId;
    }
}