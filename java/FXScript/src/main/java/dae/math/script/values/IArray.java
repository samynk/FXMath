package dae.math.script.values;

import dae.math.script.specops.ICalc;

/**
 *
 * @author Koen.Samyn
 */
public interface IArray {
    public void set(int[] indices, ICalc value);
    public ICalc get(int[] indices);
    public int dimension();
    public int dimension(int d);
}
