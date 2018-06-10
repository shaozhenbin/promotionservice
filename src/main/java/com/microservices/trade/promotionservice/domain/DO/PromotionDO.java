package com.microservices.trade.promotionservice.domain.DO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.beans.IntrospectionException;
import java.io.Serializable;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:18.
 * Desc:
 * ==================================
 */
@Data
@Entity
public class PromotionDO implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long limitCount;

    @Column(nullable = false)
    private Long limitMoney;

    @Column(nullable = false)
    private Integer limitType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer source;
}
