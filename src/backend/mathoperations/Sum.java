package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Sum extends Command {

	public Sum() {
		super("MathOperation", 2);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		Double sum = getNumCommandDouble(inputs, 0) + getNumCommandDouble(inputs, 1);
		
		return sum.toString();
		
	}
	
}
