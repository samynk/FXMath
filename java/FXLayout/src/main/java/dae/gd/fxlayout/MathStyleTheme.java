package dae.gd.fxlayout;

import dae.math.values.Double2;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Koen.Samyn
 */
public class MathStyleTheme {

    private final HashMap<String, MathStyle> styles = new HashMap<>();
    private MathStyle defaultStyle;
    private final HashMap<String, RenderMathStyle> renderStyles = new HashMap<>();
    private RenderMathStyle defaultRenderStyle;
    private final HashMap<String, Image> icons = new HashMap<>();
    private final HashMap<String, Color> colors = new HashMap<>();
    private final HashMap<String, String> fontFamilies = new HashMap<>();
    private final FontSizes fontSizes = new FontSizes();
    private int fontBaseSize = 10;

    private final HashMap<String, ArrayList<RenderMathStyle>> renderStyleCategoryMap = new HashMap<>();

    public static MathStyleTheme instance = new MathStyleTheme();

    private static boolean initialized = false;

    private String name = "default";
    private String cssFile = "fxmath/styles/jfxdefaulttheme.css";

    public MathStyleTheme() {

    }

    public MathStyleTheme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addColor(String key, Color c) {
        colors.put(key, c);
    }

    public Color getColor(String key) {
        if (colors.containsKey(key)) {
            return colors.get(key);
        } else {
            try {
                return Color.web(key);
            } catch (IllegalArgumentException ex) {
                System.out.println("could not parse : " + key);
            }
        }
        return Color.FUCHSIA;
    }

    public void addFontFamily(String key, String fontFamily) {
        fontFamilies.put(key, fontFamily);
    }

    public String getFontFamily(String key) {
        return fontFamilies.get(key);
    }

    public void addFontSize(int baseSize, String sizeName, int fontSize) {
        this.fontSizes.addFontSize(baseSize, sizeName, fontSize);
    }

    public int getFontSize(String sizeName) {
        return fontSizes.getFontSize(this.fontBaseSize, sizeName);
    }
    

    private RenderMathStyle createRenderMathStyle(String styleName, Color c1, double lineWidth, Color c2, double alpha2) {
        RenderMathStyle rs = new RenderMathStyle(styleName);
        rs.setSimpleStroke(c1, lineWidth);
        rs.setSimpleFill(c2, alpha2);
        renderStyles.put(styleName, rs);
        return rs;
    }

    private RenderMathStyle createRenderMathStrokeStyle(String styleName, Color c1, double lineWidth) {
        RenderMathStyle rs = new RenderMathStyle(styleName);
        rs.setSimpleStroke(c1, lineWidth);
        renderStyles.put(styleName, rs);
        return rs;
    }

    public void addMathStyle(MathStyle style) {
        styles.put(style.getStyleName(), style);
    }

    public MathStyle getMathStyle(String name) {
        MathStyle style = styles.get(name);
        if (style != null) {
            return style;
        } else {
            return defaultStyle;
        }
    }

    void setDefaultMathStyle(String styleName) {
        defaultStyle = styles.get(styleName);
    }

    public void addRenderMathStyle(RenderMathStyle style) {
        renderStyles.put(style.getName(), style);
        var styleList = renderStyleCategoryMap.get(style.getCategory());
        if (styleList == null) {
            styleList = new ArrayList<>();
            renderStyleCategoryMap.put(style.getCategory(), styleList);
        }
        if (!styleList.contains(style)) {
            styleList.add(style);
        }
    }

    public Set<String> getRenderStyleCategories() {
        return renderStyleCategoryMap.keySet();
    }

    public ArrayList<RenderMathStyle> getRenderStylesInCategory(String category) {
        return renderStyleCategoryMap.get(category);
    }

    public RenderMathStyle getRenderMathStyle(String name) {
        RenderMathStyle style = renderStyles.get(name);
        if (style != null) {
            return style;
        } else {
            return defaultRenderStyle;
        }
    }
    
    public RenderMathStyle getRenderMathStyleRaw(String name) {
        return renderStyles.get(name);
    }

    public void setDefaultRenderMathStyle(String def) {
        this.defaultRenderStyle = this.renderStyles.get("default");
    }

    public void addIcon(String name, String iconPath) {
        this.icons.put(name, new Image(iconPath));
    }

    public Image getIcon(String name) {
        return icons.get(name);
    }

    public void exportAsJson() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("renderstyles.json", "UTF-8");
            json(writer, "renderstyles");
            writer.write(":\n");
            writer.write("[\n");

            for (RenderMathStyle rms : renderStyles.values()) {
                writer.write("{\n");
                if (rms.isStrokeStyle()) {
                    json(writer, "strokeColor", rms.getStrokeColorAsHex(), true);
                    double[] dashes = rms.getDashes();
                    if (dashes != null) {
                        json(writer, "lineDashes", dashes, true);
                    }
                    json(writer, "lineCap", rms.getLineCap().toString(), true);
                    json(writer, "lineWidth", rms.getLineWidth(), true);
                }

                if (rms.isFillStyle()) {
                    json(writer, "fillColor", rms.getFillColorAsHex(), true);
                }
                if (rms.hasProperties()) {
                    json(writer, "properties");
                    writer.write(":{\n");
                    for (String key : rms.getPropertyKeys()) {
                        Object value = rms.getProperty(key);
                        json(writer, key, value, true);
                    }
                    writer.write("}\n,");
                }
                if (rms.hasBrushCursorImage()) {
                    json(writer, "brushCursorImagePath", rms.getBrushCursorImagePath(), true);
                }
                json(writer, "name", rms.getName(), false);

                writer.write("}");
                writer.write(",\n");
            }

            writer.write("]\n");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(MathStyleTheme.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MathStyleTheme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void json(Writer w, String toEscape) throws IOException {
        w.write('"');
        w.write(toEscape);
        w.write('"');
    }

    private void json(Writer w, String key, String value, boolean comma) throws IOException {
        json(w, key);
        w.write(":");
        json(w, value);
        if (comma) {
            w.write(",");
        }
        w.write("\n");
    }

    private void json(Writer w, String key, Object value, boolean comma) throws IOException {
        json(w, key);
        w.write(":");
        if (value instanceof Double) {
            w.write(Double.toString((Double) value));
        } else if (value instanceof Integer) {
            w.write(Integer.toString((Integer) value));
        } else if (value instanceof Boolean) {
            w.write(Boolean.toString((Boolean) value));
        } else {
            json(w, value.toString());
        }

        if (comma) {
            w.write(",");
        }
        w.write("\n");
    }

    private void json(Writer w, String key, double value, boolean comma) throws IOException {
        json(w, key);
        w.write(":");
        w.write(Double.toString(value));
        if (comma) {
            w.write(",");
        }
        w.write("\n");
    }

    private void json(Writer w, String key, double[] value, boolean comma) throws IOException {
        json(w, key);
        w.write(":[");
        for (int i = 0; i < value.length; ++i) {
            w.write(Double.toString(value[i]));
            if (i < value.length - 1) {
                w.write(",");
            }
        }
        w.write("]");
        if (comma) {
            w.write(",");
        }
        w.write("\n");
    }

    public void adjustScaleFactor(Double2 scaleFactor) {
        for (MathStyle style : this.styles.values()) {
            style.adjustSize(scaleFactor.x);
        }
    }

    public void setCssFile(String file) {
        this.cssFile = file;
    }

    public String getCssFile() {
        return cssFile;
    }

    public void setFontBaseSize(Integer baseSize) {
        this.fontBaseSize = baseSize;
    }

    public int getFontBaseSize() {
        return fontBaseSize;
    }

    

}
