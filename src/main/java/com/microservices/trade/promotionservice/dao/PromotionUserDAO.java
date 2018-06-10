package com.microservices.trade.promotionservice.dao;

import com.microservices.trade.promotionservice.domain.DO.PromotionUserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update PromotionUserDO pu " +
            "set pu.promotionStatus=?3 " +
            "where pu.id = ?1 and pu.userId = ?2")
    Integer modifyStatus(Long PromotionMapId, Long userId,Integer status);
}
