package com.microservices.trade.promotionservice.domain.module;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 13:31.
 * Desc:
 * ==================================
 */
public enum VoucherStatusEnum {
    available(1),
    used(2),
    expired(3);

    private int value;

    VoucherStatusEnum(int value) {
        this.value = value;
    }
    public Integer getValue(){
        return this.value;
    }

    public static VoucherStatusEnum getVoucherStatusEnumByValue(int value){
        for (VoucherStatusEnum statusEnum: VoucherStatusEnum.values()){
            if(statusEnum.value == value){
                return statusEnum;
            }
        }
        return null;
    }
}
