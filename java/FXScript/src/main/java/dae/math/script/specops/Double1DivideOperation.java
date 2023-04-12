/*
 * Digital Arts and Entertainment 2018.
 * www.digitalartsandentertainment.be
 */
package dae.math.script.specops;

import dae.math.script.functions.Operator;


/**
 *
 * @author Koen Samyn <samyn.koen@gmail.com>
 */
public class Double1DivideOperation extends Double1Operation {

    public Double1DivideOperation(String id, IDouble1Value op1, Operator op, IDouble1Value op2) {
        super(id, op1, op, op2);
    }


    @Override
    public void update() {
        super.update();
        double value = getOp1().getValue().value() / getOp2().getValue().value();
        this.result.set(value);
    }
}
