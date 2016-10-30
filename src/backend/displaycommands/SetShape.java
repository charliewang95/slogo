package backend.displaycommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetShape extends Command {

	private Turtle myTurtle;

	public SetShape(Turtle t) {
		super("Display Command", 1);
		myTurtle = t;
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		int index = Integer.parseInt(inputs.get(0).compute(null));
		
		myTurtle.setShape(index);
		
		return inputs.get(0).compute(null);
		
	}

}
