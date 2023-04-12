package dae.math.script.values;

import dae.math.script.ScriptContext;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.IDouble1Value;

/**
 *
 * @author Koen.Samyn
 */
public class Double1 extends ConstantScriptValue implements IMatrix, IDouble1Value {

    private double x;
    private MathUnit unit = MathUnit.DIMENSIONLESS;
    
    public static Double1 ZERO = new Double1(0);
    public static Double1 ONE = new Double1(1);

    public Double1() {
        x = 0;
    }

    public Double1(double value) {
        this.x = value;
    }

    public Double1(String caption, double value) {
        super.setCaption(caption);
        this.x = value;
    }

    public double value() {
        return x;
    }

    public void set(double value) {
        x = value;
    }

    public Int1 toInt() {
        return new Int1((int) Math.round(x));
    }

    @Override
    public String toString() {
        return "{" + ScriptContext.debugFormat.format(x) + "}";
    }

    @Override
    public int getNrOfRows() {
        return 1;
    }

    @Override
    public int getNrOfColumns() {
        return 1;
    }

    @Override
    public double getValue(int row, int column) {
        if (column == 0 && row == 0) {
            return x;
        } else {
            return Double.NaN;
        }
    }

    @Override
    public void setValue(int r, int c, double value) {
        if (r == 0 && c == 0) {
            x = value;
        }
    }

    public void convertToRadians() {
        if (this.unit == MathUnit.DEGREES) {
            this.x *= Math.PI / 180;
            this.unit = MathUnit.RADIANS;
        }
    }

    public void convertToDegrees() {
        if (this.unit == MathUnit.RADIANS) {
            this.x *= 180 / Math.PI;
            this.unit = MathUnit.DEGREES;
        }
    }

    /**
     * @return the unit
     */
    @Override
    public MathUnit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(MathUnit unit) {
        this.unit = unit;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.convert(x);
    }

    @Override
    public Double1 getValue() {
        return this;
    }

    @Override
    public void update() {
        // nothing to do.
    }

    @Override
    public double getX() {
        return this.x;
    }
}
