/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.FormulaOptions;
import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;
import dae.math.script.specops.IDouble1Value;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class RandomFunction extends MathOperation {

    private IDouble1Value max;
    // only generate random once at start;
    private double cachedRandom;
    private boolean generated = false;

    public RandomFunction(ICalc min, ICalc max) {
        super("random", min);
        if ( max instanceof IDouble1Value){
            this.max = (IDouble1Value)max;
        }
    }

    @Override
    protected double calcOpValue() {
        if (!generated) {
            double minVal = super.calcOpValue();
            double maxVal = max!=null ? max.getValue().value() : 1;
            cachedRandom = context.generateRandomDouble(minVal, maxVal);
            generated = true;
        }
        return cachedRandom;
    }

}
