
package dae.math.script.properties;

import dae.math.script.values.Double2;
import dae.math.script.values.Double3;

/**
 * This class describes the general properties that can be in a renderfile
 * for a math slide.
 * @author Koen.Samyn
 */
public class RenderProperties {
    private String renderStyleName;
    private String boxStyleName;
    private String caption;
    private Double2 loc2D;
    private Double3 loc3D;
    /**
     * @return the renderStyleName
     */
    public String getRenderStyleName() {
        return renderStyleName;
    }

    /**
     * @param renderStyleName the renderStyleName to set
     */
    public void setRenderStyleName(String renderStyleName) {
        this.renderStyleName = renderStyleName;
    }

    /**
     * @return the boxStyleName
     */
    public String getBoxStyleName() {
        return boxStyleName;
    }

    /**
     * @param boxStyleName the boxStyleName to set
     */
    public void setBoxStyleName(String boxStyleName) {
        this.boxStyleName = boxStyleName;
    }

    /**
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return the loc2D
     */
    public Double2 getLoc2D() {
        return loc2D;
    }

    /**
     * @param loc2D the loc2D to set
     */
    public void setLoc2D(Double2 loc2D) {
        this.loc2D = loc2D;
    }

    /**
     * @return the loc3D
     */
    public Double3 getLoc3D() {
        return loc3D;
    }

    /**
     * @param loc3D the loc3D to set
     */
    public void setLoc3D(Double3 loc3D) {
        this.loc3D = loc3D;
    }
}
