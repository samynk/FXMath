package dae.math.script.functions;

import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;

/**
 * Converts an angle to a positive value within the range [0, 2PI]
 *
 * @author Koen.Samyn
 */
public class PosAngleFunction extends MathOperation {

    public PosAngleFunction(ICalc value) {
        super("posangle", value);
    }

    @Override
    protected double calcOpValue() {
        double toConvert = super.calcOpValue();
        if (toConvert < 0) {
            toConvert = 2 * Math.PI + toConvert;
        }

        return toConvert;
    }
}
