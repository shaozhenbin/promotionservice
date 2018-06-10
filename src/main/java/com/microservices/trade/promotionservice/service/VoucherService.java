package com.microservices.trade.promotionservice.service;

import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 12:25.
 * Desc:
 * ==================================
 */
public interface VoucherService {
    VoucherVO saveVoucher(VoucherVO voucherVO);
    List<VoucherVO> queryVoucherByUserId(Long userId, Boolean available);
    Long offerVoucher(Long userId, Long voucherId);
    VoucherVO modifyVoucher(Long userId,Long voucherMapId, Integer status);
}
