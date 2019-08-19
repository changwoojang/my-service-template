package com.kakaopay.changwoo.web.bank.dto;

import com.kakaopay.changwoo.domain.model.bank.SupplyAmountEntity;
import com.kakaopay.changwoo.domain.model.bank.SupplyAmountSumEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */

@Data
@Builder
@ToString
public class SupplyAmount {

    String year;
    int housingCityFundAmount;
    int kbBankAmount;
    int wooriBankAmount;
    int shinhanBankAmount;
    int cityBankAmount;
    int hanaBankAmount;
    int nonghyupBankAmount;
    int kebBankAmount;
    int etcBankAmount;

    public static SupplyAmount fromSupplyAmount(SupplyAmountEntity supplyAmountEntity){
        SupplyAmount supplyAmount = SupplyAmount.builder()
                .year(supplyAmountEntity.getYear())
                .cityBankAmount(supplyAmountEntity.getCityBank())
                .etcBankAmount(supplyAmountEntity.getEtcBank())
                .hanaBankAmount(supplyAmountEntity.getHanaBank())
                .kbBankAmount(supplyAmountEntity.getKbBank())
                .kebBankAmount(supplyAmountEntity.getKebBank())
                .nonghyupBankAmount(supplyAmountEntity.getNonghyupBank())
                .shinhanBankAmount(supplyAmountEntity.getShinhanBank())
                .housingCityFundAmount(supplyAmountEntity.getHousingCityFund())
                .wooriBankAmount(supplyAmountEntity.getWooriBank())
                .build();
        return supplyAmount;
    }

    public static SupplyAmount fromSupplyAmountSum(SupplyAmountSumEntity supplyAmountSumEntity){
        SupplyAmount supplyAmount = SupplyAmount.builder()
                .year(supplyAmountSumEntity.getYear())
                .cityBankAmount(supplyAmountSumEntity.getCityBank())
                .etcBankAmount(supplyAmountSumEntity.getEtcBank())
                .hanaBankAmount(supplyAmountSumEntity.getHanaBank())
                .kbBankAmount(supplyAmountSumEntity.getKbBank())
                .kebBankAmount(supplyAmountSumEntity.getKebBank())
                .nonghyupBankAmount(supplyAmountSumEntity.getNonghyupBank())
                .shinhanBankAmount(supplyAmountSumEntity.getShinhanBank())
                .housingCityFundAmount(supplyAmountSumEntity.getHousingCityFund())
                .wooriBankAmount(supplyAmountSumEntity.getWooriBank())
                .build();
        return supplyAmount;
    }


}
