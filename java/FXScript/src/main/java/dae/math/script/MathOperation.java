/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script;

import dae.math.script.specops.ICalc;
import dae.math.script.specops.IDouble1Value;
import dae.math.script.values.Double1;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class MathOperation extends AbstractScriptVariable implements IDouble1Value {

    protected final ICalc op;
    private final String method;

    protected ScriptContext context;
    private final Double1 cachedResult = new Double1();

    public MathOperation(String method, ICalc op) {
        this.op = op;
        this.method = method;

    }

    public final void calculate() {
        double value = calcOpValue();
        cachedResult.set(value);
    }

    @Override
    public void update() {
        if (op != null) {
            op.update();
        }
        calculate();
    }

    protected double calcOpValue() {
        if (op instanceof IDouble1Value) {
            op.update();

            return ((IDouble1Value) op).getValue().value();
        }
        return Double.NaN;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.convert(calcOpValue());
    }

    @Override
    public Double1 getValue() {
        return cachedResult;
    }

    @Override
    public double getX() {
        return cachedResult.value();
    }
}
