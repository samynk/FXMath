/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;
import dae.math.script.specops.IDouble1Value;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class ArcTanFunction extends MathOperation {

    private IDouble1Value x;

    public ArcTanFunction(ICalc y, ICalc x) {
        super("arctan", y);
        if (x instanceof IDouble1Value) {
            this.x = (IDouble1Value) x;
        }
    }

    public ArcTanFunction(ICalc op1) {
        super("arctan", op1);
    }

    @Override
    protected double calcOpValue() {
        if (x != null) {
            double xv = x.getValue().value();
            return Math.atan2(super.calcOpValue(), xv);
        } else {
            return Math.atan(super.calcOpValue());
        }

    }
}
