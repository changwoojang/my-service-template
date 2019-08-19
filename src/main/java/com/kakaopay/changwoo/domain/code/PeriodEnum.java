package com.kakaopay.changwoo.domain.code;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
public enum PeriodEnum {

    YEAR("year"),
    MONTH("month");

    String period;

    PeriodEnum(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
