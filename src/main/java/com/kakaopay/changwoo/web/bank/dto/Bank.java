package com.kakaopay.changwoo.web.bank.dto;

import com.kakaopay.changwoo.domain.model.bank.BankEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by changwooj111@gmail.com on 2019-08-18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    String institute_name;
    String institute_code;

    public static Bank fromBankEntity(BankEntity bankEntity){
        Bank bankListResult = Bank.builder()
                .institute_code(bankEntity.getBankCode())
                .institute_name(bankEntity.getBankName())
                .build();
        return  bankListResult;
    }
}
