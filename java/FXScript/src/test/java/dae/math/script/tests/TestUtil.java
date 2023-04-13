
package dae.math.script.tests;

import dae.math.script.values.Double2;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Koen.Samyn
 */
public class TestUtil {

    public static void testDouble2(double eX, double eY, double eW, Double2 toCheck)
    {
        assertEquals(eX,toCheck.x,0.00001);
        assertEquals(eY,toCheck.y,0.00001);
        assertEquals(eW,toCheck.w,0.00001);
    }
}
