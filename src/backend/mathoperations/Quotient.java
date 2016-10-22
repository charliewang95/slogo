package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Quotient extends Command {

	public Quotient() {
		super("MathOperation", 2);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		Double quo = getNumCommandDouble(inputs, 0) / getNumCommandDouble(inputs, 1);
		
		return quo.toString();
		
	}
	
}
