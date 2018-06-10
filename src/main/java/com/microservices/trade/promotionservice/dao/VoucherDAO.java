package com.microservices.trade.promotionservice.dao;

import com.microservices.trade.promotionservice.domain.DO.VoucherDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:54.
 * Desc:
 * ==================================
 */
public interface VoucherDAO extends JpaRepository<VoucherDO,Long> {
}
