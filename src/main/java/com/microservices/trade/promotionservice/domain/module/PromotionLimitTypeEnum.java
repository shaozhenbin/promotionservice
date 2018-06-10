package com.microservices.trade.promotionservice.domain.module;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 15:47.
 * Desc:
 * ==================================
 */
public enum PromotionLimitTypeEnum {
    limitByDay(1),
    limitAllTime(2),
    free(3);

    private Integer value;
    PromotionLimitTypeEnum(Integer value){
        this.value = value;
    }
    public static PromotionLimitTypeEnum getPromotionLimitTypeByValue(int value){
        for(PromotionLimitTypeEnum typeEnum: PromotionLimitTypeEnum.values()){
            if (typeEnum.value == value){
                return typeEnum;
            }
        }
        return null;
    }
}
