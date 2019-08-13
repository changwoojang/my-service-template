package com.changwoo.project.domain.model.Coupon;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class CouponSpecs {

    /**
     * 쿠폰 정보
     * @param couponId
     * @return
     */
//    public static Specification<CouponEntity> detail(String couponId) {
//        return new Specification<CouponEntity>() {
//
//            @Override
//            public Predicate toPredicate(Root<CouponEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                return cb.and(
//                        cb.equal(root.get(CouponEntity_.couponId), couponId));
//            }
//        };
//    }
    
}
