package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Random extends Command {

	public Random() {
		super("MathOperation", 1);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		Integer ran = (int) ( Math.random() * getNumCommandDouble(inputs, 0) );
		
		return ran.toString();
		
	}
	
}
