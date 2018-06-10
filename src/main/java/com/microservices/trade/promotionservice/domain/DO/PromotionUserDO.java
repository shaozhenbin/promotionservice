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
 * Time: 15:32.
 * Desc:
 * ==================================
 */

@Data
@Entity
public class PromotionUserDO implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long promotionId;

    @Column(nullable = false)
    private Long useTimeStamp;

    @Column(nullable = false)
    private Integer promotionStatus;
}
