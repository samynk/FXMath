/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class ExpFunction extends MathOperation {
    
     public ExpFunction(ICalc value) {
        super("exp", value);
    }

    @Override
    protected double calcOpValue(){
        return Math.exp(super.calcOpValue());
    }
}
