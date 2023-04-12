/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.FormulaOptions;
import dae.math.script.MathOperation;
import dae.math.script.specops.I2DValue;
import dae.math.script.specops.IDouble1Value;
import dae.math.script.values.Double2;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class RandomRangeFunction extends MathOperation {

    private I2DValue range = new Double2(-1,1);

    public RandomRangeFunction(IDouble1Value input, I2DValue range) {
        super("random", input);
        this.range = range;
    }

    @Override
    protected double calcOpValue() {
        return context.generateRandomDouble(range.x(), range.y());
    }

}
