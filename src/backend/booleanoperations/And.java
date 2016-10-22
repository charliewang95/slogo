package backend.booleanoperations;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class And extends Command{

	public And(String type, int inputs) {
		super("BooleanOperation", 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		// TODO Auto-generated method stub
		if (and(inputs)){
			return "1";
		}
		return "0";
		
	}

	private boolean and(ArrayList<Command> inputs){
		return (Integer.parseInt(inputs.get(0).compute(null)) > 0 &&  Integer.parseInt(inputs.get(1).compute(null)) > 0);
	}
}
