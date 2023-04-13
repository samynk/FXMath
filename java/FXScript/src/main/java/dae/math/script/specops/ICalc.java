/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public interface ICalc {
    public static int COUNT = 0;
    public void update();
    public Object getValue();
    public Object getComponent(int index);
    
    
    default public boolean isOperation(){
        return false;
    }
    
    /**
     * Returns the visibility state of this ICalc node.
     * The default is visible.
     * @return true if the backing value of this ICalc object is visible, false otherwise.
     */
    default public boolean isVisible(){
        return true;
    }
    
    /**
     * Sets the icalc variable to visible or not.
     * @param visible true if this variable should be visible, false otherwise.
     */
    default public void setVisible(boolean visible){
        
    }
    
    default public String getCaption(){
        return "$var";
    }
}
