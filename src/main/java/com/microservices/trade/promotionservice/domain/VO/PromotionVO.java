package com.microservices.trade.promotionservice.domain.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Space
 * Date: 2018/6/10 0010.
 * Time: 10:50.
 * Desc:
 * ==================================
 */
@Data
public class PromotionVO implements Serializable {

    //此处 id 应为促销活动id。 PromotionUser 只做用户使用情况的统计。
    private Long id;
    private Long productId;
    private Long amount;
    private Long limitCount;
    private Integer limitType;
    private String title;
    private Integer source;
}
