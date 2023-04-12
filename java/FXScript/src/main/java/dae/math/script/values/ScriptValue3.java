/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.values;

import dae.math.script.specops.I3DValue;
import dae.math.script.specops.IDouble1Value;
import dae.math.script.SimpleScriptVariable;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class ScriptValue3 extends SimpleScriptVariable implements I3DValue {

    private final IDouble1Value x;
    private final IDouble1Value y;
    private final IDouble1Value z;
    private final IDouble1Value w;

    public ScriptValue3(){
        x = new Double1(0);
        y = new Double1(0);
        z = new Double1(0);
        w = new Double1(0);
    }
    
    public ScriptValue3(Double3 toWrap) {
        x = new Double1(toWrap.x);
        y = new Double1(toWrap.y);
        z = new Double1(toWrap.z);
        w = new Double1(0);
    }

    public ScriptValue3(IDouble1Value x, IDouble1Value y, IDouble1Value z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = new Double1(0);
    }

    public ScriptValue3(IDouble1Value x, IDouble1Value y, IDouble1Value z, IDouble1Value w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    // copy constructor
    public ScriptValue3(I3DValue other) {
        this.x = new Double1(other.x());
        this.y = new Double1(other.y());
        this.z = new Double1(other.z());
        this.w = new Double1(other.w());
    }

    @Override
    public ScriptValue3 clone() {
        return new ScriptValue3(this.x, this.y, this.z);
    }

    public IDouble1Value xVal() {
        return x;
    }

    public IDouble1Value yVal() {
        return y;
    }

    public IDouble1Value zVal() {
        return z;
    }

    public IDouble1Value wVal() {
        return w;
    }

    public double x() {
        x.update();
        return x.getValue().value();
    }

    public double y() {
        y.update();
        return y.getValue().value();
    }

    public double z() {
        z.update();
        return z.getValue().value();
    }

    public double w() {
        w.update();
        return w.getValue().value();
    }

    @Override
    public void set(double xv, double yv, double zv, double wv) {
        if (x instanceof Double1) {
            ((Double1) x).set(xv);
        }
        if (y instanceof Double1) {
            ((Double1) y).set(yv);
        }
        if (z instanceof Double1) {
            ((Double1) z).set(zv);
        }
    }

    @Override
    public void setX(double xv) {
        if (x instanceof Double1) {
            ((Double1) x).set(xv);
        }
    }

    @Override
    public void setY(double yv) {
        if (y instanceof Double1) {
            ((Double1) y).set(yv);
        }
    }

    @Override
    public void setZ(double zv) {
        if (z instanceof Double1) {
            ((Double1) z).set(zv);
        }
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        w.set(x(), y(), z(), w());
        return w;
    }

    @Override
    public double norm() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    @Override
    public void update() {
        this.x.update();
        this.y.update();
        this.z.update();
    }

    @Override
    public Object getValue() {
        return this;
    }
}
