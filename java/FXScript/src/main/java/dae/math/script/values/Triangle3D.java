
package dae.math.script.values;

import dae.math.script.functions.CrossProductFunction;
import dae.math.script.specops.I3DValue;
import dae.math.script.specops.I3D_I3D_MinusOperation;

/**
 *
 * @author Koen.Samyn
 */
public class Triangle3D {

    I3DValue pt1;
    I3DValue pt2;
    I3DValue pt3;
    
    private CrossProductFunction crossProduct;
    
    public Triangle3D(){
        this(Double3.ZERO, Double3.UNIT_X, Double3.UNIT_Y);
    }
    
    public Triangle3D(I3DValue pt1, I3DValue pt2, I3DValue pt3){
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.pt3 = pt3;
        
        var v1 = new I3D_I3D_MinusOperation("v_1",pt2,pt1);
        var v2 = new I3D_I3D_MinusOperation("v_2",pt3,pt1);
        crossProduct = new CrossProductFunction(v1,v2); 
    }

    public void calcNormal() {
        crossProduct.update();
    }

    public I3DValue getNormal() {
        return (I3DValue)crossProduct.getValue();
    }   
}
