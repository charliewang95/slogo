package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Sin extends Command {

	public Sin() {
		super("MathOperation", 1);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Double sin = Math.sin( Math.PI / 180 * getNumCommandDouble(inputs, 0) );
		
		return sin.toString();
		
	}
	
}
