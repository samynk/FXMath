package dae.math.script;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Koen.Samyn
 */
public class ScriptConstructor {

    private final String constructorName;
    private Class clazzToCreate;
    private boolean initialized = false;

    public ScriptConstructor(String constructorName, String className) {
        this.constructorName = constructorName;
        try {
            clazzToCreate = Class.forName(className);
            initialized = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScriptConstructor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getConstructorName() {
        return constructorName;
    }

    public ScriptVariable construct() {
        try {
            Constructor c = clazzToCreate.getConstructor();
            ScriptVariable result = (ScriptVariable) c.newInstance();
            return result;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ScriptConstructor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Creates a new script variable.
     *
     * @param values the values to construct the variable with.
     * @return the scriptvariable.
     */
    public ScriptVariable construct(ScriptValue... values) {
        if (values == null) {
            System.out.println("Values are null for " + constructorName);
        }
        if (initialized) {
            Class[] parameterTypes = new Class[values.length];
            int actualCount = 0;
            for (int i = 0; i < values.length; ++i) {
                if (values[i] != null) {
                    parameterTypes[i] = values[i].getClass();
                }
            }
            try {
                // System.out.println("Trying to create " + this.constructorName );
                Constructor[] cs = clazzToCreate.getConstructors();
                for (Constructor c : cs) {
                    if (c.getParameterCount() == parameterTypes.length) {
                        boolean found = true;
                        Class[] constructorTypes = c.getParameterTypes();
                        for (int i = 0; i < constructorTypes.length; ++i) {
                            if (parameterTypes[i] == null || !constructorTypes[i].isAssignableFrom(parameterTypes[i])) {
                                found = false;
                                break;
                            }
                        }
                        if (found) {
                            ScriptVariable result = (ScriptVariable) c.newInstance((Object[]) values);
                            return result;
                        }
                    }
                }

            } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                System.out.println("Could not construct : " + this.constructorName);
                Logger.getLogger(ScriptConstructor.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        return null;
    }

    public boolean isInitialized() {
        return initialized;
    }
}
