package dae.math.script.values;

import dae.math.script.specops.I2DValue;
import dae.math.script.specops.I3DValue;
import dae.math.script.specops.IMatrix3DValue;

/**
 * @author Koen.Samyn
 */
public class Matrix4f implements IMatrix3DValue {

    private double a11, a12, a13, a14;
    private double a21, a22, a23, a24;
    private double a31, a32, a33, a34;
    private double a41, a42, a43, a44;

    public static Matrix4f UNIT = new Matrix4f();
    private static final double EPSILON = 0.00001;

    private boolean perspectiveSet = false;
    private double pXFactor = 1;
    private double pXOffset = 0;
    private double pYFactor = 1;
    private double pYOffset = 0;
    private double pZFactor = 0;
    private double pZOffset = 1;
    private final double near = 0.5;

    /**
     * *
     * Creates a unit matrix.
     */
    public Matrix4f() {
        a11 = 1;
        a22 = 1;
        a33 = 1;
        a44 = 1;
    }

    public void reset() {
        // row1
        a11 = 1;
        a12 = 0;
        a13 = 0;
        a14 = 0;

        // row 2
        a21 = 0;
        a22 = 1;
        a23 = 0;
        a24 = 0;

        // row 3
        a31 = 0;
        a32 = 0;
        a33 = 1;
        a34 = 0;

        // row 4
        a41 = 0;
        a42 = 0;
        a43 = 0;
        a44 = 1;
    }

    public void setTranslation(double tx, double ty, double tz) {
        a14 = tx;
        a24 = ty;
        a34 = tz;
        a44 = 1;
    }

    public boolean isUnit() {
        return (Math.abs(a11 - 1.0) < EPSILON && Math.abs(a12) < EPSILON && Math.abs(a13) < EPSILON && Math.abs(a14) < EPSILON
                && Math.abs(a21) < EPSILON && Math.abs(a22 - 1.0) < EPSILON && Math.abs(a23) < EPSILON && Math.abs(a24) < EPSILON
                && Math.abs(a31) < EPSILON && Math.abs(a32) < EPSILON && Math.abs(a33 - 1.0) < EPSILON && Math.abs(a34) < EPSILON
                && Math.abs(a41) < EPSILON && Math.abs(a42) < EPSILON && Math.abs(a43) < EPSILON && Math.abs(a44 - 1.0) < EPSILON);

    }

    public void setRotation(double rz) {
        setTransform(1, 1, 1, rz, 0, 0, 0);
    }

    public void setTransform(double rz, double tx, double ty, double tz) {
        setTransform(1, 1, 1, rz, tx, ty, tz);
    }

    public void setTransform(double sx, double sy, double sz, double rz, double tx, double ty, double tz) {
        reset();
        double cr = (double) Math.cos(rz);
        double sr = (double) Math.sin(rz);
        a11 = sx * cr;
        a21 = sx * sr;
        a12 = -sy * sr;
        a22 = sy * cr;
        setTranslation(tx, ty, tz);
    }

    public void setTransform(double sX, double sY, double sZ, double rx, double ry, double rz, double tX, double tY, double tZ) {
        double sin_psi = Math.sin(rx);
        double cos_psi = Math.cos(rx);
        double sin_theta = Math.sin(ry);
        double cos_theta = Math.cos(ry);
        double sin_phi = Math.sin(rz);
        double cos_phi = Math.cos(rz);

        // cos θ cos φ          sin ψ sin θ cos φ − cos ψ sin φ         cos ψ sin θ cos φ + sin ψ sin φ
        // cos θ sin φ          sin ψ sin θ sin φ + cos ψ cos φ         cos ψ sin θ sin φ − sin ψ cos φ
        // sin θ                sin ψ cos θ                             cos ψ cos θ
        a11 = sX * cos_theta * cos_phi;
        a21 = sX * cos_theta * sin_phi;
        a31 = sX * sin_theta;
        a41 = 0;

        a12 = sY * (sin_psi * sin_theta * cos_phi - cos_psi * sin_phi);
        a22 = sY * (sin_psi * sin_theta * sin_phi + cos_psi * cos_phi);
        a32 = sY * (sin_psi * cos_theta);
        a42 = 0;

        a13 = sZ * (cos_psi * sin_theta * cos_phi + sin_psi * sin_phi);
        a23 = sZ * (cos_psi * sin_theta * sin_phi - sin_psi * cos_phi);
        a33 = sZ * (cos_psi * cos_theta);
        a43 = 0;

        setTranslation(tX, tY, tZ);
    }

