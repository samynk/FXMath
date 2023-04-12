package dae.math.script.values;

import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptValueClass;

/**
 *
 * @author Koen.Samyn
 */
public class SubMatrix extends ConstantScriptValue implements IMatrix {

    private IMatrix sourceMatrix;
    private int rows = 1;
    private int columns = 1;

    private int startRow = 0;
    private int startColumn = 0;

    private String caption;

    /**
     * '
     * Creates a sub matrix of a single cell.
     *
     * @param source the source matrix.
     * @param row the row of the cell (zero based).
     * @param column the column of the cell (zero based).
     */
    public SubMatrix(IMatrix source, int row, int column) {
        this(source, row, column, 1, 1);
    }

    /**
     * '
     * Creates a sub matrix of a range of cells
     *
     * @param source the source matrix.
     * @param startRow the row of the cell (zero based).
     * @param startColumn the column of the cell (zero based).
     * @param rows the number of rows to include.
     * @param columns the number of columns to include.
     */
    public SubMatrix(IMatrix source, int startRow, int startColumn, int rows, int columns) {
        this.sourceMatrix = source;
        this.startRow = startRow;
        this.startColumn = startColumn;
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
        return sourceMatrix.getValue(startRow + row, startColumn + column);
    }

    @Override
    public void setValue(int r, int c, double value) {
        sourceMatrix.setValue(startRow + r, startColumn + c, value);
    }

    /**
     * Sets the caption.
     *
     * @param caption the caption for this value.
     */
    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public void update() {
        this.sourceMatrix.update();
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public Object getComponent(int index) {
        return sourceMatrix.getComponent(index - startRow);
    }
}
