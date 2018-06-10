package com.microservices.trade.promotionservice.controller;

import com.microservices.trade.promotionservice.domain.DO.PromotionUserDO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.service.PromotionService;
import com.microservices.trade.promotionservice.service.imp.VoucherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.microservices.trade.promotionservice.configuration.WebConfiguration.BASIC_URL;
import static com.microservices.trade.promotionservice.configuration.WebConfiguration.UPDATE_PROMOTION;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 12:02.
 * Desc:
 * ==================================
 */

@Slf4j
@RestController
@RequestMapping(BASIC_URL + UPDATE_PROMOTION)
public class PromotionModifyController {

    @Resource
    private PromotionService promotionService;

    @Resource
    private VoucherServiceImpl voucherService;

    @ResponseBody
    @RequestMapping("/voucher/status")
    public VoucherVO modifyVoucher(@RequestParam Long userId, @RequestParam Long voucherId,
                                   @RequestParam Integer status) {
        log.info("modify voucher status,userId: {}, voucherId: {}, status: {}", userId, voucherId,
                status);
        VoucherVO voucherVO = voucherService.modifyVoucher(userId, voucherId, status);
        log.info("modify voucher status,userId: {}, voucherId: {}, status: {},result: {}", userId,
                voucherId, status, voucherVO);
        return voucherVO;
    }


    @ResponseBody
    @RequestMapping("/promotion/status")
    public Boolean modifyPromotion(@RequestParam Long userId, @RequestParam Long promotionMapId,
                                   @RequestParam Integer status) {
        log.info("modify promotion,userId: {}, promotionMapId: {}, status: {}", userId,
                promotionMapId, status);
        Boolean result = promotionService.modifyPromotion(userId, promotionMapId, status);
        log.info("modify promotion,userId: {}, promotionMapId: {}, status: {}, result: {}", userId,
                promotionMapId, status, result);
        return result;
    }

    @ResponseBody
    @RequestMapping("/promotion/use")
    public Long usePromotion(@RequestParam Long userId, @RequestParam Long promotionId) {
        log.info("verify promotion,userId: {}, promotionId: {}", userId, promotionId);
        PromotionUserDO promotionUserDO = promotionService.usePromotion(userId, promotionId);
        log.info("verify promotion,userId: {}, promotionId: {},result: {}", userId,
                promotionId, promotionUserDO);
        return promotionUserDO.getId();
    }


}