    private final static Quaternion q1 = new Quaternion();
    private final static Quaternion q2 = new Quaternion();
    private final static Double3 work1 = new Double3();
    private final static Double3 work2 = new Double3();

    public void setLookAtXY(Double3 eye, Double3 lookAt) {
        //zaxis = normal(At - Eye)
        //xaxis = normal(cross(Up, zaxis))
        //yaxis = cross(zaxis, xaxis)
        //
        // xaxis.x           yaxis.x           zaxis.x          0
        // xaxis.y           yaxis.y           zaxis.y          0
        // xaxis.z           yaxis.z           zaxis.z          0
        //-dot(xaxis, eye)  -dot(yaxis, eye)  -dot(zaxis, eye)  l

//        Double3 zt = lookAt.subtract(eye, null);
//        //zt.normalize();
//
//        // for z-axis up worlds and standard camera where z-axis is lookvector
//        double angle1 = MathUtil.projectedAngle(Double3.UNIT_Z, Double3.UNIT_Y_NEG, zt);
//        q1.setFromAxisRad(0, 0, 1, angle1);
//
//        q1.transform(Double3.UNIT_X, work1);
//
//        double angle2 = MathUtil.projectedAngle(work1, Double3.UNIT_Z, zt);
//        q2.setFromAxisRad(work1, angle2);
//        q2.mul(q1);
//        setViewRotation(q2);
//
//        a14 = -eye.dot(a11, a12, a13);
//        a24 = -eye.dot(a21, a22, a23);
//        a34 = -eye.dot(a31, a32, a33);
//
//        a41 = 0;
//        a42 = 0;
//        a43 = 0;
//        a44 = 1;
        setLookAt(eye, lookAt, Double3.UNIT_Z);
    }

    public void setLookAt(Double3 eye, Double3 lookAt, Double3 upVector) {
        //zaxis = normal(At - Eye)
        //xaxis = normal(cross(Up, zaxis))
        //yaxis = cross(zaxis, xaxis)
        //
        // xaxis.x           yaxis.x           zaxis.x          0
        // xaxis.y           yaxis.y           zaxis.y          0
        // xaxis.z           yaxis.z           zaxis.z          0
        //-dot(xaxis, eye)  -dot(yaxis, eye)  -dot(zaxis, eye)  l

        Double3 zt = lookAt.subtract(eye, null);
        //zt.normalize();
        Double3 backVector = Double3.UNIT_X.cross(upVector, null);
        // for z-axis up worlds and standard camera where z-axis is lookvector
        //double angle1 = MathUtil.projectedAngle(upVector, Double3.UNIT_Y_NEG, zt);
        double angle1 = MathUtil.projectedAngle(upVector, backVector, zt);
        q1.setFromAxisRad(upVector, angle1);

        q1.transform(Double3.UNIT_X, work1);

        double angle2 = MathUtil.projectedAngle(work1, upVector, zt);
        q2.setFromAxisRad(work1, angle2);
        q2.mul(q1);
        setViewRotation(q2);

        a14 = -eye.dot(a11, a12, a13);
        a24 = -eye.dot(a21, a22, a23);
        a34 = -eye.dot(a31, a32, a33);

        a41 = 0;
        a42 = 0;
        a43 = 0;
        a44 = 1;
    }

    public void unproject(double px, double py, double pz, Double3 result) {
        if (perspectiveSet) {
            double tz = (pz - pZOffset) / pZFactor;
            double tx = (px - pXOffset) * pz / pXFactor;
            double ty = (py - pYOffset) * pz / pYFactor;
            double upx = a11 * tx + a12 * ty + a13 * tz + a14;
            double upy = a21 * tx + a22 * ty + a23 * tz + a24;
            double upz = a31 * tx + a32 * ty + a33 * tz + a34;
            result.set(upx, upy, upz, 0);
        } else {
            transform(px, py, pz, result);
        }

    }

