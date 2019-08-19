package com.kakaopay.changwoo.web.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by changwooj111@gmail.com on 2019-08-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyAmountSum {

    @JsonProperty("주택도시기금")
    int housingCityFundAmount;
    @JsonProperty("국민은행")
    int kbBankAmount;
    @JsonProperty("우리은행")
    int wooriBankAmount;
    @JsonProperty("신한은행")
    int shinhanBankAmount;
    @JsonProperty("한국시티은행")
    int cityBankAmount;
    @JsonProperty("하나은행")
    int hanaBankAmount;
    @JsonProperty("농협은행/수협은행")
    int nonghyupBankAmount;
    @JsonProperty("외환은행")
    int kebBankAmount;
    @JsonProperty("기카은행")
    int etcBankAmount;

    public static SupplyAmountSumTotal fromSupplyAmountSumTotal(SupplyAmount supplyAmount){

        SupplyAmountSum supplyAmountSum = SupplyAmountSum.builder()
                .etcBankAmount(supplyAmount.getEtcBankAmount())
                .hanaBankAmount(supplyAmount.getHanaBankAmount())
                .housingCityFundAmount(supplyAmount.getHousingCityFundAmount())
                .kbBankAmount(supplyAmount.getKbBankAmount())
                .cityBankAmount(supplyAmount.getCityBankAmount())
                .kebBankAmount(supplyAmount.getKebBankAmount())
                .nonghyupBankAmount(supplyAmount.getNonghyupBankAmount())
                .shinhanBankAmount(supplyAmount.getShinhanBankAmount())
                .wooriBankAmount(supplyAmount.getWooriBankAmount()).build();

        //결과 값을 SupplyAmountSumTotal에 저장한다.
        SupplyAmountSumTotal supplyAmountSumTotal = SupplyAmountSumTotal.builder()
                .year(supplyAmount.getYear())
                .total_amount(supplyAmount.getHousingCityFundAmount() +
                        supplyAmount.getCityBankAmount() +
                        supplyAmount.getEtcBankAmount() +
                        supplyAmount.getKbBankAmount() +
                        supplyAmount.getNonghyupBankAmount() +
                        supplyAmount.getWooriBankAmount() +
                        supplyAmount.getHanaBankAmount() +
                        supplyAmount.getKebBankAmount())
                .detail_amount(supplyAmountSum).build();

        return supplyAmountSumTotal;
    }


}
