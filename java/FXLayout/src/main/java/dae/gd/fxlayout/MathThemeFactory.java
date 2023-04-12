package dae.gd.fxlayout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import dae.fxmath2.MathEventBus;
import dae.math.values.Double2;
import fxmath.ui.event.DpiScaleEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Koen Samyn
 */
public class MathThemeFactory {

    private static MathThemeFactory instance;

    private final MathStyleTheme defaultWhiteTheme;
    private final MathStyleTheme defaultDarkTheme;
    private final HashMap<String, MathStyleTheme> themes = new HashMap<>();

    private MathStyleTheme currentTheme;

    private Double2 scaleFactor = new Double2(1, 1);

    private MathThemeFactory() {
        MathEventBus.register(this);
        //defaultWhiteTheme.createDefaults();
        // read defaults from /resources/fxmath/defaults/DefaultWhiteTheme.json
        ObjectMapper mapper = new ObjectMapper();

        defaultWhiteTheme = new MathStyleTheme();
        readTheme("fxmath/themes/DefaultWhiteTheme.json", defaultWhiteTheme, mapper);
        themes.put("default", defaultWhiteTheme);

        defaultDarkTheme = new MathStyleTheme("midnight");
        readTheme("fxmath/themes/DefaultDarkTheme.json", defaultDarkTheme, mapper);
        themes.put("midnight", defaultDarkTheme);
        defaultDarkTheme.setDefaultMathStyle("paragraph");
        currentTheme = defaultWhiteTheme;

        //defaultDarkTheme.exportAsJson();
    }

    public Double2 getScaleFactor() {
        return scaleFactor;
    }

