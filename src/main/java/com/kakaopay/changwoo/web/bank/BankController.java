package com.kakaopay.changwoo.web.bank;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.changwoo.domain.code.BankEnum;
import com.kakaopay.changwoo.web.bank.dto.result.BankAmountAverageResult;
import com.kakaopay.changwoo.web.bank.dto.result.BankListResult;
import com.kakaopay.changwoo.web.bank.dto.result.BankTopAmountResult;
import com.kakaopay.changwoo.web.bank.dto.result.SupplyAmountSumTotalResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by changwooj111@gmail.com on 2019-08-13
 */
@RestController
@RequestMapping(value = "/v1/bank")
@Validated
@Slf4j
public class BankController {

    @Autowired
    BankService bankService;

    @GetMapping(path = "/list", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BankListResult bankList(){
        return bankService.getListOfBank();
    }

    @GetMapping(path = "/supply-amount/top", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BankTopAmountResult topSupplyAmount(){
        BankTopAmountResult bankTopAmountResult = bankService.getTopSupplyAmount();
        return bankTopAmountResult;
    }

    @GetMapping(path = "/supply-amount/total",produces= MediaType.TEXT_PLAIN_VALUE)
    public String totalSupplyAmount(@RequestParam("period") String period){
        log.info("totalSupplyAmount period : {}", period);
        SupplyAmountSumTotalResult supplyAmountSumTotalResult = bankService.getSupplyAmount(period);

        // RETURN 형태를 가공
        ObjectMapper objectMapper = new ObjectMapper();
        String supplyAmountResult = null;
        try {
            String jsonString = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(supplyAmountSumTotalResult);
            supplyAmountResult = jsonString.replaceAll("\"supplyAmountSumTotal\" : ","");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return supplyAmountResult;
    }

    @GetMapping(path = "/supply-amount/average", produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BankAmountAverageResult bankAmountAverageMinMax(@RequestParam("bankName") BankEnum bankType){
        log.info("bankAmountAverageMinMax BankType : {}", bankType);
        return bankService.getBankAmountAverageMinMax(bankType);
    }
}
