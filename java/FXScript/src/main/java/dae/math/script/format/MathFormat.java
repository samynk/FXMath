package dae.math.script.format;

import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.format.NumberStyleFormat;
import dae.math.script.values.Double1;
import dae.math.script.values.String1;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author Koen.Samyn
 */
public class MathFormat extends AbstractScriptVariable {

    private final DecimalFormat format;
    private final String pattern;
    private double conversionFactor = 1.0;

    private static final DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
    private NumberStyleFormat styleFormat = NumberStyleFormat.DEFAULTSTYLE;

    public static MathFormat DEFAULT = new MathFormat(new String1("#.#"));

    static {
        formatSymbols.setGroupingSeparator(',');
        formatSymbols.setDecimalSeparator('.');
    }

    public MathFormat(String1 pattern) {
        this(pattern, new Double1(1.0));
    }

    public MathFormat(String1 pattern, Double1 conversionFactor) {
        this.pattern = pattern.value();
        format = new DecimalFormat(pattern.value(), formatSymbols);

        this.conversionFactor = conversionFactor.value();
    }

    public MathFormat(String1 pattern, String1 styleFormat) {
        this(pattern.value(), styleFormat.value());
    }

    public MathFormat(String pattern, String styleFormat) {
        this.pattern = pattern;
        format = new DecimalFormat(pattern, formatSymbols);

        this.styleFormat = switch (styleFormat) {
            case "color" ->
                NumberStyleFormat.SIMPLECOLORSTYLE;
            case "default" ->
                NumberStyleFormat.DEFAULTSTYLE;
            default ->
                NumberStyleFormat.DEFAULTSTYLE;
        };
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public String format(double toFormat) {
        toFormat *= conversionFactor;
        if (toFormat < 1e16 && toFormat > -1e16) {
            return format.format(toFormat);
        } else {
            return Math.signum(toFormat) < 0 ? "-∞" : "+∞";
        }
    }

    public String format(boolean toFormat) {
        return Boolean.toString(toFormat);
    }

    public double convert(double toConvert) {
        return toConvert * conversionFactor;
    }

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    public String getPattern() {
        return pattern;
    }

    public int getNrOfDecimalPlaces() {
        return this.format.getMaximumFractionDigits();
    }

    public MathFormat deriveFormat(String color) {
        return new MathFormat(this.pattern, color);
    }
}
