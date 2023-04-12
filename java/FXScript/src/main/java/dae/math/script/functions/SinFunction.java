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
public class SinFunction extends MathOperation {

    public SinFunction(ICalc value) {
        super("sin", value);
    }

    @Override
    protected double calcOpValue() {
        return Math.sin(super.calcOpValue());
    }
}
