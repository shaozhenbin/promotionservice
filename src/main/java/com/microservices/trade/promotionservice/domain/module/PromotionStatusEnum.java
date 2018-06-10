package com.microservices.trade.promotionservice.domain.module;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 16:44.
 * Desc:
 * ==================================
 */
public enum  PromotionStatusEnum {
    used(1),
    reset(2);

    private int value;

    PromotionStatusEnum(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

    public static PromotionStatusEnum getPromotionStatusEnumByValue(int value){
        for (PromotionStatusEnum statusEnum: PromotionStatusEnum.values()){
            if (statusEnum.value == value){
                return statusEnum;
            }
        }
        return null;
    }
}
