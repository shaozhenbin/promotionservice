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
 * Time: 12:44.
 * Desc:
 * ==================================
 */
@Data
@Entity
public class VoucherUserDO implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long voucherId;

    @Column(nullable = false)
    private Integer voucherStatus;
}
