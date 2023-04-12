package dae.math.script.values;

import dae.math.script.ScriptValue;
import dae.math.script.ScriptValueClass;
/**
 *
 * @author Koen.Samyn
 */
public class Int1 extends ConstantScriptValue implements IMatrix {

    private int value;
    private MathUnit unit = MathUnit.DIMENSIONLESS;
    private ScriptValue cachedResult;

    public Int1(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public void update() {
        
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public Object getComponent(int index) {
        return this;
    }

    public void setUnit(MathUnit mathUnit) {
        this.unit = mathUnit;
    }

    @Override
    public MathUnit getUnit() {
        return unit;
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
        return this.value;
    }

    @Override
    public void setValue(int r, int c, double value) {
        this.value = (int) value;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.convert(value);
    }
    
    
}
