package backend.mathoperations;

import java.util.ArrayList;
import backend.Command;

public class Product extends Command {

	public Product() {
		super("MathOperation", 2);
	}
	
	public String compute(ArrayList<Command> inputs) {
		if(! checkTwoNumberInput(inputs) ) {
			return "0";
		}
		
		Double prod = getNumCommandDouble(inputs, 0) * getNumCommandDouble(inputs, 1);
		
		return prod.toString();
		
	}
	
}
