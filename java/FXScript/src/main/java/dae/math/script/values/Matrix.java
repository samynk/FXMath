package dae.math.script.values;

import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptContext;
import dae.math.script.ScriptValueClass;
import dae.math.script.ScriptVariable;
import dae.math.script.specops.ICalc;
import dae.math.script.specops.IDouble1Value;
import java.util.Random;

/**
 *
 * @author Koen.Samyn
 */
public class Matrix extends ConstantScriptValue implements IMatrix, IArray, ICalc, ScriptVariable {

    private final IDouble1Value[] values;
    private final int rows;
    private final int columns;

    private ScriptContext scriptContext;

    private boolean showColumnNumber = false;
    private boolean showRowNumber = false;

    public Matrix(Double1 defaultValue, Double1 rows, Double1 columns) {
        this(defaultValue.value(),
                (int) rows.value(),
                (int) columns.value()
        );
    }

    public Matrix(double defaultValue, int rows, int columns) {
        values = new IDouble1Value[rows * columns];
        for (int i = 0; i < values.length; ++i) {
            values[i] = new Double1(defaultValue);
        }
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public int getNrOfRows() {
        return rows;
    }

    @Override
    public int getNrOfColumns() {
        return columns;
    }

    @Override
    public double getValue(int row, int column) {
        int index = row * columns + column;
        return values[index].getValue().value();
    }

    @Override
    public void setValue(int r, int c, double value) {
        int index = r * columns + c;
        values[index] = new Double1(value);
    }

    public void setValue(int r, int c, IDouble1Value value) {
        int index = r * columns + c;
        if (index < values.length) {
            values[index] = value;
        }
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public void setScriptContext(ScriptContext context) {
        this.scriptContext = context;
    }

    public void showColumnNumber(Boolean1 show) {
        this.showColumnNumber = show.value();
    }

    public void showRowNumber(Boolean1 show) {
        this.showRowNumber = show.value();
    }

    @Override
    public void set(int[] indices, ICalc value) {
        if (indices.length == 2 && value instanceof IDouble1Value) {
            this.setValue(indices[1], indices[0], (IDouble1Value) value);
        }
    }

    @Override
    public ICalc get(int[] indices) {
        if (indices.length == 2) {
            return this.values[indices[1] * columns + indices[0]];
        } else {
            return null;
        }
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public int dimension(int d) {
        return switch (d) {
            case 0 -> rows;
            case 1 -> columns;
            default -> 0;
        };
    }

    @Override
    public void update() {
        for (IDouble1Value value : this.values) {
            value.update();
        }
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public Object getComponent(int index) {
        return values[index];
    }

    public boolean isColumnNumberShown() {
        return this.showColumnNumber;
    }

    public boolean isRowNumberShown() {
        return this.showRowNumber;
    }

    public double getMaximum() {
        double max = -Double.MAX_VALUE;
        for (var d : this.values) {
            double v = d.getValue().value();
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    public double getMinimum() {
        double min = Double.MAX_VALUE;
        for (var d : this.values) {
            double v = d.getValue().value();
            if (v < min) {
                min = v;
            }
        }
        return min;
    }
    
    public void randomize(Double1 min, Double1 max){
        double start = min.value();
        double range = max.value()-min.value();
        Random r = new Random(System.currentTimeMillis());
        for (var value:values){
           value.getValue().set(start + r.nextDouble()*range);
        }
    }
}
