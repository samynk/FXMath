/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;

import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I3DValue;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class UnitCrossProductFunction extends AbstractScriptVariable implements I3DValue {

    public I3DValue op1;
    public I3DValue op2;
    private final I3DValue result;

    public UnitCrossProductFunction(I3DValue op1, I3DValue op2) {
        this.op1 = op1;
        this.op2 = op2;
        var cp = new CrossProductFunction(op1, op2);
        result = new NormalizeFunction3D(cp);
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
    public I3DValue clone() {
        return new ScriptValue3(result);
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
        result.update();
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

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

}
