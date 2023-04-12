package dae.math.script.specops;

import dae.math.script.values.Double3;

/**
 *
 * @author Koen.Samyn
 */
public interface IMatrix3DValue extends ICalc {

    @Override
    public IMatrix3DValue getValue();

    public void setComponent(int column, Double3 value, double w);
    
    public void setComponent(int row, int column, double value);

    public double getComponent(int row, int column);

    public void transformPoint(I3DValue point, Double3 result);

    public void transformVector(I3DValue vector, Double3 result);
    
    public void transform(I3DValue vectorOrPoint, Double3 result);
}
