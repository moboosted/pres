package com.silvermoongroup.fsa.web.fund.util;

public class FundGuiUtility {
    public static boolean isAmountValid(String amount) {
        for (int i = 0; i < amount.length(); i++) {
            char a = amount.charAt(i);
            if (a >= '0' && a <= '9' || a == '.') {
                // Cannot return from here
            } else {
                return false;
            }
        }
        return true;
    }
}
