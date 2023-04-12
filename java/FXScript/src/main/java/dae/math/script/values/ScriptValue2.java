/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.values;

import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I2DValue;
import dae.math.script.specops.IDouble1Value;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class ScriptValue2 extends ConstantScriptValue implements I2DValue {

    private final IDouble1Value x;
    private final IDouble1Value y;
    private final IDouble1Value w;

    private final Double2 actualValue = new Double2();
    
    /*
    * Creates a new 2D value with all coordinates set to zero.
    */
    public ScriptValue2(){
        this(Double1.ZERO, Double1.ZERO);
    }

    /**
     * Creates a new 2D value with a homogeneous coordinate of zero.
     *
     * @param x the x value of the 2D point or vector.
     * @param y the y value of the 2D point or vector.
     */
    public ScriptValue2(IDouble1Value x, IDouble1Value y) {
        this(x, y, new Double1(0));
    }

    /**
     * Creates a new 2D value with a homogeneous coordinate of zero.
     *
     * @param x the x value of the 2D point or vector.
     * @param y the y value of the 2D point or vector.
     * @param w the homogeneous coordinate of the 2D point or vector.
     */
    public ScriptValue2(IDouble1Value x, IDouble1Value y, IDouble1Value w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    @Override
    public double x() {
        return x.getComponent(0);
    }

    public IDouble1Value xComponent() {
        return x;
    }

    @Override
    public double y() {
        return y.getComponent(0);
    }

    public IDouble1Value yComponent() {
        return y;
    }
    
    @Override
    public double w() {
        return w.getX();
    }

    public double xVal() {
        return x();
    }

    public double yVal() {
        return y();
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    public void set(double xv, double yv) {
        if (x instanceof Double1) {
            ((Double1) x).set(xv);
        }
        if (y instanceof Double1) {
            ((Double1) y).set(yv);
        }
    }

    @Override
    public double norm() {
        return Math.hypot(x(), y());
    }

    @Override
    public void update() {
        x.update();
        y.update();
        w.update();
        actualValue.x = x();
        actualValue.y = y();
        actualValue.w = w();
    }

    @Override
    public Object getValue() {
        return this.actualValue;
    }
}
