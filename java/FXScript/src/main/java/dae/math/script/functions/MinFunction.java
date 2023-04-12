package dae.math.script.functions;

import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;
import dae.math.script.specops.IDouble1Value;

/**
 *
 * @author Koen.Samyn
 */
public class MinFunction extends MathOperation {

    private ICalc op2;

    public MinFunction(ICalc op1, ICalc op2) {
        super("min", op1);
        this.op2 = op2;
    }

    @Override
    protected double calcOpValue() {
        op.update();
        op2.update();
        if (op instanceof IDouble1Value dop1 && op2 instanceof IDouble1Value dop2) {
            return Math.min(dop1.getValue().value(), dop2.getValue().value());
        } else {
            return -1.0;
        }

    }
}
