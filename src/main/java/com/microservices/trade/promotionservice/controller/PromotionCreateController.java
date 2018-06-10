package com.microservices.trade.promotionservice.controller;

import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.service.PromotionService;
import com.microservices.trade.promotionservice.service.VoucherService;

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

    @Resource
    private VoucherService voucherService;

    @ResponseBody
    @RequestMapping(value = "/create/promotion", method = RequestMethod.POST)
    public Long createPromotion(@RequestBody PromotionVO promotionVO){
        log.info("Create promotion:{}", promotionVO);
        promotionVO = promotionService.savePromotion(promotionVO);
        log.info("Create promotion result:{}", promotionVO);
        return promotionVO.getId();
    }

    @ResponseBody
    @RequestMapping(value = "/create/voucher", method = RequestMethod.POST)
    public Long createVoucher(@RequestBody VoucherVO voucherVO){
        log.info("Create voucher:{}", voucherVO);
        voucherVO = voucherService.saveVoucher(voucherVO);
        log.info("Create voucher result:{}", voucherVO);
        return voucherVO.getId();
    }

    @ResponseBody
    @RequestMapping(value = "/offer/voucher")
    public Long offerVoucher(@RequestParam Long userId, Long voucherId){
        log.info("offer userId: {}, voucherId: {}", userId, voucherId);
        Long realId  = voucherService.offerVoucher(userId,voucherId);
        log.info("offer userId: {}, voucherId: {}, resultId ", userId, voucherId,realId);
        return realId;
    }
}