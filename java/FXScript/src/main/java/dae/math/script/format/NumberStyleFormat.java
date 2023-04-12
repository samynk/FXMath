
package dae.math.script.format;

/**
 * This class allows the user to style a number based on ranges or conditions.
 * 
 * @author Koen.Samyn
 */
public class NumberStyleFormat {
    private String defaultStyle = "mathvalue";
    
    public static NumberStyleFormat DEFAULTSTYLE = new NumberStyleFormat();
    public static NumberStyleFormat SIMPLECOLORSTYLE = new SimpleColorStyleFormat();
    
    public NumberStyleFormat(){
        
    }
    
    public String getStyleFor(double number){
        return defaultStyle;
    }
}
