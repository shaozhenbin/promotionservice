package com.microservices.trade.promotionservice.domain.DO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:16.
 * Desc:
 * ==================================
 */
@Data
@Entity
public class VoucherDO implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long limitMoney;

    @Column(nullable = false)
    private Long timeStart;

    @Column(nullable = false)
    private Long timeEnd;

    @Column(nullable = false)
    private String title;

}
