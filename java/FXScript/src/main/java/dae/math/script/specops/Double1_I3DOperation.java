/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.functions.Operator;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class Double1_I3DOperation extends AbstractScriptVariable implements I3DValue {

    private final IDouble1Value op1;
    private final I3DValue op2;
    private final Operator op;

    private final I3DValue result;

    public Double1_I3DOperation(String caption, IDouble1Value op1, Operator op, I3DValue op2) {
        this.op1 = op1;
        this.op2 = op2;
        this.op = op;

        result = new ScriptValue3();
    }
    
    @Override
    public I3DValue clone(){
        return new ScriptValue3(result);
    }

    public IDouble1Value getOp1() {
        return op1;
    }

    public I3DValue getOp2() {
        return op2;
    }

    public Operator getOperator() {
        return op;
    }

    @Override
    public void update() {
        op1.update();
        op2.update();
        double d1 = op1.getValue().value();

        switch (op) {
            case TIMES:
                result.set(op2.x() * d1, op2.y() * d1, op2.z() * d1, op2.w());
                break;
            case DIVIDEBY:
                result.set(op2.x() / d1, op2.y() / d1, op2.z() / d1, op2.w());
                break;
        }
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
    public double w() {
        return result.w();
    }

    @Override
    public boolean isVisible() {
        return this.result.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        this.result.setVisible(visible);
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
