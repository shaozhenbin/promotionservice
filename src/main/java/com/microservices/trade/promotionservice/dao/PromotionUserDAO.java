package com.microservices.trade.promotionservice.dao;

import com.microservices.trade.promotionservice.domain.DO.PromotionUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 15:38.
 * Desc:
 * ==================================
 */
public interface PromotionUserDAO extends JpaRepository<PromotionUserDO, Long>{

}
