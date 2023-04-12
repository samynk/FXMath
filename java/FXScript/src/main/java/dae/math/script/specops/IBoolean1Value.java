package dae.math.script.specops;

import dae.math.script.values.Boolean1;

/**
 *
 * @author Koen.Samyn
 */
public interface IBoolean1Value extends ICalc{
     @Override
    public Boolean1 getValue();

    @Override
    public default Double getComponent(int index) {
        if (index == 0) {
            Boolean1 value = getValue();
            return value.value() ? 1.0 : 0.0;
        } else {
            return 0.0;
        }
    }
}
