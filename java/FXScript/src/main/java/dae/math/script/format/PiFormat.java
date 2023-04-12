/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.format;

import dae.math.script.format.MathFormat;
import dae.math.script.values.Double1;
import dae.math.script.values.String1;
import java.awt.Dimension;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class PiFormat extends MathFormat {

    private final int step = 15;
    private int[] angles;

    public PiFormat(String1 pattern) {
        super(pattern);
        angles = new int[360 / step];
        for (int i = 0; i < angles.length; ++i) {
            angles[i] = i * step;
        }
    }

    public PiFormat(String1 pattern, Double1 factor) {
        super(pattern, new Double1(factor.value() * 180 / Math.PI));
    }

    @Override
    public String format(double toFormat) {

        toFormat *= getConversionFactor();
        int angle = (int) Math.round(toFormat * 180 / Math.PI);
        if (angle % 15 == 0) {
            switch (angle) {
                case 0:
                    return "0";
                case 360:
                    return "2π";
                case 180:
                    return "π";
                default:
                    {
                        Dimension d = convertToSimplestRatio(angle, 180);
                        if ( d.height != 1){
                            return d.width +"/" + d.height + " π";
                        }else{
                            return d.width + " π";
                        }
                    }
            }

        } else {
            return super.format(toFormat);
        }
    }

    public static  int GCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return GCD(b, a % b);
    }

    private static Dimension convertToSimplestRatio(int n, int d) {
        int gcd = GCD(n, d);
        while (gcd != 1) {
            n = n / gcd;
            d = d / gcd;
            gcd = GCD(n,d);
        }
        
        if ( n < 0 && d < 0 ){
            n = Math.abs(n);
            d = Math.abs(d);
        }else if ( d < 0){
            n = -n;
            d = Math.abs(d);
        }
        
        Dimension r = new Dimension(n,d);
        return r;
    }
}
