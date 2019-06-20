package com.rbkmoney.logback.mask;

import java.math.BigDecimal;

public class Converter {

    public static BigDecimal getFormattedAmount(long amount) {
        return new BigDecimal(amount).movePointLeft(2);
    }

}