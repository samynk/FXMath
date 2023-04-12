/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.functions;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptContext;

import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I3DValue;
import dae.math.script.specops.IDouble1Value;
import dae.math.script.values.Boolean1;
import dae.math.script.values.Double3;
import dae.math.script.values.ScriptValue3;
import dae.math.script.values.String1;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class IntersectFunction extends AbstractScriptVariable implements I3DValue {

    private I3DValue boxCenter;
    private IDouble1Value boxWidth;
    private I3DValue rayStart;
    private I3DValue rayDir;

    private I3DValue result;
    private final Double3 dv = new Double3();
    private final Double3 p0 = new Double3();
    private final Double3 pv = new Double3();
    private final Double3 l0 = new Double3();
    private final Double3 lv = new Double3();

    public IntersectFunction(I3DValue boxCenter, IDouble1Value boxWidth, I3DValue rayStart, I3DValue rayDir) {
        this.boxCenter = boxCenter;
        this.boxWidth = boxWidth;
        this.rayStart = rayStart;
        this.rayDir = rayDir;
        result = new ScriptValue3();
    }
    
    public I3DValue clone(){
        return new ScriptValue3(result);
    }

    @Override
    public void update() {
        result.set(0, 0, 0, 1);
        boxCenter.update();
        boxWidth.update();
        rayStart.update();
        rayDir.update();

        double cx = boxCenter.x();
        double cy = boxCenter.y();
        double cz = boxCenter.z();
        double bw = boxWidth.getX() / 2.0;

        l0.set(rayStart.x(), rayStart.y(), rayStart.z(), 1);
        lv.set(rayDir.x(), rayDir.y(), rayDir.z(), 0);

        double t = Double.MAX_VALUE;

        // side1
        t = checkT(t, cx + bw, cy, cz, 1, 0, 0);
        t = checkT(t, cx - bw, cy, cz, -1, 0, 0);
        t = checkT(t, cx, cy + bw, cz, 0, 1, 0);
        t = checkT(t, cx, cy - bw, cz, 0, -1, 0);
        t = checkT(t, cz, cy, cz + bw, 0, 0, 1);
        t = checkT(t, cz, cy, cz - bw, 0, 0, -1);

        lv.scale(t);
        l0.add(lv);
        result.set(l0.x, l0.y, l0.z, 1);

    }

    private double checkT(double currentT, double p0x, double p0y, double p0z, double pvx, double pvy, double pvz) {
        p0.set(p0x, p0y, p0z, 1);
        pv.set(pvx, pvy, pvz, 1);
        double tt = calcT(p0, pv, l0, lv);

        return tt < currentT ? tt : currentT;
    }

    private double calcT(Double3 p0, Double3 pv, Double3 l0, Double3 lv) {
        p0.subtract(l0, dv);
        double num = dv.dot(pv);
        double denom = lv.dot(pv);
        double t = num / denom;
        if (t < 0) {
            return Double.MAX_VALUE;
        } else {
            return t;
        }
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public double x() {
        return result.x();
    }

    @Override
    public double y() {
        return result.y();
    }

    @Override
    public double z() {
        return result.z();
    }

    @Override
    public double w() {
        return result.w();
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return w;
    }

    @Override
    public I3DValue getValue() {
        return result;
    }
}
