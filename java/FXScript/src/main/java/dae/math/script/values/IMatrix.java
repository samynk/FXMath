package dae.math.script.values;

import dae.math.script.format.MathFormat;
import dae.math.script.specops.ICalc;

/**
 * Interface that all matrices should implement.
 * @author Koen.Samyn
 */
public interface IMatrix extends ICalc{
    
    public int getNrOfRows();
    public int getNrOfColumns();

    public double getValue(int row, int column);
    public void setValue(int r, int c, double value);

    public default MathUnit getUnit(){
        return MathUnit.DIMENSIONLESS;
    }
    
    public default MathFormat getMathFormat(MathFormat baseFormat, int row, int column){
        return MathFormat.DEFAULT;
    }
    
    public default boolean isRowNumberShown(){
        return false;
    }
    
    public default boolean isColumnNumberShown(){
        return false;
    }
}
