/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptValueClass;
import dae.math.script.functions.Operator;
import dae.math.script.values.Double1;

/**
 * Operations for operands that evaluate to two Double1 objects.
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class Double1Operation extends AbstractScriptVariable implements IDouble1Value {

    private final IDouble1Value op1;
    private final IDouble1Value op2;
    private final Operator op;

    protected final Double1 result = new Double1();

    public Double1Operation(String id, IDouble1Value op1, Operator op, IDouble1Value op2) {
        super.setCaption(id);
        super.setId(id);
        this.op1 = op1;
        this.op = op;
        this.op2 = op2;
    }

    public IDouble1Value getOp1() {
        return op1;
    }

    public IDouble1Value getOp2() {
        return op2;
    }

    public Operator getOperator() {
        return op;
    }

    @Deprecated
    @Override
    public Double1 getValue() {
        return result;
    }

    @Override
    public void update() {
        op1.update();
        op2.update();
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    @Override
    public String getCaption() {
        String caption = super.getCaption();
        return caption != null ? caption : result.getCaption();
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.convert(result.value());
    }

    @Override
    public double getX() {
        return result.getX();
    }
}