    private void readTheme(String loc, MathStyleTheme theme, ObjectMapper mapper) {
        try {
            // convert JSON file to map
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(loc);
            if (is != null) {
                Map<?, ?> map = mapper.readValue(is, Map.class);
                readParentThemeBaseSettings(map, loc, theme, mapper);

                String cssFile = map.get("cssFile").toString();
                theme.setCssFile(cssFile);
                parseBaseSettings(theme, map);
                parseColors(theme, map);
                parseFontSizes(theme, map);
                parseFontFamilies(theme, map);
                List<Map> icons = (List<Map>) map.get("icons");
                if (icons != null) {
                    parseIcons(theme, icons);
                }
                readParentThemeBaseStyles(map, loc, theme, mapper);

                List<Map> fonts = (List<Map>) map.get("fontStyles");
                if (fonts != null) {
                    parseFontStyles(theme, fonts);
                }
                List<Map> renderStyles = (List<Map>) map.get("renderStyles");
                if (renderStyles != null) {
                    parseRenderStyles(theme, null, renderStyles);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(MathThemeFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readParentThemeBaseSettings(Map<?, ?> baseMap, String loc, MathStyleTheme theme, ObjectMapper mapper) throws IOException {
        if (baseMap.containsKey("parentTheme")) {
            String baseTheme = baseMap.get("parentTheme").toString();
            Path parentLoc = Paths.get(loc);
            Path baseThemeLoc = parentLoc.resolveSibling(baseTheme);
            System.out.println("Trying to read base settings : " + baseTheme + "," + baseThemeLoc);
            readThemeBaseSettings(baseThemeLoc.toString(), theme, mapper);
        } else {
            System.out.println("no parent theme found");
        }
    }

    private void readParentThemeBaseStyles(Map<?, ?> baseMap, String loc, MathStyleTheme theme, ObjectMapper mapper) throws IOException {
        if (baseMap.containsKey("parentTheme")) {
            String baseTheme = baseMap.get("parentTheme").toString();
            Path parentLoc = Paths.get(loc);
            Path baseThemeLoc = parentLoc.resolveSibling(baseTheme);
            System.out.println("Trying to read base styles  : " + baseTheme + "," + baseThemeLoc);
            readThemeBaseStyles(baseThemeLoc.toString(), theme, mapper);
        } else {
            System.out.println("no parent theme found");
        }
    }

    private void parseFontStyles(MathStyleTheme theme, List<Map> fonts) {
        MathStyle nullStyle = new MathStyle("null");
        for (Map font : fonts) {
            MathStyle style = createMathStyle(theme, nullStyle, font);
            theme.addMathStyle(style);
        }
        theme.setDefaultMathStyle("paragraph");
    }

    private MathStyle createMathStyle(MathStyleTheme theme, MathStyle base, Map font) {
        String name = font.get("name").toString();
        MathStyle baseThemeStyle = theme.getMathStyle(name);
        if (baseThemeStyle != null) {
            base = baseThemeStyle;
        }

        String family = base.getFontFamily();
        String fontFamily = base.getLatexFontFamily();
        if (font.containsKey("fontFamily")) {
            fontFamily = font.get("fontFamily").toString();
            family = theme.getFontFamily(fontFamily);
        }

        int size = base.getFontSize();
        String fontSizeKey = "normalsize";
        if (font.containsKey("fontSize")) {
            Object fsize = font.get("fontSize");
            if (fsize instanceof String sfsize) {
                fontSizeKey = sfsize;
                size = theme.getFontSize(sfsize);
            } else if (fsize instanceof Integer ifsize) {
                size = ifsize;
            }
        }

        FontWeight fw = base.getFontWeight();
        if (font.containsKey("fontWeight")) {
            String fontWeight = font.get("fontWeight").toString();
            fw = FontWeight.findByName(fontWeight);
        }

        FontPosture posture = base.getFontPosture();
        if (font.containsKey("fontPosture")) {
            String fontPosture = font.get("fontPosture").toString();
            posture = FontPosture.findByName(fontPosture);
        }

        Color fontColor = base.getFontColor();
        if (font.containsKey("fillColor")) {
            String sFillColor = font.get("fillColor").toString();
            fontColor = theme.getColor(sFillColor);
        }

        Color strokeColor = base.getStrokeColor();
        if (font.containsKey("strokeColor")) {
            String sStrokeColor = font.get("strokeColor").toString();
            strokeColor = theme.getColor(sStrokeColor);
        }

        int strokeWidth = 1;
        if (font.containsKey("strokeWidth")) {
            strokeWidth = (Integer) font.get("strokeWidth");
        }

        MathStyle style = new MathStyle(name, family, fw, posture, size, fontSizeKey, fontColor, strokeWidth, strokeColor);
        style.setLatexFontFamily(fontFamily);

        if (font.containsKey("derived")) {
            List<Map> derived = (List<Map>) font.get("derived");
            for (Map derivedFont : derived) {
                MathStyle derivedStyle = createMathStyle(theme, style, derivedFont);
                theme.addMathStyle(derivedStyle);
            }
        }
        return style;
    }

    public static MathThemeFactory getInstance() {
        if (instance == null) {
            instance = new MathThemeFactory();
        }
        return instance;
    }

    public static MathStyleTheme getCurrentTheme() {
        return getInstance().currentTheme;
    }

    public void activateTheme(String theme) {
        MathStyleTheme t = themes.get(theme);
        if (t != null) {
            currentTheme = t;
            currentTheme.adjustScaleFactor(this.scaleFactor);
            ThemeChangeEvent tce = new ThemeChangeEvent();
            MathEventBus.postEvent(tce);
        }
    }

    private void parseRenderStyles(MathStyleTheme theme, RenderMathStyle base, List<Map> renderStyles) {
        for (Map renderStyle : renderStyles) {
            if (renderStyle.containsKey("name")) {
                RenderMathStyle style = null;
                String name = renderStyle.get("name").toString();
                if (base != null) {
                    style = new RenderMathStyle(base, name);
                } else {
                    style = theme.getRenderMathStyleRaw(name);
                    if (style == null) {
                        style = new RenderMathStyle(name);
                    }
                }
                if (renderStyle.containsKey("color")) {
                    String colorKey = renderStyle.get("color").toString();
                    Color color = theme.getColor(colorKey);
                    style.setStrokeColor(color);
                    style.setFontColor(color);
                    style.setSimpleFill(color);
                }

                if (renderStyle.containsKey("strokeColor")) {
                    String strokeColorKey = renderStyle.get("strokeColor").toString();

                    Color stroke = theme.getColor(strokeColorKey);
                    // to do : fix problem with base theme
                    Double lineWidth = base != null ? base.getLineWidth() : 1.0;
                    if (renderStyle.containsKey("lineWidth")) {
                        lineWidth = readDouble(renderStyle, "lineWidth", 1.0);
                    }

                    style.setSimpleStroke(stroke, lineWidth);
                    if (renderStyle.containsKey("lineCap"))
                    {
                        String lineCap = renderStyle.getOrDefault("lineCap", "BUTT").toString();
                        style.setLineCap(StrokeLineCap.valueOf(lineCap));
                    }

                    if (renderStyle.containsKey("lineDashes")) {
                        ArrayList<Double> dashes = (ArrayList<Double>) renderStyle.get("lineDashes");
                        style.setLineDashes(dashes);
                    }
                }
                if (renderStyle.containsKey("fillColor")) {
                    String fillColorKey = renderStyle.get("fillColor").toString();
                    if (!fillColorKey.isEmpty()) {
                        Color fill = theme.getColor(fillColorKey);
                        style.setSimpleFill(fill);
                    } else {
                        style.resetFill();
                    }
                }
                if (renderStyle.containsKey("fontColor")) {
                    String fontColorKey = renderStyle.get("fontColor").toString();
                    Color font = theme.getColor(fontColorKey);
                    style.setFontColor(font);
                }
                if (renderStyle.containsKey("brushCursorImagePath")) {
                    String iconKey = renderStyle.get("brushCursorImagePath").toString();
                    Image iconImage = theme.getIcon(iconKey);
                    style.setBrushCursorImage(new ImageCursor(iconImage));
                }

                if (renderStyle.containsKey("properties")) {
                    Map<String, Object> propertyMap = (Map) renderStyle.get("properties");
                    for (String key : propertyMap.keySet()) {
                        style.setProperty(key, propertyMap.get(key));
                    }
                }
                if (renderStyle.containsKey("category")) {
                    String category = renderStyle.get("category").toString();
                    style.setCategory(category);
                }
                theme.addRenderMathStyle(style);

                if (renderStyle.containsKey("derived")) {
                    List<Map> derived = (List<Map>) renderStyle.get("derived");
                    parseRenderStyles(theme, style, derived);
                }
            }
        }
        theme.setDefaultRenderMathStyle("default");
    }

    private void parseIcons(MathStyleTheme theme, List<Map> icons) {
        for (Map iconDef : icons) {
            String name = iconDef.get("name").toString();
            String iconPath = iconDef.get("icon").toString();
            theme.addIcon(name, iconPath);
        }
    }

    private Double readDouble(Map m, String key, Double defaultValue) {
        return (Double) m.getOrDefault(key, defaultValue);
    }

    private Integer readInt(Map object, String key) {
        if (object.containsKey(key)) {
            return (Integer) object.get(key);
        } else {
            return null;
        }
    }

    @Subscribe
    public void dpiScaleChanged(DpiScaleEvent event) {
        this.scaleFactor.set(event.getSx(), event.getSy());
        currentTheme.adjustScaleFactor(scaleFactor);
    }

    public void setScaleFactor(double outputScaleX) {
        this.scaleFactor.set(outputScaleX, outputScaleX);
    }

    private void parseColor(MathStyleTheme theme, Map<String, Object> colors) {
        for (Map.Entry<String, Object> entry : colors.entrySet()) {

            theme.addColor(entry.getKey(), Color.web(entry.getValue().toString()));
        }
    }

    private void readThemeBaseSettings(String loc, MathStyleTheme theme, ObjectMapper mapper) throws IOException {
        loc = loc.replace('\\', '/');
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(loc);

        if (is != null) {
            Map<?, ?> map = mapper.readValue(is, Map.class);
            this.readParentThemeBaseSettings(map, loc, theme, mapper);
            Object cssFile = map.get("cssFile");
            if (cssFile != null) {
                theme.setCssFile(cssFile.toString());
            }
            parseColors(theme, map);
            parseBaseSettings(theme, map);
            parseFontFamilies(theme, map);
            parseFontSizes(theme, map);
            System.out.println("base settings read");
        }
    }

    private void parseColors(MathStyleTheme theme, Map themeMap) {
        var colors = (Map<String, Object>) themeMap.get("colors");
        if (colors != null) {
            for (Map.Entry<String, Object> entry : colors.entrySet()) {
                theme.addColor(entry.getKey(), Color.web(entry.getValue().toString()));
            }
        }
    }

    private void parseFontFamilies(MathStyleTheme theme, Map themeMap) {
        var fontFamilies = (Map<String, String>) themeMap.get("fontFamilies");
        if (fontFamilies != null) {
            for (Map.Entry<String, String> entry : fontFamilies.entrySet()) {
                theme.addFontFamily(entry.getKey(), entry.getValue());
            }
        }
    }

    private void parseFontSizes(MathStyleTheme theme, Map themeMap) {
        var fontSizes = (Map<String, Object>) themeMap.get("fontSizes");
        if (fontSizes != null) {
            var baseFontSize = (List<Integer>) fontSizes.get("normalsize");
            if (baseFontSize != null) {
                for (Map.Entry<String, Object> entry : fontSizes.entrySet()) {
                    List<Integer> sizeList = (List<Integer>) entry.getValue();
                    System.out.println(entry.getKey());
                    for (int i = 0; i < sizeList.size(); ++i) {
                        if (i < baseFontSize.size()) {
                            int baseSize = baseFontSize.get(i);
                            int fontSize = sizeList.get(i);
                            theme.addFontSize(baseSize, entry.getKey(), fontSize);
                        }
                    }
                }
            }
        }
    }

    private void parseBaseSettings(MathStyleTheme theme, Map themeMap) {
        var settings = (Map<String, Object>) themeMap.get("settings");
        if (settings != null) {
            var fbs = this.readInt(settings, "fontBaseSize");
            if (fbs != null) {
                theme.setFontBaseSize(fbs);
            }
        }
    }

    private void readThemeBaseStyles(String loc, MathStyleTheme theme, ObjectMapper mapper) throws IOException {
        loc = loc.replace('\\', '/');
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(loc);
        if (is != null) {
            Map<?, ?> map = mapper.readValue(is, Map.class);
            this.readParentThemeBaseStyles(map, loc, theme, mapper);

            var fonts = (List<Map>) map.get("fontStyles");
            if (fonts != null) {
                parseFontStyles(theme, fonts);
            }

            List<Map> renderStyles = (List<Map>) map.get("renderStyles");
            if (renderStyles != null) {
                parseRenderStyles(theme, null, renderStyles);
            }

            List<Map> icons = (List<Map>) map.get("icons");
            if (icons != null) {
                parseIcons(theme, icons);
            }
        }
    }
}
