/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.values;

import dae.math.script.ScriptValue;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public abstract class ConstantScriptValue implements ScriptValue {

    private String id;
    private String caption;
    private String spaceID;

    private final boolean literal = true;

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setCaption(String1 caption) {
        this.caption = caption.value();
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isLiteral() {
        return literal;
    }

    /**
     * @return the spaceID
     */
    @Override
    public String getSpaceID() {
        return spaceID;
    }

    /**
     * @param spaceID the spaceID to set
     */
    @Override
    public void setSpaceID(String spaceID) {
        this.spaceID = spaceID;
    }
}
