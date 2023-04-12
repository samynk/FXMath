package dae.gd.fxlayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Koen.Samyn
 */
public class Size {

    private double value = 0;
    private SizeUnit unit = SizeUnit.PX;
    private SizeContext context = SizeContext.NONE;
    private SizeReferenceFrame refFram = SizeReferenceFrame.ABSOLUTE;

    private static Pattern sizeMatcher = Pattern.compile("(\\Q+\\E|-)?(\\d+)(px|pt|mm|cm|in|em|ex|%)(dw|dh|cw|ch|pw|ph)?");
    private boolean set = false;

    public Size() {

    }

    public Size(double value, SizeUnit unit) {
        this.value = value;
        this.unit = unit;
        set = true;
    }

    public Size(double value, SizeUnit unit, SizeContext context) {
        this.value = value;
        this.unit = unit;
        this.context = context;
        set = true;
    }

    private Size(double value, SizeUnit unit, SizeReferenceFrame frame, SizeContext sc) {
        this.value = value;
        this.unit = unit;
        this.refFram = frame;
        this.context = sc;
        set = true;
    }

    public boolean isSet() {
        return set;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return the unit
     */
    public SizeUnit getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(SizeUnit unit) {
        this.unit = unit;
    }

    /**
     * @return the context
     */
    public SizeContext getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(SizeContext context) {
        this.context = context;
    }

    public void set(double value, SizeUnit unit, SizeContext context) {
        this.value = value;
        this.unit = unit;
        this.context = context;
        set = true;
    }

    public void set(double value, SizeUnit unit) {
        this.value = value;
        this.unit = unit;
        set = true;
    }

    public void copy(Size s) {
        this.value = s.value;
        this.unit = s.unit;
        this.refFram = s.refFram;
        this.context = s.context;
        this.set = true;
    }

    /**
     * Parses a size in the following format: 100 -> px is the default unit 50in
     * -> 50 inches 26pt -> 26 points 60cm -> 60 cm 23mm -> 23 mm 50%doc -> 50%
     * of the document width. 25%pag -> 25% of the current page width. 10%par ->
     * 10% of the current paragraph width.
     *
     * @param size
     * @return
     */
    public static Size parseSize(String size) {
        Matcher m = sizeMatcher.matcher(size);
        if (m.find()) {
            String relative = m.group(1);
            SizeReferenceFrame frame = SizeReferenceFrame.ABSOLUTE;
            boolean negative = false;
            if (relative != null) {
                frame = SizeReferenceFrame.RELATIVE;
                if (relative.startsWith("-")) {
                    negative = true;
                }
            }

            String value = m.group(2);
            double dValue = 0;
            if (!value.isEmpty()) {
                dValue = Double.parseDouble((value));
            }

            String unit = m.group(3);

            SizeUnit sunit = SizeUnit.PX; // default
            if (unit != null) {
                if (unit.equals("%")) {
                    sunit = SizeUnit.PERCENTAGE;
                } else {
                    sunit = SizeUnit.valueOf(unit.toUpperCase());
                }
            }

            String rel = m.group(4);
            SizeContext sc = SizeContext.NONE;
            if (rel != null) {
                if (frame == SizeReferenceFrame.RELATIVE || sunit == SizeUnit.PERCENTAGE) {
                    sc = SizeContext.valueOf(rel.toUpperCase());
                }
            }
            return new Size(negative ? -dValue : dValue, sunit, frame, sc);
        } else {
            return new Size(0, SizeUnit.PX);
        }
    }

    @Override
    public String toString() {
        return this.value + " " + this.unit.toString() + " " + this.context.toString();
    }
}
