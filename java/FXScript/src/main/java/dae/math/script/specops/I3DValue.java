/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.format.MathFormat;
import dae.math.script.values.Double3;

/**
 * This interface offers support for homogeneous 3D values.
 * A value where the w-component is zero is a vector, and 
 * a value where the w-component is different from zero is a point.
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public interface I3DValue extends ICalc, I1DComponent {

    public double x();

    public double y();

    public double z();
    
    public double w();

    @Override
    public default Double getComponent(int index) {
        switch (index) {
            case 0:
                return x();
            case 1:
                return y();
            case 2:
                return z();
            case 3:
                return w();
            default:
                return 0.0;
        }
    }
    
    default public void set(double x, double y, double z, double w) {

    }
    
    default public void setX(double x){
        
    }
    
    default public void setY(double y){
        
    }
    
    default public void setZ(double z){
        
    }
    
    default public void setW(double w){
        
    }
    
    public I3DValue clone();

    public Double3 getWorldPosition(Double3 w);

    default public double norm() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }
    
    @Override
    default public int getDim1(){
        return 4;
    }

    default public String toString(MathFormat df) {
        return "[" + df.format(x()) + "," + df.format(y()) + "," + df.format(z()) + "]";

    }
}
