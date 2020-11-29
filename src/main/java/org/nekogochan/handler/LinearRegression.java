package org.nekogochan.handler;

import Jama.Matrix;
import org.nekogochan.function.Function;

public class LinearRegression implements Regression {

    private double[] working;

    double[][] x;
    double[] y;
    double[] a;

    Function foo;

    double[] errors;
    double[][] derivatives;
    double[] beta;
    double[][] alpha;

    @Override
    public void setData(double[][] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setFunction(Function foo) {
        this.foo = foo;
        a = new double[foo.getNParameters()];
        working = new double[foo.getNParameters()];
        for (int i = 0; i < foo.getNParameters(); i++) {
            a[i] = 0;
        }
    }

    @Override
    public double[] getParameters() {
        return a;
    }

    @Override
    public void fitData() {
        init();
        iterateValues();
    }

    @Override
    public double getErrors() {
        double newError = 0;
        for (int i = 0; i < y.length; i++) {
            double v = foo.getValue(x[i], a);
            errors[i] = y[i] - v;
            newError += Math.pow(errors[i], 2);
        }
        return newError;
    }

    @Override
    public double[] getUncertainty() {
        Matrix aMatrix = new Matrix(alpha);
        Matrix b = aMatrix.inverse();

        double[] residuals = new double[a.length];
        double error = getErrors() / y.length;

        for (int i = 0; i < a.length; i++){
            residuals[i] = Math.sqrt(b.get(i, i) * error);
        }
        return residuals;
    }

    private double calculateDerivative(int k, double[] x){
        for (int i = 0; i < foo.getNParameters(); i++){
            working[i] = i==k?1:0;
        }
        return foo.getValue(x, working);

    }

    private void calculateDerivatives(){
        for (int j = 0; j < a.length; j++){
            for (int i = 0; i < y.length; i++){
                derivatives[i][j] = calculateDerivative(j, x[i]);
            }
        }
    }

    private void createBetaMatrix() {
        beta = new double[a.length];
        for (int i = 0; i < beta.length; i++) {
            for (int j = 0; j < x.length; j++) {
                beta[i] += errors[j] * derivatives[j][i];
            }
        }
    }

    private void createAlphaMatrix() {
        alpha = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < x.length; k++) {
                    alpha[j][i] += derivatives[k][i] * derivatives[k][j];
                }
            }
        }
    }

    private void iterateValues() {
        getErrors();
        calculateDerivatives();

        createBetaMatrix();
        createAlphaMatrix();

        Matrix alphaMatrix = new Matrix(alpha);
        Matrix betaMatrix = new Matrix(beta, beta.length);

        Matrix out = alphaMatrix.solve(betaMatrix);

        double[][] deltaA = out.getArray();

        for (int i = 0; i < a.length; i++)
            a[i] += deltaA[i][0];
    }

    private void init() {
        errors = new double[y.length];
        derivatives = new double[y.length][a.length];
    }
}
