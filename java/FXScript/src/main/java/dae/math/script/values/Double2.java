package dae.math.script.values;

import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I2DValue;

/**
 *
 * @author Koen.Samyn
 */
public class Double2 extends ConstantScriptValue implements IMatrix, I2DValue {

    public static double Distance(I2DValue start, I2DValue end) {
        return Math.hypot(end.x() - start.x(), end.y() - start.y());
    }

    public double x, y, w;

    public Double2() {
        this(0, 0);
    }

    public Double2(double x, double y) {
        this(x, y, 0);
    }

    public Double2(double x, double y, double w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double w() {
        return w;
    }

    @Override
    public void set(double x, double y, double w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public void set(I2DValue p) {
        this.x = p.x();
        this.y = p.y();
    }

    public double distance(Double2 other) {
        return Math.hypot(other.x - x, other.y - y);
    }

    public double distance(double x, double y) {
        return Math.hypot(this.x - x, this.y - y);
    }

    @Override
    public int getNrOfRows() {
        return 1;
    }

    @Override
    public int getNrOfColumns() {
        return 2;
    }

    @Override
    public double getValue(int row, int column) {
        if (row == 0) {
            switch (column) {
                case 0:
                    return x;
                case 1:
                    return y;
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
                default:
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public Double getComponent(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            default:
                return null;
        }

    }

    @Override
    public double norm() {
        return Math.hypot(x, y);
    }

    public void scale(double s) {
        this.x *= s;
        this.y *= s;
    }

    public void add(Double2 toAdd) {
        this.x += toAdd.x;
        this.y += toAdd.y;
        this.w += toAdd.w;
    }
    
    public void subtract(Double2 toSubtract){
        this.x -= toSubtract.x;
        this.y -= toSubtract.y;
        this.w -= toSubtract.w;
    }

    public double angleBetween(Double2 v2) {
        double d = this.dot(v2)/(this.norm()*v2.norm());
        double a = Math.acos(d);

        double s = this.x * v2.y - this.y * v2.x;
        if (s < 0) {
            a = -a;
        }
        return a;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
