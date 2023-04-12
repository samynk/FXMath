
package dae.math.script.format;
/**
 *
 * @author Koen.Samyn
 */
public class SimpleColorStyleFormat extends NumberStyleFormat{
    private final static String NEGATIVE = "mathvaluenegative";
    private final static String ZERO = "mathvaluezero";
    private final static String POSITIVE = "mathvaluepositive";
            
            
    public SimpleColorStyleFormat(){
        
    }
    
    @Override
    public String getStyleFor(double number) {
        if ( number < 0 ){
            return NEGATIVE;
        }else if (number > 0 ){
            return POSITIVE;
        }else{
            return ZERO;
        }
    }
   
}
