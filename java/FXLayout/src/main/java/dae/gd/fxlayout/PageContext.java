package dae.gd.fxlayout;

import java.util.Stack;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * This class provides context about the current page, current paragraph and
 * current column.
 *
 * @author Koen.Samyn
 */
public class PageContext {

    private final Size currentDocumentWidth = new Size();
    private final Size currentDocumentHeight = new Size();
    private final DoubleProperty documentWidthProperty = new SimpleDoubleProperty();
    private final DoubleProperty documentHeightProperty = new SimpleDoubleProperty();

    private final Size currentColumnWidth = new Size();
    private final Size currentColumnHeight = new Size();

    private final Size currentParagraphWidth = new Size();
    private final Size currentParagraphHeight = new Size();

    private final Stack<Size> paragraphWidthStack = new Stack<>();
    private final Stack<Size> paragraphHeightStack = new Stack<>();

    private double dotsPerInch;
    private double layoutScale;
    private final double inchPerMm = 1 / 25.4;
    private final double inchPerCm = 1 / 2.54;
    private final double pxPerPt = 96.0 / 72.0;
    
    private final Size leftMargin = new Size();
    private final Size rightMargin = new Size();
    private final Size topMargin = new Size();
    private final Size bottomMargin = new Size();

    public PageContext(double dotsPerInch, double layoutScale) {
        this.dotsPerInch = dotsPerInch;
        this.layoutScale = layoutScale;
    }

    public DoubleProperty documentWidthProperty() {
        return this.documentWidthProperty;
    }

    public DoubleProperty documentHeightProperty() {
        return this.documentHeightProperty;
    }
    
    public void setLeftRightMargin(Size margin){
        leftMargin.copy(margin);
        rightMargin.copy(margin);
    }
    
    public void setLeftMargin(Size margin){
        leftMargin.copy(margin);
    }
    
    public void setRightMargin(Size margin){
        rightMargin.copy(margin);
    }
    
    public void setTopBottomMargin(Size margin){
        topMargin.copy(margin);
        bottomMargin.copy(margin);
    }

    public void setDotsPerInch(double dotsPerInch) {
        this.dotsPerInch = dotsPerInch;
    }

    public void setCurrentDocumentWidth(double value, SizeUnit unit, SizeContext context) {
        currentDocumentWidth.set(value, unit, context);
    }

    public void setCurrentDocumentHeight(double value, SizeUnit unit, SizeContext context) {
        currentDocumentHeight.set(value, unit, context);
    }

    public void setCurrentColumnWidth(double value, SizeUnit unit, SizeContext context) {
        currentColumnWidth.set(value, unit, context);
    }

    public void setCurrentColumnHeight(double value, SizeUnit unit, SizeContext context) {
        currentColumnHeight.set(value, unit, context);
    }

    public void pushParagraphWidth(double value, SizeUnit unit, SizeContext context) {
        paragraphWidthStack.push(new Size(value, unit, context));
    }

    public void popParagraphWidth() {
        paragraphWidthStack.pop();
    }

    public void setCurrentParagraphHeight(double value, SizeUnit unit, SizeContext context) {
        currentParagraphHeight.set(value, unit, context);
    }

    public double convertToPx(Size toConvert) {
        return convertToPx(toConvert, 1);
    }

    public double convertToPx(Size toConvert, double scale) {
        double px = switch (toConvert.getUnit()) {
            case PX ->
                toConvert.getValue();
            case IN ->
                toConvert.getValue() * dotsPerInch;
            case MM ->
                toConvert.getValue() * inchPerMm * dotsPerInch;
            case CM ->
                toConvert.getValue() * inchPerCm * dotsPerInch;
            case PT ->
                toConvert.getValue() * pxPerPt;
            case PERCENTAGE ->
                switch (toConvert.getContext()) {
                    case DW ->
                        toConvert.getValue() * getPageWidthPx() / 100.0;
                    case DH ->
                        toConvert.getValue() * getPageHeightPx() / 100.0;
                    case CW ->
                        toConvert.getValue() * getColumnWidthPx() / 100.0;
                    case CH ->
                        toConvert.getValue() * getColumnHeightPx() / 100.0;
                    case PW ->
                        toConvert.getValue() * getParagraphWidthPx() / 100.0;
                    case PH ->
                        toConvert.getValue() * getParagraphHeightPx() / 100.0;
                    case NONE ->
                        0;
                };
            case EM ->
                toConvert.getValue() * pxPerPt;
            case EX ->
                toConvert.getValue() * pxPerPt;
        };

        return px * scale;
    }

    public double getPageWidthPx() {
        return convertToPx(currentDocumentWidth) - getLeftMargin() - getRightMargin();
    }

    public double getPageHeightPx() {
        return convertToPx(currentDocumentHeight, 1);
    }
    
    public double getLeftMargin(){
        return convertToPx(leftMargin);
    }
    
    public double getRightMargin(){
        return convertToPx(rightMargin);
    }

    public double getParagraphWidthPx() {
        if (paragraphWidthStack.isEmpty()) {
            return convertToPx(currentDocumentWidth);
        } else {
            return convertToPx(paragraphWidthStack.peek(), 1);
        }
    }

    public double getParagraphHeightPx() {
        return convertToPx(currentParagraphHeight, 1);
    }

    public double getColumnWidthPx() {
        return convertToPx(currentColumnWidth, 1);
    }

    public double getColumnHeightPx() {
        return convertToPx(currentColumnHeight, 1);
    }

    public void updateDocumentSize() {
        this.currentDocumentWidth.set(this.documentWidthProperty().doubleValue(), SizeUnit.PX, SizeContext.NONE);
        this.currentDocumentHeight.set(this.documentHeightProperty().doubleValue(), SizeUnit.PX, SizeContext.NONE);
    }
}
