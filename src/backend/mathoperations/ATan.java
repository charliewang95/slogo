package backend.mathoperations;

import java.util.ArrayList;

import backend.Command;

public class ATan extends Command {

	public ATan() {
		super("MathOperation", 1);
	}

	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Double atan = Math.atan( Math.PI / 180 * getNumCommandDouble(inputs, 0) );
		
		return atan.toString();
		
	}
	
}
