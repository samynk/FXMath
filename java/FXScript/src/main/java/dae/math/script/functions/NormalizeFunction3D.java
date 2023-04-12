package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I3DValue;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;
import dae.math.script.values.String1;

/**
 *
 * @author Koen Samyn (samyn.koen@gmail.com)
 */
public class NormalizeFunction3D extends AbstractScriptVariable implements I3DValue {

    private final I3DValue var;
    private final I3DValue result3D;

    public NormalizeFunction3D(I3DValue op) {
        this.var = op;
        result3D = new ScriptValue3();
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public void update() {
        var.update();
        I3DValue op3d = (I3DValue) var;
        double length = op3d.norm();
        result3D.set(op3d.x() / length, op3d.y() / length, op3d.z() / length, 0);
    }

    @Override
    public void setCaption(String1 caption) {
        super.setCaption(caption);
    }

    @Override
    public Object getValue() {
        return result3D;
    }

    @Override
    public void setVisible(boolean visible) {
        result3D.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return result3D.isVisible();
    }

    @Override
    public Double getComponent(int index) {
        return result3D.getComponent(index);
    }

    @Override
    public double x() {
        return result3D.x();
    }

    @Override
    public double y() {
        return result3D.y();
    }

    @Override
    public double z() {
        return result3D.z();
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return result3D.getWorldPosition(w);
    }

    @Override
    public double w() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public I3DValue clone() {
        return new ScriptValue3(result3D);
    }
}
