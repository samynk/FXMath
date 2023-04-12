
package dae.math.script.specops;

import dae.math.script.values.Double2;

/**
 *
 * @author Koen.Samyn
 */
public class MathUtilities {

    public static double angle(I2DValue p1, I2DValue center, I2DValue p2, Double2 angles, boolean shortestAngle)
    {
         // calculate angle in local space, otherwise y-scaling 
        // can introduce errors.
        double angle1 = Math.atan2(p1.y() - center.y(), p1.x() - center.x());
        angle1 = angle1 < 0 ? 2 * Math.PI + angle1 : angle1;

        double angle2 = Math.atan2(p2.y() - center.y(), p2.x() - center.x());
        angle2 = angle2 < 0 ? 2 * Math.PI + angle2 : angle2;

        //gc.lineTo(radius* Math.cos(angle1), radius*Math.sin(angle1));
        if (angle2 < angle1) {
            angle2 += (2 * Math.PI);
        }

        if (shortestAngle) {
            if (Math.abs(angle2 - angle1) > Math.PI) {
                double b = angle1;
                angle1 = angle2;
                angle2 = b;
                if (angle2 < angle1) {
                    angle2 += (2 * Math.PI);
                }
            }
        }
        angles.x = angle1;
        angles.y = angle2;
        return angle2 - angle1;
    }
}
