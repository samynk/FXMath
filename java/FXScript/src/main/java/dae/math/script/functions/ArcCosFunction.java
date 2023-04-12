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
public class ArcCosFunction extends MathOperation {

    public ArcCosFunction(ICalc value) {
        super("arccos", value);
    }

    @Override
    protected double calcOpValue(){
        return Math.acos(super.calcOpValue());
    }
}
