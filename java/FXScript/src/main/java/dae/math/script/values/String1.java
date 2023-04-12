package dae.math.script.values;

import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptContext;
import dae.math.script.ScriptValueClass;

/**
 *
 * @author Koen.Samyn
 */
public class String1 extends ConstantScriptValue {

    private final String value;
    private ScriptContext context = null;
    
    public static final String1 EMPTY = new String1("");

    public String1(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null) {
            return value.equals(other.toString());
        } else {
            return false;
        }
    }

    public void setScriptContext(ScriptContext context) {
        this.context = context;
    }
}
