package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Power extends Command {

	public Power() {
		super("MathOperation", 2);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		Double pow = Math.pow( getNumCommandDouble(inputs, 0) , getNumCommandDouble(inputs, 1) );
		
		return pow.toString();
		
	}
	
}
