package com.changwoo.project.web.coupon.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by changwooj111@gmail.com on 2019-08-08
 */
@Getter
@Setter
@Data
public class CouponCreate {

    @NotBlank
    private String email;

    //private String couponName;

}
