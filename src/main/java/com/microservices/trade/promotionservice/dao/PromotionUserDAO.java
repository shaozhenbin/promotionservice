package com.microservices.trade.promotionservice.dao;

import com.microservices.trade.promotionservice.domain.DO.PromotionUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 15:38.
 * Desc:
 * ==================================
 */
public interface PromotionUserDAO extends JpaRepository<PromotionUserDO, Long> {
    List<PromotionUserDO> findAllByUserIdAndPromotionIdAndUseTimeStampGreaterThanEqualAndPromotionStatus
            (Long userId, Long PromotionId, Long timestamp, Integer status);

    List<PromotionUserDO> findAllByUserIdAndPromotionIdAndPromotionStatus(Long userId, Long
            PromotionId, Integer status);
}
