package com.kakaopay.changwoo.web.bank.dto.result;

import com.kakaopay.changwoo.web.bank.dto.Bank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankListResult {

    String name;
    List<Bank> data;

}
