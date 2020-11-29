package org.nekogochan;

import org.nekogochan.function.Function;
import org.nekogochan.function.NDegreeFunction;
import org.nekogochan.handler.LinearRegression;
import org.nekogochan.handler.Regression;
import org.nekogochan.table.TableCalculator;

public class Main {

    public static final String floatFormat = "%.2f";

    private static final double A = 0;
    private static final double B = Math.PI;
    private static final int SIZE = 10;

    static NDegreeFunction linear = new NDegreeFunction("linear") {
        @Override
        public double getValue(double[] xValues, double[] parameters) {
            double a = parameters[0];
            double b = parameters[1];
            double x = xValues[0];
            return x*a + b;
        }

        @Override
        public int getNParameters() {
            return 2;
        }
    };
    static NDegreeFunction parabolic = new NDegreeFunction("parabolic") {
        @Override
        public double getValue(double[] xValues, double[] parameters) {
            double a = parameters[0];
            double b = parameters[1];
            double c = parameters[2];
            double x = xValues[0];
            return Math.pow(x, 2)*a + x*b + c;
        }

        @Override
        public int getNParameters() {
            return 3;
        }
    };
    static NDegreeFunction cubic = new NDegreeFunction("cubic") {
        @Override
        public double getValue(double[] xValues, double[] parameters) {
            double a = parameters[0];
            double b = parameters[1];
            double c = parameters[2];
            double d = parameters[3];
            double x = xValues[0];
            return Math.pow(x, 3)*a + Math.pow(x, 2)*b + x*c + d;
        }

        @Override
        public int getNParameters() {
            return 4;
        }
    };

    static NDegreeFunction[] functions = new NDegreeFunction[] {
            linear, parabolic, cubic
    };

    static double[][] x;
    static double[] y;
    static double[][] functionsRes = new double[3][SIZE];
    static double[][] functionsErrors = new double[3][SIZE];
    static double[] functionsErrorsSum = new double[3];

    static Regression regression = new LinearRegression();
    static TableCalculator tableCalculator = new TableCalculator();

    // инициализация x и y массивов
    static {
        tableCalculator.setFoo(new Function() {
            @Override
            public double getValue(double[] xValues, double[] parameters) {
                double a = parameters[0];
                double b = parameters[1];
                double x = xValues[0];
                return Math.sin(a*x - b);
            }

            @Override
            public int getNParameters() {
                return 2;
            }
        });

        tableCalculator.configureTable(A, B, SIZE, new double[]{2, Math.PI / 3});

        double[] xValues = tableCalculator.getXValues();

        x = new double[SIZE][1];

        for (int i = 0; i < xValues.length; i++) {
            x[i][0] = xValues[i];
        }

        y = tableCalculator.getYValues();
    }

    public static void main(String[] args) {

        for (int i = 0; i < x.length; i++) {
            System.out.printf(floatFormat + "\t", x[i][0]);
        }
        System.out.println();
        for (int i = 0; i < y.length; i++) {
            System.out.format(floatFormat + "\t", y[i]);
        }
        System.out.println();


        regression.setData(x, y);
        for (NDegreeFunction foo : functions) {
            regression.setFunction(foo);
            regression.fitData();
            System.out.println("function: " + foo.toString(regression.getParameters()));
            System.out.println("uncertainty: " + arrToFormatString(regression.getUncertainty()));
            System.out.format("errors: " + floatFormat + "\n", regression.getErrors());
            System.out.println("________________________");
            System.out.println();
        }
    }


    private static String arrToFormatString(double[] arr) {
        StringBuilder str = new StringBuilder();

        for (double d : arr) {
            str.append(String.format(floatFormat, d));
            str.append(" ");
        }

        return str.toString();
    }
}
