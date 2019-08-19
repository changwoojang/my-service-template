package com.kakaopay.changwoo.web.bank.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by changwooj111@gmail.com on 2019-08-16
 */
@Data
@Builder
public class SupplyAmountAverageMinMax {
    String year;
    double amount;
}
