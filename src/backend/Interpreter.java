package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.*;

import main.Playground;
import frontend.left.ToolBox;

public class Interpreter {

	public Interpreter(Playground myPlay, Turtle turtle){
		this.myPlayground = myPlay;
		this.turtle = turtle;
	}

	private Playground myPlayground;
	private ProgramParser parse;
	private Tree commandTree;
	private List<Node> nodeList;
	private List<Command> commandList;
	private Command tempCommand;
	private List<String> stringList;
	private int output = 0;
	private String myLanguage;
	private Turtle turtle;
	private HashMap<Node, Command> nodeCommandMap;

	public Tree interpretString(String input){
		parse = new ProgramParser();
		List<String> stringList = separateStrings(input);
		List<String> parsedList = new ArrayList<String>();
		output = 0;
		if (myLanguage == null){
			myLanguage = "English";
		}
		parse.addPatterns("resources.languages/" + myLanguage);
		parse.addPatterns("resources.languages/Syntax");
		for (int i = 0; i < stringList.size(); i++){
			parsedList.add(parse.getSymbol(stringList.get(i)));
			System.out.println(parsedList);
		}
		createCommandTree(createCommandList(parsedList));

		parseTree(commandTree.root);

		return commandTree;

	}

	private List<String> separateStrings(String input){
		stringList = new ArrayList<String>();
		StringBuilder currentString = new StringBuilder();
		input = input + " ";
		for (int i = 0; i < input.length(); i++){
			char currentChar = input.charAt(i);

			//refactor this later
			if (currentChar != ' '){
				currentString.append(currentChar);
			}
			else{
				if (!currentString.toString().isEmpty()){
					stringList.add(currentString.toString());
				}
				currentString = new StringBuilder();
			}

		}
		return stringList;

	}

