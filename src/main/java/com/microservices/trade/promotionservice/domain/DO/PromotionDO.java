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
    private Long stock;

    @Column(nullable = false)
    private Long Amount;

    @Column(nullable = false)
    private Long Limit;

    @Column(nullable = false)
    private String title;
}
