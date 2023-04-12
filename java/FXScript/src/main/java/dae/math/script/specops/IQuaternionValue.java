package dae.math.script.specops;

import dae.math.script.FormulaOptions;

/**
 *
 * @author Koen Samyn
 */
public interface IQuaternionValue extends ICalc {

    @Override
    public IQuaternionValue getValue();
    
    public I3DValue getAxis();
    public IDouble1Value getAngle();
    
    public void transform(double x, double y, double z, I3DValue result);
    public void mul(IQuaternionValue op2, IQuaternionValue result);
    public void set(double x, double y, double z, double w);
    
    public double x();
    public double y();
    public double z();
    public double w();
    

    @Override
    public default Double getComponent(int index) {
        return switch (index) {
            case 0 -> getValue().x();
            case 1 -> getValue().y();
            case 2 -> getValue().z();
            case 3 -> getValue().w();
            default -> null;
        };
    }
}
