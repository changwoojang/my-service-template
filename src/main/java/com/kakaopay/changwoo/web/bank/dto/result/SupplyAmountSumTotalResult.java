package com.kakaopay.changwoo.web.bank.dto.result;

import com.kakaopay.changwoo.web.bank.dto.SupplyAmountSumTotal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
@Data
@AllArgsConstructor
@Builder
@ToString
public class SupplyAmountSumTotalResult {

    String name;

    List<SupplyAmountSumTotal> supplyAmountSumTotal;

}
