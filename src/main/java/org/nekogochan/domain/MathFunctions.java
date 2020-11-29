package org.nekogochan.domain;

public class MathFunctions {
    public static String getSign(Number number) {
        double signum = Math.signum(number.doubleValue());
        if (signum == 1.0) {
            return "+";
        } else if (signum == -1.0) {
            return "-";
        } else {
            return "";
        }
    }
}
