package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptContext;
import dae.math.script.ScriptValueClass;
import dae.math.script.values.Double3;
import dae.math.script.values.Matrix4f;
import dae.math.script.values.Quaternion;
import dae.math.script.values.ScriptValue3;
import dae.math.script.values.String1;

/**
 *
 * @author Koen Samyn
 */
public class Quat_I3D_TransformOperation extends AbstractScriptVariable implements I3DValue {

    private final IQuaternionValue quaternion;
    private final I3DValue op;
    private Double3 temp = new Double3();
    private I3DValue result;
    
    public Quat_I3D_TransformOperation(String caption, IQuaternionValue q, I3DValue op) {
        setCaption(caption);
        this.quaternion = q;
        this.op = op;
        result = new ScriptValue3();
        setVisible(false);
    }
    
    public I3DValue clone(){
        return new ScriptValue3(result);
    }

    public void calculate() {
        quaternion.transform(op.x(), op.y(), op.z(), temp);
        result.set(temp.x,temp.y,temp.z,op.w());
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
    public double w(){
        return result.w();
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public void update() {
        op.update();
        quaternion.update();
        calculate();
    }

    @Override
    public Object getValue() {
        return result;
    }

    @Override
    public boolean isVisible() {
        return result.isVisible();
    }

    @Override
    public double norm() {
        return result.norm();
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return result.getWorldPosition(w);
    }
}
