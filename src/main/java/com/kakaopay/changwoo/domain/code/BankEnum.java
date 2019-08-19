package com.kakaopay.changwoo.domain.code;

import java.util.stream.Stream;

/**
 * Created by changwooj111@gmail.com on 2019-08-15
 */
public enum BankEnum {

    KB_BANK("국민은행","bnk1111"),
    WOORI_BANK("우리은행","bnk2222"),
    SHINHAN_BANK("신한은행","bnk3333"),
    CITY_BANK("한국시티은행","bnk4444"),
    HANA_BANK("하나은행","bnk5555"),
    NONGHYUP_BANK("농협은행/수협은행","bnk6666"),
    KEB_BANK("외환은행","bnk7777"),
    ETC_BANK("기타은행","bnk8888");

    private String bankName;
    private String bankCode;

    BankEnum(String bankName, String bankCode) {
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public static Stream<BankEnum> stream() {
        return Stream.of(BankEnum.values());
    }

    public static BankEnum fromString(String text) {
        for (BankEnum each : BankEnum.values()) {
            if (each.getBankName().equalsIgnoreCase(text)) {
                return each;
            }
        }
        return null;
    }

}
