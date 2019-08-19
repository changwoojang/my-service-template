package com.kakaopay.changwoo.domain.code;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
public enum WordEnum {

    HOUSING_FINANCE_SUPPLY_STATUS("주택금융 공급현황"),
    LIST_OF_INSTITUTIONS("주택금융 공급 금융기관(은행) 목록");

    String word;

    WordEnum(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
