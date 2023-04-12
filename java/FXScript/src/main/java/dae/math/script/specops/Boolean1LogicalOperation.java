package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.functions.Operator;
import dae.math.script.values.Boolean1;

/**
 *
 * @author Koen.Samyn
 */
public class Boolean1LogicalOperation extends AbstractScriptVariable implements IBoolean1Value {

    private final Boolean1 result = new Boolean1(false);
    private final IBoolean1Value op1;
    private final IBoolean1Value op2;
    private final Operator op;

    public Boolean1LogicalOperation(IBoolean1Value op1, Operator op, IBoolean1Value op2) {
        this.op1 = op1;
        this.op = op;
        this.op2 = op2;
    }

    @Override
    public ScriptValueClass getValueClass() {
        if (result.value()) {
            return ScriptValueClass.TRUE;
        } else {
            return ScriptValueClass.FALSE;
        }
    }

    @Override
    public Boolean1 getValue() {
        return result;
    }

    @Override
    public void update() {
        op1.update();
        op2.update();

        switch (op) {
            case AND:
                result.set(op1.getValue().value() && op2.getValue().value());
                break;
            case OR:
                result.set(op1.getValue().value() && op2.getValue().value());
                break;
        }
    }
}