	public List<Command> createCommandList(List<String> list) {
		//  must handle all types when converting to commands
		commandList = new ArrayList<Command>();
		for (int i = 0; i < list.size(); i++){

			if (list.get(i).equals("Constant")){
				tempCommand = new CommandNumber(Integer.parseInt(stringList.get(i)));
				//System.out.println(tempCommand.compute(null));

			}
			else if (list.get(i).equals("ListStart") || list.get(i).equals("ListEnd")){
				tempCommand = new CommandOperator(list.get(i));
			}
			else{
				try {
					Class<?> cls;
					Constructor<?> cst;
					Object instance;
					System.out.println(list.get(i));
					cls = Class.forName("backend." + getType(list.get(i))+"." + list.get(i));
					/*
					 * refactor this later
					 */
					if (getType(list.get(i)).equals("turtlecommands")||getType(list.get(i)).equals("turtlequeries")){		
						cst = cls.getConstructor(Turtle.class);
						instance = cst.newInstance(turtle);
					}
					else{
						instance = cls.newInstance();
					}
					tempCommand = (Command) instance;

					// reflection issues?```
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (tempCommand != null){
				commandList.add(tempCommand);
			}
		}
		return commandList;

	}

	public Tree createCommandTree(List<Command> commandList){
		nodeCommandMap = new HashMap<Node, Command>();
		commandTree = new Tree();
		nodeList = new ArrayList<Node>();
		for (int i = 0; i < commandList.size(); i++){
			Command currCommand = commandList.get(i);
			Node tempNode = new Node(currCommand);
			tempNode.type = currCommand.getType();
			tempNode.value = stringList.get(i);
			if (tempNode.type.equals("Constant")){
				tempNode.returnValue = currCommand.compute(null);
			}
			tempNode.children = new ArrayList<Node>();
			nodeList.add(tempNode);
			nodeCommandMap.put(tempNode, currCommand);
		}
		// if tempCommand fits in a certain type, assign it to that type. If it doesn't fit in a type and is a bracket
		// start a new branch?

		commandTree.root = nodeList.get(0);
		Node currNode = commandTree.root;

		for (int i = 1; i < nodeList.size(); i++){
			nodeList.get(i).parent = currNode;
			currNode.children.add(nodeList.get(i));

			if (nodeList.get(i).type.equals("Forward")){
				currNode = nodeList.get(i);
			}
		}

		return commandTree;

	}

	public int parseTree(Node n){

		System.out.println(nodeCommandMap.keySet());
		System.out.println(nodeCommandMap.values());
		Node myNode = n;
		if (myNode.children.size()>0){
			for (int i = myNode.children.size() - 1; i>=0; i--){
				Node child = myNode.children.get(i);
				if (child.type.equals("Constant")){

					output += Integer.parseInt(child.value);
					child.parent.returnValue += output;

				}
				else{
					if (child.returnValue == null){
						parseTree(child);
					}
					else{
						output += Integer.parseInt(child.returnValue);
					}
				}
				ArrayList<Command> tempList = new ArrayList<Command>();
				for (int j = 0; j < myNode.children.size(); j++){
					/*
					 * check if node child has children, if no then continue, if yes then make a new instance of this?
					 */
					tempList.add(nodeCommandMap.get(myNode.children.get(j)));
				}
				updateTurtle(nodeCommandMap.get(myNode), tempList);

			}
		}
		else{
			System.out.println("else");
			updateTurtle(nodeCommandMap.get(myNode), null);
		}
		System.out.println(output);
		return output;
	}

	private void updateTurtle(Command command, ArrayList<Command> list) {

		System.out.println(command.compute(list));

	}

	public class Tree{
		public Node root;
	}

	public class Node{
		public Node(Command tempCommand) {
			// TODO Auto-generated constructor stub
		}
		public ArrayList<Node> children;
		public Node parent;
		public String value;
		public String type;
		public String returnValue;
	}


	public enum Commands{
		Forward (Type.turtlecommands), 
		Backward (Type.turtlecommands), 
		ClearScreen (Type.turtlecommands), 
		HideTurtle (Type.turtlecommands), 
		ShowTurtle (Type.turtlecommands), 
		Left (Type.turtlecommands), 
		Right (Type.turtlecommands), 
		Home (Type.turtlecommands), 
		PenUp (Type.turtlecommands), 
		PenDown (Type.turtlecommands), 
		PositionMove (Type.turtlecommands),
		SetHeading (Type.turtlecommands), 
		SetXY (Type.turtlecommands), 
		Towards (Type.turtlecommands),
		Heading (Type.turtlequeries),
		IsPenDown (Type.turtlequeries),
		IsShowing (Type.turtlequeries),
		XCoordinate (Type.turtlequeries),
		YCoordinate (Type.turtlequeries),
		And (Type.booleanoperations),
		Equal (Type.booleanoperations),
		GreaterThan (Type.booleanoperations),
		LessThan (Type.booleanoperations),
		Not (Type.booleanoperations),
		NotEqual (Type.booleanoperations),
		Or (Type.booleanoperations),
		ATan (Type.mathoperations),
		Cosine (Type.mathoperations),
		Difference (Type.mathoperations),
		Log (Type.mathoperations),
		Minus (Type.mathoperations),
		PI (Type.mathoperations),
		Pow (Type.mathoperations),
		Product (Type.mathoperations),
		Quotient (Type.mathoperations),
		Random (Type.mathoperations),
		Remainder (Type.mathoperations),
		Sine (Type.mathoperations),
		Sum (Type.mathoperations),
		Tan (Type.mathoperations),
		MakeVariable(Type.variables);

		private Type type;

		Commands(Type type){
			this.type = type;
		}

		public boolean isInGroup(Type type){
			return (this.type == type);
		}



		public enum Type {
			turtlecommands,
			turtlequeries,
			booleanoperations,
			mathoperations,
			othercommands,
			variables;

		}
	}
	public String getType(String input){
		for (Commands comm : Commands.values()){
			if (comm.toString().equals(input)){
				return comm.type.toString();
			}
		}
		return null;
	}



	public void setLanguage(String language){
		myLanguage = language;
	}

}
