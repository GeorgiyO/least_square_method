package org.nekogochan.domain;

public class Table {

    private double[] xValues;
    private double[] yValues;

    private int size;
    private int i = 0;

    public void setSize(int size) {
        this.size = size;
        xValues = new double[size];
        yValues = new double[size];
    }

    public void addValue(double x, double y) {
        if (i == size) throw new IllegalStateException("i == size");
        xValues[i] = x;
        yValues[i] = y;
        i++;
    }

    public double[] getXValues() {
        return xValues;
    }

    public double[] getYValues() {
        return yValues;
    }
}
