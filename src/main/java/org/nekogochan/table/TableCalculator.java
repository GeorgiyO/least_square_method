package org.nekogochan.table;

import org.nekogochan.domain.Table;
import org.nekogochan.function.Function;

public class TableCalculator {

    private Table table = new Table();
    private Function foo;
    private double step;

    public void setFoo(Function foo) {
        this.foo = foo;
    }

    public void configureTable(double min, double max, int size, double[] functionParameters) {
        table.setSize(size);
        step = (max - min) / (size - 1);
        double current = min;

        for (int i = 0; i < size; i++) {
            table.addValue(current, foo.getValue(new double[]{current}, functionParameters));
            current += step;
        }
    }

    public double[] getXValues() {
        return table.getXValues();
    }

    public double[] getYValues() {
        return table.getYValues();
    }
}
