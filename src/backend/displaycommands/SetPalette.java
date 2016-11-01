package backend.displaycommands;

import java.util.ArrayList;

import backend.Command;
import backend.Turtle;

public class SetPalette extends Command{

	private Turtle myTurtle;

	public SetPalette(Turtle t) {
		super("Display Command", 4);
		myTurtle = t;
	}

	@Override
	public String compute(ArrayList<Command> inputs) {
		
		if(! checkFourNumberInput(inputs) ) {
			return "0";
		}
		
		int index = Integer.parseInt(inputs.get(0).compute(null));
		int red = Integer.parseInt(inputs.get(1).compute(null));
		int green = Integer.parseInt(inputs.get(2).compute(null));
		int blue = Integer.parseInt(inputs.get(3).compute(null));
		
		myTurtle.setPalette(index, red, blue, green);
		
		return inputs.get(0).compute(null);
		
	}

}
