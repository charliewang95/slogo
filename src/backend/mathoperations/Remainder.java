package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Remainder extends Command {

	public Remainder() {
		super("MathOperation", 2);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		Double rem = getNumCommandDouble(inputs, 0) % getNumCommandDouble(inputs, 1);
		
		return rem.toString();
		
	}
	
}
