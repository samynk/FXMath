package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.values.Quaternion;

/**
 *
 * @author Rasputin
 */
public class Quat_MultiplyOperation extends AbstractScriptVariable implements IQuaternionValue {

    private final IQuaternionValue quat1;
    private final IQuaternionValue quat2;

    private final IQuaternionValue result;

    public Quat_MultiplyOperation(String id, IQuaternionValue q1, IQuaternionValue q2) {
        setCaption(id);
        result = new Quaternion( 0,0,1,0);
        quat1 = q1;
        quat2 = q2;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public IQuaternionValue getValue() {
        return result;
    }

    @Override
    public void update() {
        quat1.update();
        quat2.update();
        quat1.mul(quat2,result);
    }

    @Override
    public I3DValue getAxis() {
        return result.getAxis();
    }

    @Override
    public IDouble1Value getAngle() {
        return result.getAngle();
    }

    @Override
    public void transform(double x, double y, double z, I3DValue result) {
        this.result.transform(x,y,z,result);
        
    }

    @Override
    public void mul(IQuaternionValue op2, IQuaternionValue result) {
        result.mul(op2, result);
    }

    @Override
    public void set(double x, double y, double z, double w) {
        result.set(x,y,z,w);
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
}
