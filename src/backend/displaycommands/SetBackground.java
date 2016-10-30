package backend.displaycommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetBackground extends Command {

	private Turtle myTurtle;

	public SetBackground(Turtle t) {
		super("Display Command", 1);
		myTurtle = t;
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		
		if(! checkOneNumberInput(inputs) ) {
			return "0";
		}
		
		myTurtle.setBackground(Integer.parseInt(inputs.get(0).compute(null)));
		
		return inputs.get(0).compute(null);
		
	}

}
