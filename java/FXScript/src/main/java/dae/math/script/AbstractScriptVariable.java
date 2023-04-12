/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script;

import dae.math.script.values.String1;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public abstract class AbstractScriptVariable implements ScriptVariable {

    private String id;
    private String caption;
    private ScriptContext scriptContext;
    private String spaceID;

    private boolean literal = false;

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public final void setId(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    /**
     * @return the caption
     */
    @Override
    public String getCaption() {
        return caption;
    }

    private Pattern p = Pattern.compile("(\\p{L}+)(\\d+)");

    /**
     * @param caption the caption to set
     */
    @Override
    public final void setCaption(String caption) {
        if (caption != null) {
            Matcher m = p.matcher(caption);
            if (m.matches()) {
                this.caption = m.group(1) + "_" + m.group(2);
            } else {
                this.caption = caption;
            }
        }
    }

    public void setCaption(String1 caption) {
        setCaption(caption.value());
    }
    
    @Override
    public final void setScriptContext(ScriptContext sc) {
        this.scriptContext = sc;
    }

    /**
     * Checks if this is a literal value.
     *
     * @return true if the value is a literal, false otherwise.
     */
    @Override
    public final boolean isLiteral() {
        return literal;
    }

    /**
     * @return the spaceID
     */
    @Override
    public final String getSpaceID() {
        return spaceID;
    }

    /**
     * @param spaceID the spaceID to set
     */
    @Override
    public final  void setSpaceID(String spaceID) {
        this.spaceID = spaceID;
    }

}
