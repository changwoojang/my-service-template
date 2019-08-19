package com.kakaopay.changwoo.web.bank.dto.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

/**
 * Created by changwooj111@gmail.com on 2019-08-16
 */
@Data
@Builder
public class BankTopAmountResult {

    String year;

    String bank;

    @JsonIgnore
    int supplyAmount;

}
