package com.microservices.trade.promotionservice.domain.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:50.
 * Desc:
 * ==================================
 */
@Data
public class VoucherVO implements Serializable {
    // 应该是 VoucherUser的Id。。这样一个人得到多张同批次券，编号不一样。
    private Long id;
    private Long userId;
    private Long amount;
    private Long limitMoney;
    private Long timeStart;
    private Long timeEnd;
    private Integer voucherStatus;
    private String title;
}
