package org.nekogochan.function;

import org.nekogochan.domain.MathFunctions;

import static org.nekogochan.Main.floatFormat;

public abstract class NDegreeFunction extends Function {

    private String name;

    public NDegreeFunction(String name) {
        this.name = name;
    }

    public abstract double getValue(double[] xValues, double[] parameters);
    public abstract int getNParameters();

    public String toString(double[] parameters) {
        StringBuilder str = new StringBuilder(name);
        str.append(":");

        for (int i = 0; i < parameters.length; i++) {
            int degree = parameters.length - i - 1;
            double par = parameters[i];

            switch (degree) {
                case 0:
                    str.append(getStrPart(par, ""));
                    break;
                case 1:
                    str.append(getStrPart(par, "x"));
                    break;
                default:
                    str.append(getStrPart(par, "x^" + (parameters.length - i - 1)));
            }
        }

        return str.toString();
    }

    private String getStrPart(double par, String part) {
        String sign = MathFunctions.getSign(par);
        String res = " ";
        if (!sign.equals("")) {
            if (!sign.equals("-")) {
                res += sign;
            }
            res += String.format(floatFormat, par);
            if (part != null) {
                res += part;
            }
        }
        return res;
    }
}