    public void transform(double x, double y, double z, Double3 result) {
        double tx = a11 * x + a12 * y + a13 * z + a14;
        double ty = a21 * x + a22 * y + a23 * z + a24;
        double tz = a31 * x + a32 * y + a33 * z + a34;

        if (perspectiveSet) {
            double tw = pZFactor * tz + pZOffset;
            result.set(pXOffset + tx * pXFactor / tw, pYOffset + ty * pYFactor / tw, tw, 1);
        } else {
            result.set(tx, ty, tz, 1);
        }
    }

    public void transform(Double3 p, Double3 result) {
        transform(p.x, p.y, p.z, result);
    }

    public void transform(Double2 p, Double3 result) {
        transform(p.x, p.y, 0, result);
    }

    public void transform(I2DValue p, Double3 result) {
        transform(p.x(), p.y(), 0, result);
    }

    public double xTransform(double x, double y, double z) {
        double tx = a11 * x + a12 * y + a13 * z + a14;
        if (perspectiveSet) {
            double tw = zTransform(x, y, z);
            return tx * pXFactor / tw + pXOffset;
        } else {
            return tx;
        }
    }

    public double xTransform(Double3 p) {
        return xTransform(p.x(), p.y(), p.z());
    }

    public double xTransform(Double2 p) {
        return xTransform(p.x(), p.y(), 0);
    }

    public double yTransform(double x, double y, double z) {
        double ty = a21 * x + a22 * y + a23 * z + a24;
        if (perspectiveSet) {
            double tw = zTransform(x, y, z);
            return ty * pYFactor / tw + pYOffset;
        } else {
            return ty;
        }
    }

    public double yTransform(Double3 p) {
        return yTransform(p.x(), p.y(), p.z());
    }

    public double yTransform(Double2 p) {
        return yTransform(p.x(), p.y(), 0);
    }

    public double zTransform(double x, double y, double z) {
        double tz = a31 * x + a32 * y + a33 * z + a34;
        if (perspectiveSet) {
            return tz * pZFactor + pZOffset;
        } else {
            return tz;
        }

    }

    public double zTransform(Double3 p) {
        return zTransform(p.x(), p.y(), p.z());
    }

    public double zTransform(Double2 p) {
        return zTransform(p.x(), p.y(), 0);
    }

    public void transformVector(Double3 v, Double3 result) {
        transformVector(v.x, v.y, v.z, result);
    }

    public void transformVector(double vx, double vy, double vz, Double3 result) {
        double tx = a11 * vx + a12 * vy + a13 * vz;
        double ty = a21 * vx + a22 * vy + a23 * vz;
        double tz = a31 * vx + a32 * vy + a33 * vz;
        result.set(tx, ty, tz, 0);
    }

