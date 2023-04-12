/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I3DValue;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;

/**
 * Copies an I3DValue, for example to make to same vector appear twice.
 * TODO: fix the base point setting of the vector3d.
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class CopyFunction extends AbstractScriptVariable implements I3DValue {

    private I3DValue toCopy;
    private I3DValue copy;
    private I3DValue basePoint;

    public CopyFunction(I3DValue toCopy) {
        setCaption(toCopy.getCaption());
        this.toCopy = toCopy;
        copy = toCopy.clone();
        copy.setVisible(false);
        setVisible(false);
    }
    
    public I3DValue clone(){
        return new ScriptValue3(copy);
    }

    public void setBasePoint(I3DValue basePoint) {
        this.basePoint = basePoint;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public double x() {
        return copy.x();
    }

    @Override
    public double y() {
        return copy.y();
    }

    @Override
    public double z() {
        return copy.z();
    }
    
    @Override
    public double w() {
        return copy.w();
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return copy.getWorldPosition(w);
    }

    @Override
    public double norm() {
        return copy.norm();
    }

    @Override
    public void update() {
        toCopy.update();
        copy.set(toCopy.x(), toCopy.y(), toCopy.z(),toCopy.w());
    }

    @Override
    public Object getValue() {
        return copy;
    }

    @Override
    public void setVisible(boolean visible) {
        copy.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return copy.isVisible();
    }
}
