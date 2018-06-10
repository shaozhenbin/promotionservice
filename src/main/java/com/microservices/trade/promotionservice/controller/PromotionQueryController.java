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
            Boolean available) {
        log.info("Query all voucher by userId: {}, available: {}", userId, available);
        List<VoucherVO> voucherVOS = voucherService.queryVoucherByUserId(userId, available);
        log.info("Query all voucher by userId: {}, available: {}, result:{}", userId, available, voucherVOS);
        return voucherVOS;
    }

    @RequestMapping("/query/promotion")
    public List<PromotionVO> queryAllPromotion(@RequestParam Long userId, @RequestParam Long
            productId, @RequestParam Long amount) {
        log.info("Query all promotion with userId: {}, productId: {}, amount: {}", userId,
                productId,amount);
        List<PromotionVO> promotionVOS = promotionService.queryAvailablePromotions(userId,
                productId, amount);
        log.info("Query all promotion with userId: {}, productId: {}, amount:{}, result: {}",
                userId, productId, amount, promotionVOS.toString());
        return promotionVOS;
    }

    @RequestMapping("/verify/promotion")
    public PromotionVO verifyPromotion(@RequestParam Long userId, @RequestParam Long promotionId,
                                       @RequestParam Long productId, @RequestParam Long amount) {
        log.info("verify promotion with userId: {}, productId: {}, promotionId: {}, amount: {}",
                userId, productId, promotionId, amount);
        PromotionVO promotionVO = promotionService.verifyPromotion(userId, productId,
                promotionId, amount);
        log.info("verify promotion with userId: {}, productId: {}, promotionId: {}, amount: {}," +
                        "result: {}", userId, productId, promotionId, amount, promotionVO);
        return promotionVO;
    }

    @RequestMapping("/verify/voucher")
    public VoucherVO verifyVoucher(@RequestParam Long userId, @RequestParam Long voucherMapId,
                                     @RequestParam Long amount) {
        log.info("verify voucher with userId: {}, voucherMapId: {}, amount: {}",
                userId, voucherMapId, amount);
        VoucherVO voucherVO = voucherService.verifyVoucher(userId, voucherMapId, amount);
        log.info("verify voucher with userId: {}, voucherMapId: {}, amount: {}, result: {}",
                userId, voucherMapId, amount, voucherVO);
        return voucherVO;
    }
}
