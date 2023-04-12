package dae.math.script;

import dae.math.script.specops.IBoolean1Value;
import dae.math.script.specops.IDouble1Value;
import dae.math.script.values.Boolean1;
import dae.math.script.values.Double1;
import dae.math.script.values.Double2;
import dae.math.script.values.MathUnit;
import dae.math.script.values.String1;

/**
 * This class offers additional infrastructure for 
 * @author Koen.Samyn
 */
public class SimpleScriptVariable extends AbstractScriptVariable {

    protected ScriptContext context;

    private boolean captionDirectionSet;
    private IDouble1Value captionAngle = new Double1(0);
    private IDouble1Value captionDistance = new Double1(15);
    // label
    private boolean labelSet = false;
    private IDouble1Value labelAngle = new Double1(Math.PI / 2);
    private IDouble1Value labelDistance = new Double1(15);
    
    private boolean visible;
    private IBoolean1Value renderControl =new Boolean1(true);
    
    public SimpleScriptVariable() {

    }

    public void setLabel(String1 label) {
        this.labelSet = true;
    }

    /**
     * Sets a label for this script variable. This label can be used for display
     * purposes in a picture.
     */
    public void setLabel(String1 label, IDouble1Value labelAngle, IDouble1Value labelDistance) {
        this.labelDistance = labelDistance;
        this.labelAngle = labelAngle;

        setLabel(label);
    }

    public boolean isLabelSet() {
        return labelSet;
    }

    public IDouble1Value getLabelAngle() {
        return this.labelAngle;
    }

    public IDouble1Value getLabelDistance() {
        return this.labelDistance;
    }

    public final void setLabelDirection(IDouble1Value angle, IDouble1Value distance) {
        this.labelAngle = angle;
        this.labelDistance = distance;
    }

    /**
     * Stores the location of the label into the provided Double2 object.
     *
     * @param location the Double2 object that stores the result.
     * @param width the width of the rectangle holding the caption.
     * @param height the height of the rectangle holding the caption.
     * @param baseline the baseline of the rectangle holding the caption.
     * @return the Double2 object with the location of the caption. If location
     *
     * is null a new object will be made.
     */
    public Double2 getLabelRelativeLoc(Double2 location, double width, double height, double baseline) {
        IDouble1Value sv_d = this.labelDistance;
        IDouble1Value sv_angle = this.labelAngle;
        return calculateRelativeLoc(sv_d, sv_angle, location, width, height, baseline);
    }

    /**
     * The angle in which to display the caption.
     *
     * @param angle the angle of the caption.
     * @param distance the direction of the caption.
     */
    public final void setCaptionDirection(IDouble1Value angle, IDouble1Value distance) {
        captionDirectionSet = true;
        this.captionAngle = angle;
        this.captionDistance = distance;
    }

    /**
     * Returns true if the caption direction was set, false otherwise.
     *
     * @return true if the caption direction was set, false otherwise.
     */
    public boolean isCaptionDirectionSet() {
        return captionDirectionSet;
    }

    /**
     * The angle of the caption.
     *
     * @return the angle of the caption.
     */
    public IDouble1Value getCaptionAngle() {
        return captionAngle;
    }
    
    public double getCaptionAngleValue(){
        if (captionAngle!=null){
            captionAngle.update();
            return captionAngle.getValue().value();
        }else{
            return 0;
        }
    }

    /**
     * The distance of the caption.
     *
     * @return the distance of the caption.
     */
    public IDouble1Value getCaptionDistance() {
        return captionDistance;
    }
    
     public double getCaptionDistanceValue(){
        if (captionDistance!=null){
            captionDistance.update();
            return captionDistance.getValue().value();
        }else{
            return 0;
        }
    }

    /**
     * Stores the location of the caption into the provided Double2 object.
     *
     * @param location the Double2 object that stores the result.
     * @param width the width of the rectangle holding the caption.
     * @param height the height of the rectangle holding the caption.
     * @param baseline the baseline of the rectangle holding the caption.
     * @return the Double2 object with the location of the caption. If location
     *
     * is null a new object will be made.
     */
    public Double2 getCaptionRelativeLoc(Double2 location, double width, double height, double baseline) {
        IDouble1Value sv_d = getCaptionDistance();
        IDouble1Value sv_angle = getCaptionAngle();
        return calculateRelativeLoc(sv_d, sv_angle, location, width, height, baseline);
    }

    public void getCaptionAngleDistance(Double2 result) {
        this.captionDistance.update();
        Double1 d = captionDistance.getValue();

        this.captionAngle.update();
        Double1 angle = captionAngle.getValue();
        double v = angle.value();
        if (angle.getUnit() == MathUnit.DEGREES) {
            v *= Math.PI / 180.0;
        }
        result.x = d.value();
        result.y = v;
    }

    private Double2 calculateRelativeLoc(IDouble1Value sv_d, IDouble1Value sv_angle, Double2 location, double width, double height, double baseline) {
        sv_d.update();
        sv_angle.update();

        Double1 d = sv_d.getValue();
        Double1 angle = sv_angle.getValue();

        if (location == null) {
            location = new Double2();
        }
        double v = angle.value();
        if (angle.getUnit() == MathUnit.DEGREES) {
            v *= Math.PI / 180.0;
        }
        double cosValue = Math.cos(-v);
        double sinValue = Math.sin(-v);
        double r = d.value();
        location.x = r * cosValue;
        location.y = r * sinValue;

        if (cosValue < 0) {
            location.x += (cosValue - width);
        }

        if (sinValue < 0) {
            location.y += (sinValue - height);
        }

        return location;

    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }
    
    public void setRenderCondition(IBoolean1Value renderControl)
    {
        this.renderControl = renderControl;
    }
    
    public void setVisible(boolean visible){
        this.visible = visible;
    }
    
    public final boolean isVisible(){
        renderControl.update();
        return visible && renderControl.getValue().value();
    }
}
