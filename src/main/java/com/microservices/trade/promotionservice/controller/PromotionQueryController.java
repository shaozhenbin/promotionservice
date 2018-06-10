package com.microservices.trade.promotionservice.controller;

import com.microservices.trade.promotionservice.domain.VO.PromotionVO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.service.PromotionService;
import com.microservices.trade.promotionservice.service.imp.VoucherServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static com.microservices.trade.promotionservice.configuration.WebConfiguration.BASIC_URL;
import static com.microservices.trade.promotionservice.configuration.WebConfiguration.QUERY_INF;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 12:02.
 * Desc:
 * ==================================
 */
@Slf4j
@RestController
@RequestMapping(BASIC_URL + QUERY_INF)
public class PromotionQueryController {

    @Resource
    private PromotionService promotionService;

    @Resource
    private VoucherServiceImp voucherService;

    @RequestMapping("/query/voucher/list")
    public List<VoucherVO> queryAllVoucherByUserId(@RequestParam Long userId, @RequestParam
            Boolean available){
        log.info("Query all voucher by userId: {}, available: {}", userId, available);
        List<VoucherVO> voucherVOS = voucherService.queryVoucherByUserId(userId,available);
        log.info("Query all voucher by userId: {}, available: {}, result:{}", userId, available, voucherVOS);
        return voucherVOS;
    }

    @RequestMapping("/query/promotion")
    public List<PromotionVO> queryAllPromotion(@RequestParam Long userId, @RequestParam Long
            productId){
        log.info("Query all promotion with userId: {}, productId: {}", userId, productId);
        List<PromotionVO> promotionVOS = promotionService.queryAvailablePromotions(userId,
                productId);
        log.info("Query all promotion with userId: {}, productId: {}, result: {}", userId,
                productId, promotionVOS.toString());
        return promotionVOS;
    }
}
