/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class ArcSinFunction extends MathOperation {

    public ArcSinFunction(ICalc value) {
        super("arcsin", value);
    }

    @Override
    protected double calcOpValue() {
        return Math.asin(super.calcOpValue());
    }
}
