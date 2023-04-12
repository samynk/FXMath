/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValue;
import dae.math.script.ScriptValueClass;
import dae.math.script.values.Double1;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class Double1Property extends AbstractScriptVariable implements IDouble1Value {

    private Double1 value = new Double1();

    private ScriptValue sourceObject;
    private Method methodToCall;

    public Double1Property(String caption, ScriptValue object, String property) {
        setCaption(property);
        try {
            // a method with the property name must be callable on object
            // and return a double or a Double1.
            Method m = object.getClass().getMethod(property, (Class<?>[]) null);
            if (m != null) {
                Class c = m.getReturnType();
                if (c == Double.class || c == double.class || c == Double1.class) {
                    methodToCall = m;
                    sourceObject = object;
                }
            }
            
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Double1Property.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.convert(value.value());
    }

    @Override
    public Double1 getValue() {
        return value;
    }

    @Override
    public void update() {
        try {
            if (methodToCall != null) {
                Object result = methodToCall.invoke(sourceObject);
                if (result instanceof Double) {
                    value.set((double) result);
                } else if (result instanceof Double1) {
                    Double1 val = (Double1) result;
                    value.set(val.value());
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Double1Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double getX() {
        return value.getX();
    }
}
