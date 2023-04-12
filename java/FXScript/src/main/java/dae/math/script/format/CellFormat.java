package dae.math.script.format;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.I2DValue;
import dae.math.script.values.String1;

/**
 * This class helps to style a table or a matrix.
 *
 * @author Koen.Samyn
 */
public class CellFormat extends AbstractScriptVariable {
    private final I2DValue from;
    private final I2DValue to;

    public CellFormat(String1 styleName, I2DValue from, I2DValue to) {
        this.from = from;
        this.to = to;
    }

    public void update() {
        from.update();
        to.update();
    }

    public int getFromRow() {
        return (int) from.x();
    }

    public int getToRow() {
        return (int) to.x();
    }

    public int getFromColumn() {
        return (int) from.y();
    }

    public int getToColumn() {
        return (int) to.y();
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }
}
