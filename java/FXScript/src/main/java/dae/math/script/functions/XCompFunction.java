package dae.math.script.functions;

import dae.math.script.MathOperation;
import dae.math.script.specops.ICalc;

/**
 *
 * @author Koen.Samyn
 */
public class XCompFunction extends MathOperation {

    private final ICalc component;
    private final int index;
    private static final String[] names = {"x", "y", "z", "w"};

    public XCompFunction(ICalc component, int index) {
        super(names[index], component);
        this.component = component;
        this.index = index;
    }

    @Override
    protected double calcOpValue() {
        return (Double) component.getComponent(this.index);
    }
}
