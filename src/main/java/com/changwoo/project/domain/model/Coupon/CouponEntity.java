package com.changwoo.project.domain.model.Coupon;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by changwooj111@gmail.com on 2019-08-07
 */

/*
각각 뭘 사용할지 선택해서 이유 생각하기
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "COUPON_INFO")
public class CouponEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "COUPON_ID", nullable = false)
    private String couponId;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    private CouponEntity(Builder builder){
        this.couponId = checkNotNull(builder.couponId);
        this.email = checkNotNull(builder.email);

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String couponId;
        private String email;

        private Builder() {
        }

        public Builder withCouponId(String couponId) {
            this.couponId = couponId;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CouponEntity build() {
            return new CouponEntity(this);
        }
    }
}