    public static void multiply(IMatrix3DValue op1, IMatrix3DValue op2, IMatrix3DValue result) {
//        result.a11 = op1.a11 * op2.a11 + op1.a12 * op2.a21 + op1.a13 * op2.a31 + op1.a14 * op2.a41;
//        result.a12 = op1.a11 * op2.a12 + op1.a12 * op2.a22 + op1.a13 * op2.a32 + op1.a14 * op2.a42;
//        result.a13 = op1.a11 * op2.a13 + op1.a12 * op2.a23 + op1.a13 * op2.a33 + op1.a14 * op2.a43;
//        result.a14 = op1.a11 * op2.a14 + op1.a12 * op2.a24 + op1.a13 * op2.a34 + op1.a14 * op2.a44;
//
//        result.a21 = op1.a21 * op2.a11 + op1.a22 * op2.a21 + op1.a23 * op2.a31 + op1.a24 * op2.a41;
//        result.a22 = op1.a21 * op2.a12 + op1.a22 * op2.a22 + op1.a23 * op2.a32 + op1.a24 * op2.a42;
//        result.a23 = op1.a21 * op2.a13 + op1.a22 * op2.a23 + op1.a23 * op2.a33 + op1.a24 * op2.a43;
//        result.a24 = op1.a21 * op2.a14 + op1.a22 * op2.a24 + op1.a23 * op2.a34 + op1.a24 * op2.a44;
//
//        result.a31 = op1.a31 * op2.a11 + op1.a32 * op2.a21 + op1.a33 * op2.a31 + op1.a34 * op2.a41;
//        result.a32 = op1.a31 * op2.a12 + op1.a32 * op2.a22 + op1.a33 * op2.a32 + op1.a34 * op2.a42;
//        result.a33 = op1.a31 * op2.a13 + op1.a32 * op2.a23 + op1.a33 * op2.a33 + op1.a34 * op2.a43;
//        result.a34 = op1.a31 * op2.a14 + op1.a32 * op2.a24 + op1.a33 * op2.a34 + op1.a34 * op2.a44;
//
//        result.a41 = op1.a41 * op2.a11 + op1.a42 * op2.a21 + op1.a43 * op2.a31 + op1.a44 * op2.a41;
//        result.a42 = op1.a41 * op2.a12 + op1.a42 * op2.a22 + op1.a43 * op2.a32 + op1.a44 * op2.a42;
//        result.a43 = op1.a41 * op2.a13 + op1.a42 * op2.a23 + op1.a43 * op2.a33 + op1.a44 * op2.a43;
//        result.a44 = op1.a41 * op2.a14 + op1.a42 * op2.a24 + op1.a43 * op2.a34 + op1.a44 * op2.a44;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float value = 0;
                for (int k = 0; k < 4; k++) {
                    value += op1.getComponent(i, k) * op2.getComponent(k, j);
                }
                result.setComponent(i, j, value);
            }
        }

        if (result instanceof Matrix4f r) {
            if (op1 instanceof Matrix4f m && m.perspectiveSet) {
                copyPerspective(r, m);
            } else if (op2 instanceof Matrix4f m && m.perspectiveSet) {
                copyPerspective(r, m);
            }
        }
    }

    public static void copy(Matrix4f from, Matrix4f to) {
        to.a11 = from.a11;
        to.a12 = from.a12;
        to.a13 = from.a13;
        to.a14 = from.a14;

        to.a21 = from.a21;
        to.a22 = from.a22;
        to.a23 = from.a23;
        to.a24 = from.a24;

        to.a31 = from.a31;
        to.a32 = from.a32;
        to.a33 = from.a33;
        to.a34 = from.a34;

        to.a41 = from.a41;
        to.a42 = from.a42;
        to.a43 = from.a43;
        to.a44 = from.a44;

        copyPerspective(to, from);
    }

    private static void copyPerspective(Matrix4f to, Matrix4f from) {
        to.pXFactor = from.pXFactor;
        to.pXOffset = from.pXOffset;
        to.pYFactor = from.pYFactor;
        to.pYOffset = from.pYOffset;
        to.pZFactor = from.pZFactor;
        to.pZOffset = from.pZOffset;
        to.perspectiveSet = from.perspectiveSet;
    }

    public static void inverse(Matrix4f f, Matrix4f inv) {
        // Cofactors
        double A2323 = f.a33 * f.a44 - f.a34 * f.a43;
        double A1323 = f.a32 * f.a44 - f.a34 * f.a42;
        double A1223 = f.a32 * f.a43 - f.a33 * f.a42;
        double A0323 = f.a31 * f.a44 - f.a34 * f.a41;
        double A0223 = f.a31 * f.a43 - f.a33 * f.a41;
        double A0123 = f.a31 * f.a42 - f.a32 * f.a41;
        double A2313 = f.a23 * f.a44 - f.a24 * f.a43;
        double A1313 = f.a22 * f.a44 - f.a24 * f.a42;
        double A1213 = f.a22 * f.a43 - f.a23 * f.a42;
        double A2312 = f.a23 * f.a34 - f.a24 * f.a33;
        double A1312 = f.a22 * f.a34 - f.a24 * f.a32;
        double A1212 = f.a22 * f.a33 - f.a23 * f.a32;
        double A0313 = f.a21 * f.a44 - f.a24 * f.a41;
        double A0213 = f.a21 * f.a43 - f.a23 * f.a41;
        double A0312 = f.a21 * f.a34 - f.a24 * f.a31;
        double A0212 = f.a21 * f.a33 - f.a23 * f.a31;
        double A0113 = f.a21 * f.a42 - f.a22 * f.a41;
        double A0112 = f.a21 * f.a32 - f.a22 * f.a31;

        double det
                = //m.m00 * ( m.m11 * A2323 - m.m12 * A1323 + m.m13 * A1223 ) 
                f.a11 * (f.a22 * A2323 - f.a23 * A1323 + f.a24 * A1223)
                //- m.m01 * ( m.m10 * A2323 - m.m12 * A0323 + m.m13 * A0223 ) 
                - f.a12 * (f.a21 * A2323 - f.a23 * A0323 + f.a24 * A0223)
                //+ m.m02 * ( m.m10 * A1323 - m.m11 * A0323 + m.m13 * A0123 ) 
                + f.a13 * (f.a21 * A1323 - f.a22 * A0323 + f.a24 * A0123)
                //- m.m03 * ( m.m10 * A1223 - m.m11 * A0223 + m.m12 * A0123 ) ;
                - f.a14 * (f.a21 * A1223 - f.a22 * A0223 + f.a23 * A0123);
        det = 1 / det;

        // m00 = det *   ( m.m11 * A2323 - m.m12 * A1323 + m.m13 * A1223 ),
        inv.a11 = det * (f.a22 * A2323 - f.a23 * A1323 + f.a24 * A1223);
        // m01 = det * - ( m.m01 * A2323 - m.m02 * A1323 + m.m03 * A1223 ),
        inv.a12 = det * -(f.a12 * A2323 - f.a13 * A1323 + f.a14 * A1223);
        // m02 = det *   ( m.m01 * A2313 - m.m02 * A1313 + m.m03 * A1213 ),
        inv.a13 = det * (f.a12 * A2313 - f.a13 * A1313 + f.a14 * A1213);
        // m03 = det * - ( m.m01 * A2312 - m.m02 * A1312 + m.m03 * A1212 ),
        inv.a14 = det * -(f.a12 * A2312 - f.a13 * A1312 + f.a14 * A1212);

        // m10 = det * - ( m.m10 * A2323 - m.m12 * A0323 + m.m13 * A0223 ),
        inv.a21 = det * -(f.a21 * A2323 - f.a23 * A0323 + f.a24 * A0223);
        // m11 = det *   ( m.m00 * A2323 - m.m02 * A0323 + m.m03 * A0223 ),
        inv.a22 = det * (f.a11 * A2323 - f.a13 * A0323 + f.a14 * A0223);
        // m12 = det * - ( m.m00 * A2313 - m.m02 * A0313 + m.m03 * A0213 ),
        inv.a23 = det * -(f.a11 * A2313 - f.a13 * A0313 + f.a14 * A0213);
        // m13 = det *   ( m.m00 * A2312 - m.m02 * A0312 + m.m03 * A0212 ),
        inv.a24 = det * (f.a11 * A2312 - f.a13 * A0312 + f.a14 * A0212);

        // m20 = det *   ( m.m10 * A1323 - m.m11 * A0323 + m.m13 * A0123 ),
        inv.a31 = det * (f.a21 * A1323 - f.a22 * A0323 + f.a24 * A0123);
        // m21 = det * - ( m.m00 * A1323 - m.m01 * A0323 + m.m03 * A0123 ),
        inv.a32 = det * -(f.a11 * A1323 - f.a12 * A0323 + f.a14 * A0123);
        // m22 = det *   ( m.m00 * A1313 - m.m01 * A0313 + m.m03 * A0113 ),
        inv.a33 = det * (f.a11 * A1313 - f.a12 * A0313 + f.a14 * A0113);
        // m23 = det * - ( m.m00 * A1312 - m.m01 * A0312 + m.m03 * A0112 ),
        inv.a34 = det * -(f.a11 * A1312 - f.a12 * A0312 + f.a14 * A0112);

        // m30 = det * - ( m.m10 * A1223 - m.m11 * A0223 + m.m12 * A0123 ),
        inv.a41 = det * -(f.a21 * A1223 - f.a22 * A0223 + f.a23 * A0123);
        // m31 = det *   ( m.m00 * A1223 - m.m01 * A0223 + m.m02 * A0123 ),
        inv.a42 = det * (f.a11 * A1223 - f.a12 * A0223 + f.a13 * A0123);
        // m32 = det * - ( m.m00 * A1213 - m.m01 * A0213 + m.m02 * A0113 ),
        inv.a43 = det * -(f.a11 * A1213 - f.a12 * A0213 + f.a13 * A0113);
        // m33 = det *   ( m.m00 * A1212 - m.m01 * A0212 + m.m02 * A0112 ),
        inv.a44 = det * (f.a11 * A1212 - f.a12 * A0212 + f.a13 * A0112);

        inv.pXFactor = f.pXFactor;
        inv.pXOffset = f.pXOffset;
        inv.pYOffset = f.pYOffset;
        inv.pYFactor = f.pYFactor;
        inv.pZOffset = f.pZOffset;
        inv.pZFactor = f.pZFactor;
        inv.perspectiveSet = f.perspectiveSet;
    }

    /**
     * Calculates the scale necessary to scale elements that need to maintain a
     * constant pixel size on the screen.
     *
     * @return
     */
    public double inverseXScale() {
        double xscale = Math.sqrt(a11 * a11 + a21 * a21 + a31 * a31);
        return 1 / xscale;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\n%.4f\t", a11));
        builder.append(String.format("%.4f\t", a12));
        builder.append(String.format("%.4f\t", a13));
        builder.append(String.format("%.4f\t\n", a14));

        builder.append(String.format("%.4f\t", a21));
        builder.append(String.format("%.4f\t", a22));
        builder.append(String.format("%.4f\t", a23));
        builder.append(String.format("%.4f\t\n", a24));

        builder.append(String.format("%.4f\t", a31));
        builder.append(String.format("%.4f\t", a32));
        builder.append(String.format("%.4f\t", a33));
        builder.append(String.format("%.4f\t\n", a34));

        builder.append(String.format("%.4f\t", a41));
        builder.append(String.format("%.4f\t", a42));
        builder.append(String.format("%.4f\t", a43));
        builder.append(String.format("%.4f\t\n", a44));

        return builder.toString();
    }

    public Double3 getScale() {
        double xscale = Math.sqrt(a11 * a11 + a21 * a21 + a31 * a31);
        double yscale = Math.sqrt(a12 * a12 + a22 * a22 + a32 * a32);
        double zscale = Math.sqrt(a13 * a13 + a23 * a23 + a33 * a33);
        return new Double3(xscale, yscale, zscale, 1);
    }

    public void setAsIdentity() {
        reset();
    }

    public Double3 getTranslation() {
        return new Double3(a14, a24, a34, 1);
    }

    public void getTranslation(Double3 wPos) {
        wPos.set(a14, a24, a34, 1);
    }

    public void getColumn(int index, Double3 result) {
        switch (index) {
            case 0:
                result.set(a11, a21, a31, a41);
                break;
            case 1:
                result.set(a12, a22, a32, a42);
                break;
            case 2:
                result.set(a13, a23, a33, a43);
                break;
            case 3:
                result.set(a14, a24, a34, a44);
                break;
        }
    }

    public void getRow(int index, Double3 result) {
        switch (index) {
            case 0:
                result.set(a11, a12, a13, a14);
                break;
            case 1:
                result.set(a21, a22, a23, a24);
                break;
            case 2:
                result.set(a31, a32, a33, a34);
                break;
            case 3:
                result.set(a41, a42, a43, a44);
                break;
        }
    }

    public void setRotation(Quaternion q) {
//        q.transform(Double3.UNIT_X, x);
//        q.transform(Double3.UNIT_Y, y);
//        q.transform(Double3.UNIT_Z, z);
//        

        double sqw = q.w * q.w;
        double sqx = q.x * q.x;
        double sqy = q.y * q.y;
        double sqz = q.z * q.z;
        /*
        this.a11 = 1-2*sqy-2*sqz;
        this.a12 = 2*q.x*q.y+2*q.w*q.z;
        this.a13 = 2*q.x*q.z-2*q.w*q.y;
        
        this.a21 = 2*q.x*q.y-2*q.w*q.z;
        this.a22 = 1-2*sqx-2*sqz;
        this.a23 = 2*q.y*q.z+2*q.w*q.x;
        
        this.a31 = 2*q.x*q.z+2*q.w*q.y;
        this.a32 = 2*q.y*q.z-2*q.w*q.x;
        this.a33 = 1-2*sqx-2*sqy;
         */
        // invs (inverse square length) is only required if quaternion is not already normalised

        double invs = 1 / (sqx + sqy + sqz + sqw);

        a11 = (sqx - sqy - sqz + sqw) * invs; // since sqw + sqx + sqy + sqz =1/invs*invs
        a22 = (-sqx + sqy - sqz + sqw) * invs;
        a33 = (-sqx - sqy + sqz + sqw) * invs;

        double tmp1 = q.x * q.y;
        double tmp2 = q.z * q.w;
        a21 = 2.0 * (tmp1 + tmp2) * invs;
        a12 = 2.0 * (tmp1 - tmp2) * invs;

        tmp1 = q.x * q.z;
        tmp2 = q.y * q.w;
        a31 = 2.0 * (tmp1 - tmp2) * invs;
        a13 = 2.0 * (tmp1 + tmp2) * invs;
        tmp1 = q.y * q.z;
        tmp2 = q.x * q.w;
        a32 = 2.0 * (tmp1 + tmp2) * invs;
        a23 = 2.0 * (tmp1 - tmp2) * invs;

    }

    public void setPerspective(double fov, double ar, double hw, double hh, double near, double far) {
        //reset();
        perspectiveSet = true;
        double tar = hh / hw;
        double tfov = Math.tan(fov / 2);
        this.pXFactor = -hh / (tfov);
        this.pXOffset = 0;
        this.pYFactor = -hh / (tfov);
        this.pYOffset = 0;
        this.pZFactor = 1;
        this.pZOffset = -near;
    }

    private void setViewRotation(Quaternion q) {
        double sqw = q.w * q.w;
        double sqx = q.x * q.x;
        double sqy = q.y * q.y;
        double sqz = q.z * q.z;

        // invs (inverse square length) is only required if quaternion is not already normalised
        double invs = 1 / (sqx + sqy + sqz + sqw);

        a11 = (sqx - sqy - sqz + sqw) * invs; // since sqw + sqx + sqy + sqz =1/invs*invs
        a22 = (-sqx + sqy - sqz + sqw) * invs;
        a33 = (-sqx - sqy + sqz + sqw) * invs;

        double tmp1 = q.x * q.y;
        double tmp2 = q.z * q.w;
        a12 = 2.0 * (tmp1 + tmp2) * invs;
        a21 = 2.0 * (tmp1 - tmp2) * invs;

        tmp1 = q.x * q.z;
        tmp2 = q.y * q.w;
        a13 = 2.0 * (tmp1 - tmp2) * invs;
        a31 = 2.0 * (tmp1 + tmp2) * invs;
        tmp1 = q.y * q.z;
        tmp2 = q.x * q.w;
        a23 = 2.0 * (tmp1 + tmp2) * invs;
        a32 = 2.0 * (tmp1 - tmp2) * invs;
    }

    public void setFrame(Double3 ax, Double3 ay, Double3 az) {
        a11 = ax.x;
        a21 = ax.y;
        a31 = ax.z;

        a12 = ay.x;
        a22 = ay.y;
        a32 = ay.z;

        a13 = az.x;
        a23 = az.y;
        a33 = az.z;

    }

    public void setRotationAsIdentity() {
        a11 = 1;
        a21 = 0;
        a31 = 0;

        a12 = 0;
        a22 = 1;
        a32 = 0;

        a13 = 0;
        a23 = 0;
        a33 = 1;

    }

    public void setYawPitchCamera(Double3 eye, Double3 dUpVector, double yaw, double pitch) {
        q1.setFromAxisRad(dUpVector, yaw);
        q2.setFromAxisRad(Double3.UNIT_X, pitch);

        Quaternion q = q1.mul(q2);
        Double3 vx = new Double3();
        q.transform(-1, 0, 0, vx);
        a11 = vx.x;
        a12 = vx.y;
        a13 = vx.z;
        q.transform(Double3.UNIT_Y, vx);
        a21 = vx.x;
        a22 = vx.y;
        a23 = vx.z;
        q.transform(Double3.UNIT_Z, vx);
        a31 = vx.x;
        a32 = vx.y;
        a33 = vx.z;

//setViewRotation(q2.mul(q1));
        a14 = -eye.dot(a11, a12, a13);
        a24 = -eye.dot(a21, a22, a23);
        a34 = -eye.dot(a31, a32, a33);

        a41 = 0;
        a42 = 0;
        a43 = 0;
        a44 = 1;
    }

    @Override
    public IMatrix3DValue getValue() {
        return this;
    }

    @Override
    public void setComponent(int column, Double3 value, double w) {
        setComponent(0, column, value.x);
        setComponent(1, column, value.y);
        setComponent(2, column, value.z);
        setComponent(3, column, w);
    }

    @Override
    public void setComponent(int row, int column, double value) {
        switch (row) {
            case 0: {
                switch (column) {
                    case 0:
                        a11 = value;
                        break;
                    case 1:
                        a12 = value;
                        break;
                    case 2:
                        a13 = value;
                        break;
                    case 3:
                        a14 = value;
                        break;
                }
            }
            break;
            case 1: {
                switch (column) {
                    case 0:
                        a21 = value;
                        break;
                    case 1:
                        a22 = value;
                        break;
                    case 2:
                        a23 = value;
                        break;
                    case 3:
                        a24 = value;
                        break;
                }
            }
            break;
            case 2: {
                switch (column) {
                    case 0:
                        a31 = value;
                        break;
                    case 1:
                        a32 = value;
                        break;
                    case 2:
                        a33 = value;
                        break;
                    case 3:
                        a34 = value;
                        break;
                }
            }
            break;
            case 3: {
                switch (column) {
                    case 0:
                        a41 = value;
                        break;
                    case 1:
                        a42 = value;
                        break;
                    case 2:
                        a43 = value;
                        break;
                    case 3:
                        a44 = value;
                        break;
                }
            }
            break;
        }
    }

    @Override
    public double getComponent(int row, int column) {
        return switch (row) {
            case 0 ->
                switch (column) {
                    case 0 ->
                        a11;
                    case 1 ->
                        a12;
                    case 2 ->
                        a13;
                    case 4 ->
                        a14;
                    default ->
                        Double.NaN;
                };

            case 1 ->
                switch (column) {
                    case 0 ->
                        a21;
                    case 1 ->
                        a22;
                    case 2 ->
                        a23;
                    case 3 ->
                        a24;
                    default ->
                        Double.NaN;
                };

            case 2 ->
                switch (column) {
                    case 0 ->
                        a31;
                    case 1 ->
                        a32;
                    case 2 ->
                        a33;
                    case 3 ->
                        a34;
                    default ->
                        Double.NaN;
                };
            case 3 ->
                switch (column) {
                    case 0 ->
                        a41;
                    case 1 ->
                        a42;
                    case 2 ->
                        a43;
                    case 3 ->
                        a44;
                    default ->
                        Double.NaN;
                };
            default ->
                Double.NaN;
        };
    }

    @Override
    public void transformPoint(I3DValue point, Double3 result) {
        this.transform(point, result);
    }

    @Override
    public void transformVector(I3DValue vector, Double3 result) {
        this.transform(vector, result);
    }

    @Override
    public void transform(I3DValue v, Double3 result) {
        double x = v.x();
        double y = v.y();
        double z = v.z();
        double w = v.w();
        double tx = a11 * x + a12 * y + a13 * z + a14 * w;
        double ty = a21 * x + a22 * y + a23 * z + a24 * w;
        double tz = a31 * x + a32 * y + a33 * z + a34 * w;

        if (perspectiveSet) {
            double tw = pZFactor * tz + pZOffset;
            result.set(pXOffset + tx * pXFactor / tw, pYOffset + ty * pYFactor / tw, tw, 1);
        } else {
            result.set(tx, ty, tz, w);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Object getComponent(int index) {
        return null;
    }
}
