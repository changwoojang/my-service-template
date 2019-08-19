package com.kakaopay.changwoo.domain.model.bank;

import lombok.*;

import javax.persistence.*;

/**
 * Created by changwooj111@gmail.com on 2019-08-18
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SUPPLY_AMOUNT_SUM")
public class SupplyAmountSumEntity {
    /**
     * 파일 업로드 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    /**
     * 년도
     */
    @Column(name = "YEAR", nullable = false)
    private String year;

    @Column(name ="HOUSING_CITY_FUND", nullable = false)
    private int housingCityFund;

    @Column(name = "KB_BANK", nullable = false)
    private int kbBank;

    @Column(name = "WOORI_BANK", nullable = false)
    private int wooriBank;

    @Column(name = "SHINHAN_BANK", nullable = false)
    private int shinhanBank;

    @Column(name = "CITY_BANK", nullable = false)
    private int cityBank;

    @Column(name = "HANA_BANK", nullable = false)
    private int hanaBank;

    @Column(name = "NONGHYUP_BANK", nullable = false)
    private int nonghyupBank;

    @Column(name = "KEB_BANK", nullable = false)
    private int kebBank;

    @Column(name = "ETC_BANK", nullable = false)
    private int etcBank;
}
