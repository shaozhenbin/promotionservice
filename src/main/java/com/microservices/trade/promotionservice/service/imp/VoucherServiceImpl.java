package com.microservices.trade.promotionservice.service.imp;

import com.microservices.trade.promotionservice.dao.VoucherDAO;
import com.microservices.trade.promotionservice.dao.VoucherUserDAO;
import com.microservices.trade.promotionservice.domain.DO.VoucherDO;
import com.microservices.trade.promotionservice.domain.DO.VoucherUserDO;
import com.microservices.trade.promotionservice.domain.VO.VoucherVO;
import com.microservices.trade.promotionservice.domain.module.VoucherStatusEnum;
import com.microservices.trade.promotionservice.service.VoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:54.
 * Desc:
 * ==================================
 */
@Slf4j
@Component
public class VoucherServiceImpl implements VoucherService {
    @Resource
    private VoucherDAO voucherDAO;

    @Resource
    private VoucherUserDAO voucherUserDAO;

    @Override
    public VoucherVO saveVoucher(VoucherVO voucherVO) {
        if (voucherVO == null) {
            return null;
        }
        VoucherDO voucherDO = new VoucherDO();
        BeanUtils.copyProperties(voucherVO, voucherDO);
        voucherDO = voucherDAO.save(voucherDO);
        BeanUtils.copyProperties(voucherDO, voucherVO);
        log.info("PromotionService save voucher: {}", voucherVO);
        return voucherVO;
    }

    @Override
    public List<VoucherVO> queryVoucherByUserId(Long userId, Boolean available) {
        if (userId == null) {
            return null;
        }
        List<VoucherUserDO> voucherUserDOS;
        // 个人持有红包信息
        if (available) {
            voucherUserDOS = voucherUserDAO.findAllByUserIdAndVoucherStatus(userId,
                    VoucherStatusEnum.available.getValue());
        } else {
            voucherUserDOS = voucherUserDAO.findAllByUserIdAndVoucherStatusIsNot(userId,
                    VoucherStatusEnum.available.getValue());
        }

        //红包信息
        List<Long> voucherIds = voucherUserDOS.stream()
                .map(voucherUser -> voucherUser.getVoucherId())
                .collect(Collectors.toList());
        List<VoucherDO> voucherDOS = voucherDAO.findAllByIdIn(voucherIds);

        //转视图
        Map<Long, VoucherDO> voucherDOMap = voucherDOS.stream()
                .collect(Collectors.toMap(VoucherDO::getId, voucherDO -> voucherDO));

        List<VoucherVO> voucherVOS = new ArrayList<>();
        for (VoucherUserDO voucherUserDO : voucherUserDOS) {
            VoucherVO voucherVO = new VoucherVO();
            VoucherDO voucherDO = voucherDOMap.get(voucherUserDO.getVoucherId());
            BeanUtils.copyProperties(voucherDO, voucherVO);
            BeanUtils.copyProperties(voucherUserDO, voucherVO);
            voucherVOS.add(voucherVO);
        }

        log.info("query Voucher userId: {}, available: {}, result: {}", userId, available,
                voucherVOS);
        return voucherVOS;
    }

    @Override
    public Long offerVoucher(Long userId, Long voucherId) {
        if (userId == null || voucherId == null) {
            return null;
        }
        Long timestamp = new Date().getTime();
        List<Long> voucherIds = new ArrayList<>(1);
        voucherIds.add(voucherId);
        List<VoucherDO> availVoucher = voucherDAO.findAllByIdInAndTimeEndGreaterThan(voucherIds,
                timestamp);
        if (availVoucher == null || availVoucher.isEmpty()) {
            log.error("所选抵用券批次已失效或不存在, voucherId: {}, timestamp: {}", voucherId, timestamp);
            return null;
        }
        VoucherUserDO voucherUserDO = new VoucherUserDO();
        voucherUserDO.setUserId(userId);
        voucherUserDO.setVoucherId(voucherId);
        voucherUserDO.setVoucherStatus(VoucherStatusEnum.available.getValue());
        voucherUserDO = voucherUserDAO.save(voucherUserDO);
        log.info("添加抵用券成功,voucherUserDO:{}", voucherUserDO);
        return voucherUserDO.getId();
    }

