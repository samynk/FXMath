/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public enum ScriptValueClass {
    NEGATIVE, MINUSONE, ZERO, ONE, POSITIVE, UNDETERMINED, FALSE, TRUE;

    public static ScriptValueClass convert(double value) {
        if (value < 0) {
            if (value == -1) {
                return ScriptValueClass.MINUSONE;
            } else {
                return ScriptValueClass.NEGATIVE;
            }
        } else if (value > 0) {
            if (value == -1) {
                return ScriptValueClass.ONE;
            } else {
                return ScriptValueClass.POSITIVE;
            }
        } else if (value == 0) {
            return ZERO;
        } else {
            return UNDETERMINED;
        }
    }

    public static ScriptValueClass convert(int value) {
        if (value < 0) {
            if (value == -1) {
                return ScriptValueClass.MINUSONE;
            } else {
                return ScriptValueClass.NEGATIVE;
            }
        } else if (value > 0) {
            if (value == -1) {
                return ScriptValueClass.ONE;
            } else {
                return ScriptValueClass.POSITIVE;
            }
        } else if (value == 0) {
            return ZERO;
        } else {
            return UNDETERMINED;
        }
    }

    public static ScriptValueClass convert(boolean value) {
        return value ? TRUE : FALSE;
    }

}
