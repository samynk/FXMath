/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script;

import dae.math.script.format.MathFormat;
import dae.math.script.format.CellFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class FormulaOptions {

    private final List<String> valueIDs = new ArrayList<>();
    private final List<String> formulaID = new ArrayList<>();
    private final List<String> captionID = new ArrayList<>();
    private final List<CellFormat> cellFormatters = new ArrayList<>();
    private MathFormat format;
    private boolean transpose;

    public FormulaOptions() {

    }

    public void setMathFormat(MathFormat format) {
        this.format = format;
    }

    public MathFormat getMathFormat() {
        return format;
    }

    public void addValueID(String id) {
        valueIDs.add(id);
    }

    public boolean hasValueID(String id) {
        return valueIDs.contains(id);
    }

    public void addFormulaID(String id) {
        formulaID.add(id);
    }

    public boolean hasFormulaID(String id) {
        return formulaID.contains(id);
    }

    public void addCaptionID(String id) {
        captionID.add(id);
    }

    public boolean hasCaptionID(String id) {
        return captionID.contains(id);
    }

    public void setTranspose(boolean transpose) {
        this.transpose = transpose;
    }

    public boolean isTranspose() {
        return transpose;
    }

    public void setCellFormatters(ArrayList<CellFormat> cellFormatters) {
        this.cellFormatters.addAll(cellFormatters);
    }
    
    public List<CellFormat> getCellFormatters(){
        return cellFormatters;
    }

    public void addCellFormat(CellFormat cformat) {
        this.cellFormatters.add(cformat);
    }
}
