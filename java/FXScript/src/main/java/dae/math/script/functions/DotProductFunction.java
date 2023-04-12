package dae.math.script.functions;

import dae.math.script.MathOperation;
import dae.math.script.specops.I2DValue;
import dae.math.script.specops.I3DValue;
import dae.math.script.specops.ICalc;

/**
 *
 * @author Koen Samyn (samyn.koen@gmail.com)
 */
public class DotProductFunction extends MathOperation {

    private ICalc v2;
    private boolean is3D = false;

    public DotProductFunction(ICalc v1, ICalc v2) {
        super("dot", v1);
        this.v2 = v2;
        if (v1 instanceof I3DValue) {
            is3D = true;
        }
        setCaption("d");
    }

    public DotProductFunction(I2DValue v1, I2DValue v2) {
        super("dot", v1);
        this.v2 = v2;
        setCaption("d");
    }

    @Override
    protected double calcOpValue() {
        double x1 = (Double) op.getComponent(0);
        double x2 = (Double) v2.getComponent(0);

        double y1 = (Double) op.getComponent(1);
        double y2 = (Double) v2.getComponent(1);

        double z1 = (Double) op.getComponent(2);
        double z2 = (Double) v2.getComponent(2);

        double result = x1 * x2 + y1 * y2 + z1 * z2;
        return result;
    }

    @Override
    public boolean isOperation() {
        return true;
    }
}
