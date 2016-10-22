package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Cos extends Command {

	public Cos() {
		super("MathOperation", 1);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Double cos = Math.cos( Math.PI / 180 * getNumCommandDouble(inputs, 0) );
		
		return cos.toString();
		
	}
	
}
