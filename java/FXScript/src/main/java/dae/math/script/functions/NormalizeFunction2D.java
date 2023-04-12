package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I2DValue;
import dae.math.script.values.ScriptValue2;

/**
 *
 * @author Koen Samyn (samyn.koen@gmail.com)
 */
public class NormalizeFunction2D extends AbstractScriptVariable implements I2DValue {

    private I2DValue var;

    private I2DValue result2D;

    static int resultCount = 1;
    private boolean debug;

    public NormalizeFunction2D(I2DValue op) {
        this.var = op;
        result2D = new ScriptValue2();
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public void update() {
        var.update();
        double length = var.norm();
        result2D.set(var.x() / length, var.y() / length, var.w());
    }

    @Override
    public Object getValue() {
        return result2D;
    }

    @Override
    public void setVisible(boolean visible) {
        result2D.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return result2D.isVisible();
    }

    @Override
    public Double getComponent(int index) {
        return result2D.getComponent(index);
    }

    @Override
    public double x() {
        return result2D.x();
    }

    @Override
    public double y() {
        return result2D.y();
    }
    
    @Override
    public double w() {
        return result2D.w();
    }

    @Override
    public double norm() {
        return result2D.norm();
    }
}
