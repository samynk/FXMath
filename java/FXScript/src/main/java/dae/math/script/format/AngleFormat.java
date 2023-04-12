/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.format;

import dae.math.script.format.MathFormat;
import dae.math.script.values.Double1;
import dae.math.script.values.String1;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class AngleFormat extends MathFormat {

    public AngleFormat(String1 pattern) {
        super(pattern, new Double1(180 / Math.PI));
    }

    public AngleFormat(String1 pattern, Double1 factor) {
        super(pattern, new Double1(factor.value() * 180 / Math.PI));
    }   
}
