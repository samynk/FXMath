package dae.math.script;

/**
 *
 * @author Koen.Samyn
 */
public interface ScriptVariable extends ScriptValue{
/**
     * Returns the caption of the script variable.
     * @return the caption of this script variable.
     */
    @Override
    public String getCaption();
        
    public void setScriptContext(ScriptContext context);
}
