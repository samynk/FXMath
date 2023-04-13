/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dae.math.script.tests;

import dae.math.script.values.Double2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static dae.math.script.tests.TestUtil.*;

/**
 *
 * @author Koen.Samyn
 */
public class TestDouble2Math {

    public TestDouble2Math() {
    }

    @Test
    public void test2DVariables() {
        Double2 v1 = new Double2(1, 3, 0);
        Double2 v2 = new Double2(4, 1, 0);

        assertEquals(3.60555, v1.distance(v2), 0.00001);
        assertEquals(3.16228, v1.norm(), 0.00001);
        assertEquals(4.12311, v2.norm(), 0.00001);

        double angle = v1.angleBetween(v2);
        assertEquals(-1.00407, angle, 0.00001);
        
        v1.add(v2);
        testDouble2(5,4,0,v1);
        
        Double2 p1 = new Double2(4,5,1);
        Double2 p2 = new Double2(6,2,1);
        p2.subtract(p1);
        testDouble2(2,-3,0,p2);
    }
    
    
}
