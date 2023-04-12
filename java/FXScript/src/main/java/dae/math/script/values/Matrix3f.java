package dae.math.script.values;

import dae.math.script.specops.I2DValue;

/**
 * @author Koen.Samyn
 */
public class Matrix3f {

    private double a11, a12, a13;
    private double a21, a22, a23;
    private double a31, a32, a33;

    public static Matrix3f UNIT = new Matrix3f();

    public Matrix3f() {
        a11 = 1;
        a22 = 1;
        a33 = 1;
    }

    public void setTranslation(double tx, double ty) {
        a11 = 1;
        a12 = 0;
        a21 = 0;
        a22 = 1;
        a13 = tx;
        a23 = ty;

        a31 = 0;
        a32 = 0;
        a33 = 1;
    }

    public void setRotation(double r) {
        setTransform(1, 1, r, 0, 0);
    }

    public void setTransform(double r, double tx, double ty) {
        setTransform(1, 1, r, tx, ty);
    }

    public void setTransform(double sx, double sy, double r, double tx, double ty) {
        double cr = (double) Math.cos(r);
        double sr = (double) Math.sin(r);
        a11 = sx * cr;
        a21 = sx * sr;
        a12 = -sy * sr;
        a22 = sy * cr;
        a13 = tx;
        a23 = ty;

        a31 = 0;
        a32 = 0;
        a33 = 1;
    }

    public void transform(double x, double y, Double2 result) {
        result.set(a11 * x + a12 * y + a13, a21 * x + a22 * y + a23, 1);
    }

    public void transform(Double2 p, Double2 result) {
        result.set(a11 * p.x() + a12 * p.y() + a13, a21 * p.x() + a22 * p.y() + a23, 1);
    }

    public void transform(I2DValue p, Double2 result) {
        result.set(a11 * p.x() + a12 * p.y() + p.w() * a13, a21 * p.x() + a22 * p.y() + p.w() * a23, p.w());
    }

    public void transformVector(double x, double y, Double2 result) {
        result.set(a11 * x + a12 * y, a21 * x + a22 * y, 0);
    }

    public double xTransform(double x, double y) {
        return a11 * x + a12 * y + a13;
    }

    public double xTransform(Double2 p) {
        return xTransform(p.x(), p.y());
    }

    public double yTransform(double x, double y) {
        return a21 * x + a22 * y + a23;
    }

    public double yTransform(Double2 p) {
        return yTransform(p.x(), p.y());
    }

    public static void multiply(Matrix3f op1, Matrix3f op2, Matrix3f result) {
        result.a11 = op1.a11 * op2.a11 + op1.a12 * op2.a21 + op1.a13 * op1.a31;
        result.a12 = op1.a11 * op2.a12 + op1.a12 * op2.a22 + op1.a13 * op1.a32;
        result.a13 = op1.a11 * op2.a13 + op1.a12 * op2.a23 + op1.a13 * op1.a33;

        result.a21 = op1.a21 * op2.a11 + op1.a22 * op2.a21 + op1.a23 * op1.a31;
        result.a22 = op1.a21 * op2.a12 + op1.a22 * op2.a22 + op1.a23 * op1.a32;
        result.a23 = op1.a21 * op2.a13 + op1.a22 * op2.a23 + op1.a23 * op1.a33;

        result.a31 = op1.a31 * op2.a11 + op1.a32 * op2.a21 + op1.a33 * op1.a31;
        result.a32 = op1.a31 * op2.a12 + op1.a32 * op2.a22 + op1.a33 * op1.a32;
        result.a33 = op1.a31 * op2.a13 + op1.a32 * op2.a23 + op1.a33 * op1.a33;
    }

    public static void copy(Matrix3f from, Matrix3f to) {
        to.a11 = from.a11;
        to.a12 = from.a12;
        to.a13 = from.a13;

        to.a21 = from.a21;
        to.a22 = from.a22;
        to.a23 = from.a23;

        to.a31 = from.a31;
        to.a32 = from.a32;
        to.a33 = from.a33;
    }

    public static void inverse(Matrix3f from, Matrix3f inv) {
        double d = (from.a31 * from.a12 * from.a23 - from.a31 * from.a13 * from.a22 - from.a21 * from.a12 * from.a33 + from.a21 * from.a13 * from.a32 + from.a11 * from.a22 * from.a33 - from.a11 * from.a23 * from.a32);
        double t11 = (from.a22 * from.a33 - from.a23 * from.a32) / d;
        double t12 = -(from.a12 * from.a33 - from.a13 * from.a32) / d;
        double t13 = (from.a12 * from.a23 - from.a13 * from.a22) / d;
        double t21 = -(-from.a31 * from.a23 + from.a21 * from.a33) / d;
        double t22 = (-from.a31 * from.a13 + from.a11 * from.a33) / d;
        double t23 = -(-from.a21 * from.a13 + from.a11 * from.a23) / d;
        double t31 = (-from.a31 * from.a22 + from.a21 * from.a32) / d;
        double t32 = -(-from.a31 * from.a12 + from.a11 * from.a32) / d;
        double t33 = (-from.a21 * from.a12 + from.a11 * from.a22) / d;

        inv.a11 = t11;
        inv.a12 = t12;
        inv.a13 = t13;
        inv.a21 = t21;
        inv.a22 = t22;
        inv.a23 = t23;
        inv.a31 = t31;
        inv.a32 = t32;
        inv.a33 = t33;
    }

    /**
     * Calculates the scale necessary to scale elements that need to maintain a
     * constant pixel size on the screen.
     *
     * @return
     */
    public double inverseXScale() {
        double xscale = Math.hypot(a11, a21);
        return 1 / xscale;
    }

    public double getXScale() {
        return Math.sqrt(a11 * a11 + a21 * a21);
    }
}
