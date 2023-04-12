
package dae.math.script.functions;

/**
 * The supported operator types.
 * @author Koen.Samyn
 */
public enum Operator {
    PLUS(" ＋ "),MINUS(" － "),TIMES("\u00d7"),DIVIDEBY(" / "),MODULO("%"),CROSS(" \u00d7 "),NOOP("<err>"), POWER("^"),
    LT("<"),GT(">"),LTEQ("⩽"),GTEQ("⩾"),EQ("⩵"),NEQ("≠"),
    AND("∧"), OR("∨"), DOT("\u00b7");
    
    private final String symbol;
    
    Operator(String symbol){
        this.symbol = symbol;
    }
    
    public String symbol(){
        return symbol;
    }
}
