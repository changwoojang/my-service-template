package com.changwoo.project.web.coupon;

import com.changwoo.project.common.ErrorCodeType;
import com.changwoo.project.domain.model.Coupon.CouponEntity;
import com.changwoo.project.domain.model.Coupon.CouponRepository;
import com.changwoo.project.util.AppException;
import com.changwoo.project.web.coupon.dto.CouponCreate;
import com.changwoo.project.web.coupon.dto.CouponCreateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by changwooj111@gmail.com on 2019-08-08
 */
@Service
public class CouponService {
    static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private CouponRepository couponRepository;

    static boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Transactional
    public CouponCreateResult create(CouponCreate dto) {

        checkNotNull(dto);

        if(!isEmailValid(dto.getEmail())){
            throw new AppException(ErrorCodeType.USER_EMAIL_FORMAT_INVALID);
        }

        CouponEntity couponEntity = CouponEntity.builder()
                .withCouponId("111")
                .withEmail(dto.getEmail())
                .build();

        couponEntity = this.couponRepository.save(couponEntity);

        CouponCreateResult couponCreateResult = new CouponCreateResult();
        couponCreateResult.setCouponId(couponEntity.getCouponId());

        return  couponCreateResult;

    }


}
