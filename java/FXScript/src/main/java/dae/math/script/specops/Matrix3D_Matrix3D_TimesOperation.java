
package dae.math.script.specops;


import dae.math.script.AbstractScriptVariable;
import dae.math.script.ScriptValueClass;
import dae.math.script.values.Double3;
import dae.math.script.values.Matrix4f;

/**
 *
 * @author Koen.Samyn
 */
public class Matrix3D_Matrix3D_TimesOperation extends AbstractScriptVariable implements IMatrix3DValue {
    private IMatrix3DValue result;
    
    private IMatrix3DValue op1;
    private IMatrix3DValue op2;
    private boolean visible;
    
    public Matrix3D_Matrix3D_TimesOperation(String id, IMatrix3DValue op1, IMatrix3DValue op2){
        super.setId(id);
        result = new Matrix4f();
        this.op1 = op1;
        this.op2 = op2;
    }
    
   
    @Override
    public void update() {
        Matrix4f.multiply(op1, op2, result);
    }

   

    @Override
    public ScriptValueClass getValueClass() {
        return ScriptValueClass.UNDETERMINED;
    }

    @Override
    public IMatrix3DValue getValue() {
        return result;
    }

    @Override
    public void setComponent(int column, Double3 value, double w) {
        result.setComponent(column, value, w);
    }

    @Override
    public void setComponent(int row, int column, double value) {
        result.setComponent(row,column, value);
    }

    @Override
    public double getComponent(int row, int column) {
        return result.getComponent(row,column);
    }

    @Override
    public void transformPoint(I3DValue point, Double3 t) {
        result.transformPoint(point,t);
    }

    @Override
    public void transformVector(I3DValue vector, Double3 t) {
        result.transformVector(vector,t);
    }
    
    @Override
    public void transform(I3DValue vector, Double3 t) {
        result.transform(vector,t);
    }

    @Override
    public Object getComponent(int index) {
        return result.getComponent(index);
    }

    
}
