package com.changwoo.project.domain.model.Coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by changwooj111@gmail.com on 2019-08-07
 */
@Repository
public interface CouponRepository extends
        CrudRepository<CouponEntity, Long>,
        JpaRepository<CouponEntity, Long>,
        JpaSpecificationExecutor<CouponEntity> {
}
