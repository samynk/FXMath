package dae.math.script.values;

import dae.math.script.format.MathFormat;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I3DValue;

/**
 *
 * @author Koen.Samyn
 */
public class Double3 extends ConstantScriptValue implements IMatrix, I3DValue {

    public static final Double3 UNIT_X = new Double3(1, 0, 0, 0);
    public static final Double3 UNIT_Y = new Double3(0, 1, 0, 0);
    public static final Double3 UNIT_Z = new Double3(0, 0, 1, 0);

    public static final Double3 UNIT_X_NEG = new Double3(-1, 0, 0, 0);
    public static final Double3 UNIT_Y_NEG = new Double3(0, -1, 0, 0);
    public static final Double3 UNIT_Z_NEG = new Double3(0, 0, -1, 0);
    public static final Double3 ZERO = new Double3();

    public static void lico(double y, Double3 a, double d, Double3 b, Double3 u) {
        u.x = y * a.x + d * b.x;
        u.y = y * a.y + d * b.y;
        u.z = y * a.z + d * b.z;
    }

    public static void createNormal(Double3 a, Double3 b, Double3 c, Double3 normal) {
        double dx1 = b.x - a.x;
        double dy1 = b.y - a.y;
        double dz1 = b.z - a.z;

        double dx2 = c.x - a.x;
        double dy2 = c.y - a.y;
        double dz2 = c.z - a.z;

        // w coordinate is 0 for vector.
        normal.set(dy1 * dz2 - dz1 * dy2,
                dz1 * dx2 - dx1 * dz2,
                dx1 * dy2 - dy1 * dx2,
                0);
        normal.normalize();
    }

    public double x, y, z, w;

    public Double3() {
        this(0, 0, 0, 0);
    }

    public Double3(Double3 c) {
        this(c.x, c.y, c.z, 0);
    }

    public Double3(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public I3DValue clone() {
        return new Double3(x, y, z, w);
    }

    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double w() {
        return w;
    }

    @Override
    public void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double distance(Double3 other) {
        double dx = (other.x - x);
        double dy = (other.y - y);
        double dz = (other.z - z);
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distance(double x, double y, double z) {
        double dx = (x - this.x);
        double dy = (y - this.y);
        double dz = (z - this.z);
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static double Distance(I3DValue op1, I3DValue op2) {
        double dx = op1.x() - op2.x();
        double dy = op1.y() - op2.y();
        double dz = op1.y() - op2.y();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Double3 subtract(Double3 op, Double3 result) {
        if (result == null) {
            result = new Double3();
        }
        result.set(x - op.x, y - op.y, z - op.z, w - op.w);
        return result;
    }

    public Double3 add(Double3 op, Double3 result) {
        if (result == null) {
            result = new Double3();
        }
        result.set(x + op.x, y + op.y, z + op.z, w + op.w);
        return result;
    }

    public Double3 cross(Double3 op, Double3 result) {
        if (result == null) {
            result = new Double3();
        }
        // result is a vector.
        result.set(y * op.z - z * op.y,
                z * op.x - x * op.z,
                x * op.y - y * op.x,
                0);
        return result;
    }

    public double dot(Double3 op) {
        return x * op.x + y * op.y + z * op.z + w * op.w;
    }

    public void scale(double scaleFactor) {
        x *= scaleFactor;
        y *= scaleFactor;
        z *= scaleFactor;
    }

    public void normalize() {
        double length = Math.sqrt(x * x + y * y + z * z);
        scale(1 / length);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    @Override
    public int getNrOfRows() {
        return 1;
    }

    @Override
    public int getNrOfColumns() {
        return 4;
    }

    @Override
    public double getValue(int row, int column) {
        if (row == 0) {
            switch (column) {
                case 0:
                    return x;
                case 1:
                    return y;
                case 2:
                    return z;
                case 3:
                    return w;
                default:
                    return Double.NaN;
            }
        } else {
            return Double.NaN;
        }
    }

    @Override
    public void setValue(int r, int c, double value) {
        if (r == 0) {
            switch (c) {
                case 0:
                    x = value;
                    break;
                case 1:
                    y = value;
                    break;
                case 2:
                    z = value;
                    break;
                case 3:
                    w = value;
                default:
            }
        }
    }

    @Override
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void set(Double3 toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
    }

    public void set(I3DValue toCopy) {
        x = toCopy.x();
        y = toCopy.y();
        z = toCopy.z();
    }

    public void add(Double3 toAdd) {
        x += toAdd.x;
        y += toAdd.y;
        z += toAdd.z;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public Double3 add(double xVal, double yVal, double zVal, Double3 result) {
        if (result == null) {
            result = new Double3();
        }
        result.set(x + xVal, y + yVal, z + zVal, w);
        return result;
    }

    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static double distance(Double3 op1, Double3 op2) {
        return op1.distance(op2);
    }

    public void subtract(Double3 toSubtract) {
        x -= toSubtract.x;
        y -= toSubtract.y;
        z -= toSubtract.z;
        w -= toSubtract.w;
    }

    public void subtract(double x, double y, double z, Double3 result) {
        result.set(this.x - x, this.y - y, this.z - z, this.w);
    }

    @Override
    public String toString(MathFormat df) {
        return "[" + df.format(x) + "," + df.format(y) + "," + df.format(z) + "]";
    }

    public void scale(double td, Double3 result) {
        result.set(x * td, y * td, z * td, w);
    }

    public void neg() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
    }

    double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }

    @Override
    public Double3 getWorldPosition(Double3 w) {
        return this;
    }

    @Override
    public void update() {
        // literal value, nothing to update.
    }

    @Override
    public Object getValue() {
        return this;
    }
}
