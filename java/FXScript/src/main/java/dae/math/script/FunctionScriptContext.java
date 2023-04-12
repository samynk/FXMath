
package dae.math.script;

import dae.math.script.specops.ICalc;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Koen.Samyn
 */
public class FunctionScriptContext extends ScriptContext{
    
    private ArrayList<ObjectMethodCall> methodCalls = new ArrayList<>();
    private ScriptContext parentContext;
    
    public FunctionScriptContext(){
        super(false);
    }

    @Override
    public ScriptValue getVariable(String name) {
        ScriptValue result = super.getVariable(name);
        if ( result == null && parentContext != null){
            return parentContext.getVariable(name);
        }else{
            return result;
        }
    }

    public void call(String method, String id, ScriptValue[] values) {
        methodCalls.add(new ObjectMethodCall(id,method,values));
    }

    public void execute(){
        for(var call: methodCalls){
            call.execute(this);
        }
    }

    public void setParentContext(ScriptContext context) {
        this.parentContext = context;
    }
}
