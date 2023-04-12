package dae.gd.fxlayout;

import dae.math.values.Double2;
import java.util.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Koen.Samyn
 */
public class MathStyle {

    private final String name;
    private Font font;
    private final FontWeight fontWeight;
    private final FontPosture fontPosture;
    private final Color fontColor;
    private final Color strokeColor;
    private final int strokeWidth;
    private final int fontSize;
    private final String fontSizeKey;

    private FontMetrics metrics;
    private final Text internal = new Text();

    private String latexFontFamily;

    public MathStyle(
            String name,
            String fontName,
            FontWeight weight,
            FontPosture posture,
            int fontSize,
            String fontSizeKey,
            Color fontColor
    ) {
        this(name, fontName, weight, posture, fontSize, fontSizeKey, fontColor, 1, null);
    }

    public MathStyle(String name, String fontName,
            FontWeight weight,
            FontPosture posture,
            int fontSize,
            String fontSizeKey,
            Color fillColor,
            int strokeWidth,
            Color strokeColor
    ) {
        this.name = name;
        this.font = Font.font(fontName, weight, posture, fontSize);
        this.fontSize = fontSize;
        this.fontSizeKey = fontSizeKey;
        this.fontWeight = weight;
        this.fontPosture = posture;
        this.fontColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        internal.setFont(font);
        metrics = new FontMetrics(font);
    }

    public MathStyle(String name) {
        this.name = name;
        fontColor = Color.ANTIQUEWHITE;
        strokeColor = Color.GRAY;
        strokeWidth = 1;
        this.fontWeight = FontWeight.NORMAL;
        this.fontPosture = FontPosture.REGULAR;
        this.fontSize = 20;
        this.fontSizeKey = "large";
        this.font = Font.font("Arial", this.fontWeight, this.fontPosture, this.fontSize);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.name);
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
        final MathStyle other = (MathStyle) obj;
        return Objects.equals(this.name, other.name);
    }

    public String getStyleName() {
        return name;
    }

    public Font getFont() {
        return font;
    }

    public String getFontSizeKey() {
        return fontSizeKey;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public boolean hasStrokeColor() {
        return strokeColor != null;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public FontMetrics getFontMetrics() {
        return metrics;
    }

    public void drawText(GraphicsContext gc, String text, double x, double y) {
        double baseLine = metrics.getAscent();
        gc.setFont(getFont());
        gc.setFill(getFontColor());
        gc.fillText(text, x, y + baseLine);

        drawText(gc, text, x, y, hasStrokeColor());
    }

    public void drawText(GraphicsContext gc, String text, double x, double y, boolean stroke) {
        double baseLine = metrics.getAscent();
        gc.setFont(getFont());
        gc.setFill(getFontColor());
        gc.fillText(text, x, y + baseLine);
        if (stroke && hasStrokeColor()) {
            gc.setLineWidth(strokeWidth);
            gc.setStroke(getStrokeColor());
            gc.strokeText(text, x, y + baseLine);
        }
    }

    public void drawText(GraphicsContext gc, HorizontalAlignment align, String text, double w, double x, double y) {
        gc.setFont(getFont());
        gc.setFill(getFontColor());
        double textWidth = metrics.computeStringWidth(text);

        x += switch (align) {
            case LEFT,NONE ->
                0;
            case CENTER ->
                w / 2 - textWidth / 2;
            case RIGHT ->
                w - textWidth;
        };

        gc.fillText(text, x, y);
    }

    public void getSize(String text, Double2 dim) {
        internal.setText(text);
        dim.x = internal.getLayoutBounds().getWidth();
        dim.y = internal.getLayoutBounds().getHeight();
    }

    public void adjustSize(double sx) {
        int newSize = (int) Math.floor(this.fontSize / sx);
        if (newSize != (int) Math.floor(font.getSize())) {
            String fname = font.getFamily();
            String style = font.getStyle();
            this.font = Font.font(fname, fontWeight, fontPosture, newSize);
            this.internal.setFont(font);
            this.metrics = new FontMetrics(font);
        }
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFontFamily() {
        return font.getFamily();
    }

    public FontPosture getFontPosture() {
        return this.fontPosture;
    }

    void setLatexFontFamily(String fontFamily) {
        this.latexFontFamily = fontFamily;

    }

    public String getLatexFontFamily() {
        return latexFontFamily;
    }

    public boolean hasLatexFontFamily() {
        return latexFontFamily != null;
    }

    public boolean isSourceCode() {
        return "sourceCodeFont".equals(latexFontFamily);
    }
}
