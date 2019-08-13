package com.changwoo.project.web.coupon;

import com.changwoo.project.common.ErrorCodeType;
import com.changwoo.project.domain.model.Coupon.CouponEntity;
import com.changwoo.project.domain.model.Coupon.CouponRepository;
import com.changwoo.project.domain.model.Coupon.CouponSpecs;
import com.changwoo.project.util.AppException;
import com.changwoo.project.web.coupon.dto.CouponCreate;
import com.changwoo.project.web.coupon.dto.CouponCreateResult;
import com.changwoo.project.web.coupon.dto.CouponDetail;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by changwooj111@gmail.com on 2019-08-07
 */
@RestController
@RequestMapping(value = "/coupon/")
@Validated
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @PostMapping(path = "create")
//    @PostMapping(path = "create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CouponCreateResult create(@Valid @RequestBody CouponCreate dto){

        logger.info("Create coupon by email: email={}",dto.getEmail());
        CouponCreateResult couponCreateResult = couponService.create(dto);
        logger.info("Success creating coupon by email: email={}",dto.toString());

        return couponCreateResult;
    }

    @GetMapping(path= "coupon_detail")
    public CouponDetail couponDetailByCouponId(@RequestParam(name = "couponId") @NotBlank String couponId){
        logger.info("Detail Coupon : couponId={}", couponId);

//        Specification<CouponEntity> spec = Specifications.where(CouponSpecs.detail(couponId));

        CouponEntity couponEntity = couponRepository.findOne(Long.parseLong(couponId));

        if (couponEntity == null) {
            throw new AppException(ErrorCodeType.COUPON_USE_NOT_FOUND);
        }

        CouponDetail result = CouponDetail.fromEntity(couponEntity);

        logger.info("OK Detail CouponDetail : {}", result.toString());
        return result;
    }

}
