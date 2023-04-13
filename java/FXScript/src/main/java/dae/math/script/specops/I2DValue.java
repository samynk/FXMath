/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public interface I2DValue extends ICalc, I1DComponent {

    public double x();

    public double y();

    public double w();

    @Override
    public default Double getComponent(int index) {
        return switch (index) {
            case 0 ->
                x();
            case 1 ->
                y();
            case 2 ->
                w();
            default ->
                Double.NaN;
        };
    }

    default public void set(double x, double y, double w) {

    }

    public double norm();

    default public double dot(I2DValue v2) {
        return x() * v2.x() + y() * v2.y();
    }

    @Override
    public default int getDim1() {
        return 2;
    }
}
