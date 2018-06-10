package com.microservices.trade.promotionservice.dao;

import com.microservices.trade.promotionservice.domain.DO.PromotionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:54.
 * Desc:
 * ==================================
 */


public interface PromotionDAO extends JpaRepository<PromotionDO, Long>{

    List<PromotionDO> findAllByProductIdAndLimitMoneyLessThanEqual(Long productId, Long amount);
}
