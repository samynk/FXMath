package dae.math.script.values;

import dae.math.script.FormulaOptions;
import dae.math.script.format.MathFormat;
import dae.math.script.ScriptValueClass;
import dae.math.script.specops.IBoolean1Value;

/**
 *
 * @author Koen.Samyn
 */
public class Boolean1 extends ConstantScriptValue implements IBoolean1Value {

    private boolean value;

    public Boolean1(boolean value) {
        this.value = value;
    }

    public boolean value() {
        return value;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.convert(value);
    }

    @Override
    public Boolean1 getValue() {
        return this;
    }

    @Override
    public void update() {

    }

    public void set(boolean value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        return "{" + Boolean.toString(value) +"}";
    }
}
