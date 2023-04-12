package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;
import dae.math.script.values.String1;

/**
 * Performs a transformation operation on a vector or point.
 *
 * @author Koen.Samyn
 */
public class Matrix3D_I3D_TransformOperation extends AbstractScriptVariable implements I3DValue {

    private IMatrix3DValue m;
    private I3DValue op;

    private ScriptValue3 result;

    // calculation
    private Double3 wResult = new Double3();

    // rendering
    private boolean visible;

    public Matrix3D_I3D_TransformOperation(String caption, IMatrix3DValue m, I3DValue op) {
        setCaption(caption);
        this.op = op;
        this.m = m;
        setVisible(false);
    }

    public I3DValue clone() {
        return new ScriptValue3(result);
    }

    @Override
    public void setCaption(String1 caption) {
        result.setCaption(caption);
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
    public double w() {
        return result.w();
    }

    @Override
    public double norm() {
        return result.norm();
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return result.getWorldPosition(w);
    }

    @Override
    public void update() {
        m.update();
        op.update();
        m.transformPoint(op, wResult);
        result.set(wResult.x, wResult.y, wResult.z, wResult.w);

    }

    @Override
    public Object getValue() {
        return result;
    }
}
