package dae.math.script.functions;

import dae.math.script.MathOperation;
import dae.math.script.specops.I2DValue;
import dae.math.script.specops.I3DValue;
import dae.math.script.specops.ICalc;

/**
 *
 * @author Koen Samyn (samyn.koen@gmail.com)
 */
public class LengthFunction extends MathOperation {

    private final ICalc var;

    public LengthFunction(ICalc op) {
        super("length", op);
        this.var = op;
    }

    @Override
    protected double calcOpValue() {
        op.update();
        if (this.op instanceof I3DValue v) {
            return v.norm();
        } else if (this.op instanceof I2DValue v) {
            return v.norm();
        }
        return 0.0;
    }
}
