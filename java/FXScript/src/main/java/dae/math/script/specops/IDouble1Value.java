/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.values.Double1;
import dae.math.script.values.MathUnit;
import dae.math.script.values.MathUtil;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public interface IDouble1Value extends ICalc {

    @Override
    public Double1 getValue();
    
    public double getX();

    @Override
    public default Double getComponent(int index) {
        if (index == 0) {
            Double1 value = getValue();
            if (value.getUnit() == MathUnit.DEGREES) {
                return MathUtil.toRadians(value.value());
            } else {
                return getValue().value();
            }
        } else {
            return 0.0;
        }
    }
}
