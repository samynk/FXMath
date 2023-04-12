package dae.math.script;

/**
 *
 * @author Koen.Samyn
 */
public interface ScriptValue {
    /**
     * Calculate the result of this script value.
     *
     * @return the result of the calculation
     */
    /*
    public ScriptValue calculate();
    */

    /**
     * Sets a caption for this script value for use in formulas.
     *
     * @param caption the caption.
     */
    public void setCaption(String caption);

    /**
     * Returns the caption of this script value
     *
     * @return the caption of this script value.
     */
    public default String getCaption() {
        return "";
    }

    /**
     * Sets an id for this ScriptValue.
     *
     * @param id the id for this ScriptValue.
     */
    public void setId(String id);
    
    /**
     * Gets the id of this ScriptValue.
     * @return the id of this ScriptValue.
     */
    public String getId();

    /**
     * Returns the value class of the actual value.
     *
     * @return the value class.
     */
    public ScriptValueClass getValueClass();


    public default boolean isLiteral() {
        return false;
    }

    /**
     * Sets the id of the space of this variable.
     *
     * @param spaceID
     */
    public void setSpaceID(String spaceID);

    /**
     * Gets the id of the space to render this variable in.
     *
     * @return the id of the space.
     */
    public String getSpaceID();

}
