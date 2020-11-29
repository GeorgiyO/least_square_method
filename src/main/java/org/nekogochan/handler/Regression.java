package org.nekogochan.handler;

import org.nekogochan.function.Function;

public interface Regression {

    void setData(double[][] x, double[] y);
    void setFunction(Function foo);
    double getErrors();
    double[] getParameters();
    double[] getUncertainty();
    void fitData();

}
