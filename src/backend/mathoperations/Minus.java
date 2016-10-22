package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Minus extends Command {

	public Minus() {
		super("MathOperation", 1);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Double minus = getNumCommandDouble(inputs, 0) * -1;
		
		return minus.toString();
		
	}
	
}
