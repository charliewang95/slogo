package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Difference extends Command {

	public Difference() {
		super("MathOperation", 2);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		Double diff = Math.abs( getNumCommandDouble(inputs, 0) - getNumCommandDouble(inputs, 1) );
		
		return diff.toString();
		
	}
	
}
