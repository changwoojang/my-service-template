package com.kakaopay.changwoo.web.bank.dto.result;

import com.kakaopay.changwoo.web.bank.dto.SupplyAmountAverageMinMax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by changwooj111@gmail.com on 2019-08-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAmountAverageResult {

    /**
     * 은행 이름
     */
    String bank;

    List<SupplyAmountAverageMinMax> support_amount;

}
