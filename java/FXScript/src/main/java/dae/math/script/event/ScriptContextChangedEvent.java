package dae.math.script.event;

import dae.math.script.ScriptContext;

/**
 *
 * @author Koen.Samyn
 */
public class ScriptContextChangedEvent {

    private final ScriptContext context;

    public ScriptContextChangedEvent(ScriptContext context) {
        this.context = context;
    }

    public ScriptContext getContext() {
        return context;
    }
}
