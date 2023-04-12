package dae.math.script;

import dae.math.script.specops.ICalc;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Koen.Samyn
 */
public class ObjectMethodCall {

    private String method;
    private String id;
    private ScriptValue[] parameters;

    public ObjectMethodCall(String id, String method, ScriptValue[] parameters) {
        this.id = id;
        this.method = method;
        this.parameters = parameters;
    }

    public void execute(ScriptContext context) {
        ScriptValue sv = context.getVariable(id);

        if (sv != null) {
            if (!callMethod(sv, method, parameters)) {

                if (sv instanceof ICalc) {
                    sv = (ScriptValue) ((ICalc) sv).getValue();
                    callMethod(sv, method, parameters);
                }
            }
        }
    }

    private boolean callMethod(ScriptValue object, String method, ScriptValue[] parameters) {
        Class[] parameterTypes = new Class[parameters.length];

        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i] != null) {
                parameterTypes[i] = parameters[i].getClass();
            } else {
                System.out.println("Value with index " + i + " is null for " + method + " of object " + id + ".");
            }
        }
        try {
            // System.out.println("Trying to call " + method);
            Class objectClass = object.getClass();
            ScriptMethodCache cache = ScriptContext.getScriptMethodCache(objectClass);
            Method m = cache.findMethod(method, parameterTypes);
            if (m != null) {
                m.invoke(object, (Object[]) parameters);
            } else {
                // System.out.println("Could not find " + method + " of object " + id + ".");
                return false;
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println("Could not call " + method + " of object " + id + ".");
            System.out.println("With types: ");
            for (Class c : parameterTypes) {
                System.out.println("parameter : " + c.getName());
            }
            System.out.println("And values:");
            for (Object o : parameters) {
                System.out.println("value : " + o);
            }
            Logger.getLogger(ScriptConstructor.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            return false;
        }
        return true;
    }

}
