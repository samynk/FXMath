package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptValueClass;
import dae.math.script.functions.Operator;
import dae.math.script.values.Boolean1;

/**
 *
 * @author Koen.Samyn
 */
public class Double1CompareOperation extends AbstractScriptVariable implements IBoolean1Value {

    private final Boolean1 result = new Boolean1(false);
    private final IDouble1Value op1;
    private final IDouble1Value op2;
    private final Operator op;

    public Double1CompareOperation(IDouble1Value op1, Operator op, IDouble1Value op2) {
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

        double v1 = op1.getValue().value();
        double v2 = op2.getValue().value();
        
        switch (op) {
            case EQ:
                result.set(v1 == v2);
                break;// todo compare with margin
            case NEQ:
                result.set(v1 != v2);
                break;
            case LT:
                result.set(v1<v2);
                break;
            case GT:
                result.set(v1>v2);
                break;
            case LTEQ:
                result.set(v1<= v2);
                break;
            case GTEQ:
                result.set(v1>= v2);
                break;
        }
    }
}
