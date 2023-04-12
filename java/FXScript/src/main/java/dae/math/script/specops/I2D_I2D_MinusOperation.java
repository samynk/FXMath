/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.ScriptValueClass;
import dae.math.script.ScriptVariable;
import dae.math.script.values.ScriptValue2;
import dae.math.script.values.String1;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class I2D_I2D_MinusOperation extends AbstractScriptVariable implements I2DValue {

    private final I2DValue op1;
    private final I2DValue op2;

    private final I2DValue result;

    public I2D_I2D_MinusOperation(I2DValue op1, I2DValue op2) {
        // vector - vector : vector
        // point - vector : point
        // point - point : vector
        // vector - point :
        result = new ScriptValue2();
        this.op1 = op1;
        this.op2 = op2;
    }

    public I2D_I2D_MinusOperation(String caption, I2DValue op1, I2DValue op2) {
        setCaption(caption);
        // vector - vector : vector
        // point - vector : point
        // point - point : vector
        // vector - point :
        result = new ScriptValue2();
        this.op1 = op1;
        this.op2 = op2;
    }

    public I2DValue getOp1() {
        return op1;
    }

    public I2DValue getOp2() {
        return op2;
    }

    @Override
    public void update() {
        op1.update();
        op2.update();
        result.set(op1.x() - op2.x(), op1.y() - op2.y(), op1.w() - op2.w());
    }

    @Override
    public String getCaption() {
        return result.getCaption();
    }

    @Override
    public void setCaption(String1 caption) {
        if (result instanceof ScriptVariable sv) {
            sv.setCaption(caption.value());
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
        update();
        return result.x();
    }

    @Override
    public double y() {
        update();
        return result.y();
    }

    @Override
    public double w() {
        update();
        return result.w();
    }

    @Override
    public boolean isVisible() {
        return result.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        this.result.setVisible(visible);
    }

    @Override
    public double norm() {
        return result.norm();
    }
}
