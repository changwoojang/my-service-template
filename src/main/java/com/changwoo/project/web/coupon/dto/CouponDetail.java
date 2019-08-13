package com.changwoo.project.web.coupon.dto;

import com.changwoo.project.domain.model.Coupon.CouponEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by changwooj111@gmail.com on 2019-08-13
 */

@Setter
@Getter
@ToString
public class CouponDetail {

    @NotNull
    Long id;
    @NotNull
    String couponId;
    @NotNull
    String email;

    public static CouponDetail fromEntity(CouponEntity couponEntity){
        CouponDetail couponDetail = new CouponDetail();
        couponDetail.setId(couponEntity.getId());
        couponDetail.setCouponId(couponEntity.getCouponId());
        couponDetail.setEmail(couponEntity.getEmail());
        return couponDetail;
    }

}
