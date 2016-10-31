package backend;

import java.util.ArrayList;

public class CommandOperator extends Command {
	
	public CommandOperator(String operator) {
		super("Operator", 0);
	}
	
	public String compute(ArrayList<Command> inputs) {
//		return commandNum.toString();
		return inputs.get(0).toString();
	}

}
