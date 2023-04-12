package dae.math.script.specops;

import dae.math.script.FormulaOptions;
import dae.math.script.values.Double3;

/**
 *
 * @author Koen.Samyn
 */
public interface IMatrix33Value extends ICalc {

    @Override
    public IMatrix33Value getValue();

    public void setComponent(int column, Double3 value);

    public double getComponent(int row, int column);

    public void transformVector(I3DValue vector, Double3 result);
}
