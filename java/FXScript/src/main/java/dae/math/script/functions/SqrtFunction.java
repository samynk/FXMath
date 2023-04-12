/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;
import dae.math.script.values.ConstantScriptValue;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class SqrtFunction extends MathOperation {

    public SqrtFunction(ICalc value) {
        super("sqrt", value);
    }

    @Override
    protected double calcOpValue() {
        return Math.sqrt(super.calcOpValue());
    }
}
