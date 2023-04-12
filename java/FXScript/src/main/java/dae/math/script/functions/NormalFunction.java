/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptContext;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I3DValue;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;
import dae.math.script.values.Triangle3D;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class NormalFunction extends AbstractScriptVariable implements I3DValue {

    private Triangle3D triangle;
    private ScriptContext scriptContext;
    private I3DValue result;

    public NormalFunction(Triangle3D t) {
        this.triangle = t;
        update();
    }

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
    public Double3 getWorldPosition(Double3 w) {
        return result.getWorldPosition(w);
    }

    @Override
    public double norm() {
        return result.norm();
    }

    @Override
    public void update() {
        triangle.calcNormal();
        result = triangle.getNormal();
    }

    @Override
    public Object getValue() {
        return result;
    }

    @Override
    public double w() {
        return result.w();
    }

    @Override
    public I3DValue clone() {
        return new ScriptValue3(result);
    }
}
