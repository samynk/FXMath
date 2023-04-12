
package dae.gd.fxlayout;

import java.util.HashMap;
import java.util.Map;

/**
 *  Provides a way to set font sizes for a document in different circumstance
 * (e.g. web, presentation, pdf, ...)
 * 
 * @author Koen.Samyn
 */
public class FontSizes {
    // first key is the base size of the font (the point size of the 'normal' font).
    // second key is the font size name , for example, miniscule, small, large , ...
    private Map<Integer,Map<String,Integer>> fontSizeMap = new HashMap<Integer,Map<String,Integer>>();
    
    public void addFontSize(int baseFont, String fontSizeName, int fontSize)
    {
        var specificFontSizeMap= fontSizeMap.get(baseFont);
        if ( specificFontSizeMap == null){
            specificFontSizeMap = new HashMap<String,Integer>();
            fontSizeMap.put(baseFont, specificFontSizeMap);
        }
        specificFontSizeMap.put(fontSizeName, fontSize);
    }
    
    public int getFontSize(int baseFont, String fontSizeName){
        var specificFontSizeMap= fontSizeMap.get(baseFont);
        if (specificFontSizeMap.containsKey(fontSizeName)){
            return specificFontSizeMap.get(fontSizeName);
        }else{
            return 12;
        }
    }
}
