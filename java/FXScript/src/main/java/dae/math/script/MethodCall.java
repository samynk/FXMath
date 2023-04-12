package dae.math.script;

/**
 *
 * @author Koen.Samyn
 */
public class MethodCall {

    private String method;
    private ScriptValue[] parameters;

    public MethodCall(String method, ScriptValue[] parameters) {
        this.method = method;
        this.parameters = parameters;
    }
}
