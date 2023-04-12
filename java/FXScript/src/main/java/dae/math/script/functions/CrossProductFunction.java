/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I3DValue;
import dae.math.script.values.Boolean1;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class CrossProductFunction extends AbstractScriptVariable implements I3DValue {

    public I3DValue op1;
    public I3DValue op2;

    public I3DValue result;
    private Boolean1 drawXYBase = new Boolean1(false);

    public CrossProductFunction(I3DValue op1, I3DValue op2) {
        this.op1 = op1;
        this.op2 = op2;
        result = new ScriptValue3();
    }
    
    public I3DValue clone(){
        return new ScriptValue3(result);
    }

    @Override
    public String getCaption() {
        return result.getCaption();
    }

//    public void setBasePoint(I3DValue base) {
//        result.setBasePoint(base);
//    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public double x() {
        return result.x();
    }

    @Override
    public double y() {
        return result.y();
    }

    @Override
    public double z() {
        return result.z();
    }
    
    @Override
    public double w() {
        return result.w();
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return result.getWorldPosition(w);
    }

    @Override
    public double norm() {
        return result.norm();
    }

    @Override
    public void update() {
        op1.update();
        op2.update();
        double xn = op1.y() * op2.z() - op1.z() * op2.y();
        double yn = op1.z() * op2.x() - op1.x() * op2.z();
        double zn = op1.x() * op2.y() - op1.y() * op2.x();
        result.set(xn, yn, zn, 0);
    }

    @Override
    public Object getValue() {
        return result;
    }

    @Override
    public void setVisible(boolean visible) {
        result.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return result.isVisible();
    }
}