    @Override
    public VoucherVO modifyVoucher(Long userId, Long voucherMapId, Integer status) {
        if (userId == null || voucherMapId == null || status == null) {
            return null;
        }
        log.info("modify voucher userId: {}, voucherMapId: {}, status: {}", userId, voucherMapId,
                status);
        VoucherUserDO voucherUserDO = voucherUserDAO.findByIdAndUserId(voucherMapId, userId);
        if (voucherUserDO == null) {
            log.error("未查询到抵用券, userId:{}, voucherMapId: {}", userId, voucherMapId);
            return null;
        } else if (voucherUserDO.getVoucherStatus() != VoucherStatusEnum.available.getValue()
                && voucherUserDO.getVoucherStatus() != status) {
            log.error("当前抵用券不可用, userId:{}, voucherMapId: {}, voucherUser: {}", userId,
                    voucherMapId, voucherUserDO);
            return null;
        }

        if (voucherUserDO.getVoucherStatus() == status) {
            log.info("当前状态已为 {} userId: {}, voucherMapId: {}", VoucherStatusEnum
                    .getVoucherStatusEnumByValue(status).name(), userId, voucherMapId);
        } else {
            Integer updateCount = voucherUserDAO.modifyStatus(voucherMapId, status);
            if(updateCount == null || updateCount != 1){
                log.error("抵用券状态更新失败, status: {}, updateCount: {}",status,updateCount);
                return null;
            }
            voucherUserDO = voucherUserDAO.findById(voucherMapId);
        }

        VoucherDO voucherDO = voucherDAO.findById(voucherUserDO.getVoucherId());
        if (voucherDO == null) {
            log.error("未查询到抵用券, userId:{}, voucherId: {}", userId, voucherUserDO.getVoucherId());
            return null;
        }
        VoucherVO voucherVO = new VoucherVO();
        BeanUtils.copyProperties(voucherDO, voucherVO);
        BeanUtils.copyProperties(voucherUserDO, voucherVO);
        log.info("modify voucher userId: {}, voucherMapId: {}, status: {},result: {}", userId,
                voucherMapId, status, voucherVO.toString());
        return voucherVO;
    }

    @Override
    public VoucherVO verifyVoucher(Long userId, Long voucherMapId, Long amount) {
        if (userId == null || voucherMapId == null || amount == null) {
            return null;
        }
        VoucherUserDO voucherUserDO = voucherUserDAO.findByIdAndUserId(voucherMapId, userId);
        if (voucherUserDO == null) {
            log.error("未查到用户对应抵用券信息, userId: {}, voucherMapId: {}", userId, voucherMapId);
            return null;
        }
        if (voucherUserDO.getVoucherStatus() != VoucherStatusEnum.available.getValue()) {
            log.error("验券失败, 该券状态不可用, useId: {}, voucherUserDO: {}", userId, voucherUserDO.toString());
            return null;
        }
        VoucherDO voucherDO = voucherDAO.getOne(voucherUserDO.getVoucherId());
        if(voucherDO == null){
            log.error("未查到抵用券信息, voucherID: {}",voucherUserDO.getVoucherId());
            return null;
        }
        if(voucherDO.getLimitMoney() > amount){
            log.error("未达到用券金额, amount: {}, voucherDO: {}",amount,voucherDO.toString());
            return null;
        }
        VoucherVO voucherVO = new VoucherVO();
        BeanUtils.copyProperties(voucherDO, voucherVO);
        BeanUtils.copyProperties(voucherUserDO, voucherVO);
        return voucherVO;
    }
}
