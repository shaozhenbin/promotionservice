package com.microservices.trade.promotionservice.controller;

import com.microservices.trade.promotionservice.domain.DO.VoucherDO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.service.PromotionService;
import com.microservices.trade.promotionservice.service.imp.VoucherServiceImp;
import common.ObjectResponse;
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
@RequestMapping(BASIC_URL+UPDATE_PROMOTION)
public class PromotionModifyController {

    @Resource
    private PromotionService promotionService;

    @Resource
    private VoucherServiceImp voucherService;

    @ResponseBody
    @RequestMapping("/voucher/status")
    public VoucherVO modifyVoucher(@RequestParam Long userId,@RequestParam Long voucherId,
                                   @RequestParam Integer status){
        log.info("modify voucher status,userId: {}, voucherId: {}, status: {}",userId, voucherId,
                status);
        VoucherVO voucherVO = voucherService.modifyVoucher(userId,voucherId,status);
        log.info("modify voucher status,userId: {}, voucherId: {}, status: {},result: {}",userId,
                voucherId, status, voucherVO);
        return voucherVO;
    }


    @ResponseBody
    @RequestMapping("/promotion/status")
    public Long modifyPromotion(@RequestParam Long userId, @RequestParam Long promotionMapId,
                                @RequestParam Integer status){
        log.info("modify promotion,userId: {}, promotionId: {}, status: {}",userId,
                promotionMapId, status);
        VoucherVO voucherVO = promotionService.modifyPromotion(userId, promotionMapId, status);
        log.info("modify promotion,userId: {}, promotionId: {}, status: {},result: {}",userId,
                voucherId, status, voucherVO);
        return voucherVO;
    }


}
