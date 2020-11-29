package org.nekogochan.function;

import org.nekogochan.domain.MathFunctions;

public abstract class Function {
    public abstract double getValue(double[] xValues, double[] parameters);
    public abstract int getNParameters();
}
