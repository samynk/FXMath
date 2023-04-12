/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptValueClass;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;
import dae.math.script.values.String1;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class I3D_I3D_PlusOperation extends AbstractScriptVariable implements I3DValue {

    private final I3DValue op1;
    private final I3DValue op2;

    private I3DValue result;

    private Double3 av1 = new Double3();
    private Double3 av2 = new Double3();

    public I3D_I3D_PlusOperation(String caption, I3DValue op1, I3DValue op2) {
        setCaption(caption);
        result = new ScriptValue3();
        this.op1 = op1;
        this.op2 = op2;
    }
    
    @Override
    public I3DValue clone(){
        return new ScriptValue3(result);
    }

    public I3DValue getOp1() {
        return op1;
    }

    public I3DValue getOp2() {
        return op2;
    }

    @Override
    public void update() {
        op1.update();
        op2.update();

        result.set(op1.x() + op2.x(), op1.y() + op2.y(), op1.z() + op2.z(), op1.w() + op2.w());
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public Object getValue() {
        return result;
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
    public double norm() {
        return result.norm();
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return result.getWorldPosition(w);
    }
}
