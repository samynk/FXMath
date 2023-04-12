package dae.math.script.values;

/**
 *
 * @author Koen.Samyn
 */
public class MatrixSupport {
    public static int maxRows(IMatrix op1, IMatrix op2) {
        return Math.max(op1.getNrOfRows(), op2.getNrOfRows());
    }

    public static int minRows(IMatrix op1, IMatrix op2) {
        return Math.min(op1.getNrOfRows(), op2.getNrOfRows());
    }

    public static int maxColumns(IMatrix op1, IMatrix op2) {
        return Math.max(op1.getNrOfColumns(), op2.getNrOfColumns());
    }

    public static int minColumns(IMatrix op1, IMatrix op2) {
        return Math.min(op1.getNrOfColumns(), op2.getNrOfColumns());
    }

    public static IMatrix create(int rs, int cs) {
        if (cs == 1) {
            switch (rs) {
                case 1:
                    return new Double1();
                case 2:
                    return new Double2();
                case 3:
                    return new Double3();
                default:
                    return new Matrix(0,rs,cs);
            }
        } else {
            return new Matrix(0, rs, cs);
        }
    }

    public static IMatrix add(IMatrix op1, IMatrix op2) {
        int rs = maxRows(op1, op2);
        int cs = maxColumns(op1, op2);

        IMatrix result = create(rs, cs);

        int ars = minRows(op1, op2);
        int acs = minColumns(op1, op2);

        for (int r = 0; r < ars; ++r) {
            for (int c = 0; c < acs; ++c) {
                double val1 = op1.getValue(r, c);
                double val2 = op2.getValue(r, c);
                result.setValue(r, c, val1+val2);
            }
        }
        return result;
    }
    
    public static IMatrix subtract(IMatrix op1, IMatrix op2) {
        int rs = maxRows(op1, op2);
        int cs = maxColumns(op1, op2);

        IMatrix result = create(rs, cs);

        int ars = minRows(op1, op2);
        int acs = minColumns(op1, op2);

        for (int r = 0; r < ars; ++r) {
            for (int c = 0; c < acs; ++c) {
                double val1 = op1.getValue(r, c);
                double val2 = op2.getValue(r, c);
                result.setValue(r, c, val1-val2);
            }
        }
        return result;
    }

    public static IMatrix mul(IMatrix op1, IMatrix iMatrix) {
        // if op1 or op2 is a singleont
        return null;
    }
}
