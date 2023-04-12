package dae.gd.fxlayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Koen.Samyn
 */
public class RenderMathStyle {

    private final String name;
    private String category;

    // stroke properties.
    private boolean stroke;
    private double lineWidth = 1.0;
    private Color strokeColor;

    private boolean useDashes = false;
    private double dashes[];

    private StrokeLineCap lineCap = StrokeLineCap.BUTT;
    private double radius = 10;

    // fill properties
    private boolean fill;
    private Color fillColor;

    // font color
    private Color fontColor;

    // shape properties
    private final HashMap<String, Object> properties = new HashMap<>();

    /**
     * Optional cursor image if this renderstyle is used as a brush.
     */
    private ImageCursor brushCursorImage;
    private String brushCursorImagePath;

    public RenderMathStyle(String name) {
        this.name = name;

    }

    public RenderMathStyle(RenderMathStyle base, String name) {
        this.name = name;
        this.category = base.category;

        this.brushCursorImage = base.brushCursorImage;
        this.brushCursorImagePath = base.brushCursorImagePath;
        this.fill = base.fill;
        this.fillColor = base.fillColor;
        this.stroke = base.stroke;
        this.strokeColor = base.strokeColor;
        this.lineWidth = base.lineWidth;
        this.lineCap = base.lineCap;
        this.useDashes = base.useDashes;
        this.dashes = base.dashes;
        this.radius = base.radius;

        this.properties.putAll(base.properties);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RenderMathStyle other = (RenderMathStyle) obj;
        return Objects.equals(this.name, other.name);
    }

    public String getName() {
        return name;
    }

    /**
     * Convenience method to create a dashed stroke.
     *
     * @param strokeColor the color of the stroke
     * @param lineWidth the width of the line.
     */
    public void setSimpleStroke(Color strokeColor, double lineWidth) {
        this.strokeColor = strokeColor;
        this.stroke = true;
        this.useDashes = false;
        this.lineWidth = lineWidth;
    }

    public void setLineCap(StrokeLineCap cap) {
        this.lineCap = cap;
    }

    public StrokeLineCap getLineCap() {
        return lineCap;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * Convenience method to create a dashed stroke.
     *
     * @param strokeColor the color of the stroke
     * @param dashWidth the width of one dash (in pixels).
     * @param dashSpace the space between dashes (in pixels).
     */
    public void setDashedStroke(Color strokeColor, double dashWidth, double dashSpace) {
        this.strokeColor = strokeColor;
        this.stroke = true;
        this.useDashes = true;
        this.dashes = new double[]{dashWidth, dashSpace};
    }

    /**
     * Sets the fill to a simple fill.
     *
     * @param fillColor the fill color.
     * @param alpha the alpha value for the fill.
     */
    public void setSimpleFill(Color fillColor, double alpha) {
        this.fill = true;
        this.fillColor = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), alpha);
    }

    public void setSimpleFill(Color fill) {
        this.fill = true;
        this.fillColor = fill;
    }
    
    public void resetFill() {
        fill = false;
        fillColor = null;
    }

    /**
     * Activates this render math style in the rendering.
     *
     * @param gc the graphics context
     */
    public void activateStroke(GraphicsContext gc) {
        if (stroke) {
            //gc.setGlobalAlpha(alphaStroke);
            gc.setStroke(strokeColor);
            if (useDashes) {
                gc.setLineDashes(dashes);
            } else {
                gc.setLineDashes(null);
            }
            gc.setLineWidth(this.lineWidth);
            gc.setLineCap(lineCap);
        }

    }

    public void activateFill(GraphicsContext gc) {
        if (fill) {
            //gc.setGlobalAlpha(alphaFill);
            gc.setFill(fillColor);

        }
    }

    public void activateFill(GraphicsContext gc, double intensity) {
        if (fill) {
            Color fillColor2 = fillColor.deriveColor(1, 1, Math.max(0, intensity), 1);
            gc.setFill(fillColor2);
        }
    }

    public void setProperty(String name, Object o) {
        properties.put(name, o);
    }

    public double getDoubleProperty(String name) {
        Object o = properties.get(name);
        if (o != null && o instanceof Double) {
            return (Double) o;
        }
        return 0.0;
    }

    public boolean getBooleanProperty(String name) {
        Object o = properties.get(name);
        if (o != null && o instanceof Boolean) {
            return (Boolean) o;
        }
        return false;
    }

    public String getStringProperty(String name) {
        Object o = properties.get(name);
        if (o != null && o instanceof String) {
            return (String) o;
        }
        return "";
    }

    public Iterable<String> getPropertyKeys() {
        return properties.keySet();
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public boolean hasProperties() {
        return properties.size() > 0;
    }

    /**
     * @return the brushCursorImage
     */
    public ImageCursor getBrushCursorImage() {
        return brushCursorImage;
    }

    /**
     * @param brushCursorImage the brushCursorImage to set
     */
    public void setBrushCursorImage(ImageCursor brushCursorImage) {
        this.brushCursorImage = brushCursorImage;
    }

    public void setBrushCursorImagePath(String path) {
        brushCursorImagePath = path;
        setBrushCursorImage(new ImageCursor(new Image(path)));
    }

    public String getBrushCursorImagePath() {
        return brushCursorImagePath;
    }

    public boolean hasBrushCursorImage() {
        return brushCursorImage != null;

    }

    public boolean isStrokeStyle() {
        return stroke;
    }

    public boolean isFillStyle() {
        return fill;
    }

    public void setStrokeColor(Color stroke) {
        this.strokeColor = stroke;
        this.stroke = true;
    }

    public Color getStrokeColor() {
        return this.strokeColor;
    }

    public String getStrokeColorAsHex() {
        return getColorAsHex(strokeColor);
    }

    public String getFillColorAsHex() {
        return getColorAsHex(fillColor);
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public boolean hasFontColor() {
        return fontColor != null;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontColorAsHex() {
        return getColorAsHex(fontColor);
    }

    public Color getFontColor() {
        return fontColor;
    }

    private String getColorAsHex(Color color) {
        return String.format("#%02X%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                (int) (color.getOpacity() * 255));
    }

    public double getLineWidth() {
        return this.lineWidth;
    }

    public boolean usesDashes() {
        return useDashes;
    }

    public double[] getDashes() {
        return dashes;
    }

    public void setLineDashes(ArrayList<Double> dashes) {
        useDashes = true;
        this.dashes = new double[dashes.size()];
        for (int i = 0; i < dashes.size(); ++i) {
            this.dashes[i] = dashes.get(i);
        }
    }

    void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    

}
