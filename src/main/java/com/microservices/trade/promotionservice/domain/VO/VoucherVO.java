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

    private Long id;
    private Long stock;
    private Long Amount;
    private Long LimitMoney;
    private String title;
}
