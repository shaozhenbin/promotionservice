package com.microservices.trade.promotionservice.dao;

import com.microservices.trade.promotionservice.domain.DO.VoucherUserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 12:51.
 * Desc:
 * ==================================
 */
public interface VoucherUserDAO extends JpaRepository<VoucherUserDO,Long> {
    List<VoucherUserDO> findAllByUserId(Long userId);
    List<VoucherUserDO> findAllByUserIdAndVoucherStatus(Long userId,Integer status);
    List<VoucherUserDO> findAllByUserIdAndVoucherStatusIsNot(Long userId,Integer status);
    VoucherUserDO findByIdAndUserId(Long id, Long userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update VoucherUserDO vu set vu.voucherStatus=?2 where vu.id=?1")
    Integer modifyStatus(Long id, Integer status);
}
