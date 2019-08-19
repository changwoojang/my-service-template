package com.kakaopay.changwoo.web.bank.dto;

import lombok.Builder;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
@Builder
public class SupplyAmountSumTotal {

    String year;
    int total_amount;
    SupplyAmountSum detail_amount;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public SupplyAmountSum getDetail_amount() {
        return detail_amount;
    }

    public void setDetail_amount(SupplyAmountSum detail_amount) {
        this.detail_amount = detail_amount;
    }

}
