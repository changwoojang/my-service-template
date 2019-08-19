package com.kakaopay.changwoo.web.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by changwooj111@gmail.com on 2019-08-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplyAmountAverage {
    String year;
    String amount;
    double average;
}
