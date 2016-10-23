package backend;

import java.util.ArrayList;
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

	public Tree interpretString(String input){
		parse = new ProgramParser();
		List<String> stringList = separateStrings(input);
		List<String> parsedList = new ArrayList<String>();
		output = 0;
		setLanguage();
		if (myLanguage == null){
			myLanguage = "Chinese";
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
					/*
					 * 
					 * NEEDS WORK HERE
					 * 
					 */
					System.out.println(list.get(i));
					cls = Class.forName("backend." + getType(list.get(i))+"." + list.get(i));
					cst = cls.getConstructor(Turtle.class);
					instance = cst.newInstance(turtle);
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
		}
		// if tempCommand fits in a certain type, assign it to that type. If it doesn't fit in a type and is a bracket
		// start a new branch?

		commandTree.root = nodeList.get(0);
		Node currNode = commandTree.root;
		//		nodeList.remove(nodeList.get(0));

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
		Node myNode = n;
		if (myNode.children.size()>0){
			for (int i = myNode.children.size() - 1; i>=0; i--){
				Node child = myNode.children.get(i);
				if (child.type.equals("Constant")){
					
					System.out.println("Child type is a constant");
					System.out.println(child.returnValue);
					output += Integer.parseInt(child.value);
					child.parent.returnValue += output;
					System.out.println(child.parent.type);
				}
				else{
					if (child.returnValue == null){
					parseTree(child);
					}
					else{
						output += Integer.parseInt(child.returnValue);
					}
				};
				/**
				 * 
				 * WE WILL NEED AN UPDATE TURTLE METHOD HERE TO DRAW OUT EVERY STEP
				 * updateTurtle();
				 * 
				 */
				
			}
		}
		System.out.println(output);
		return output;
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
			PenDownP (Type.turtlequeries),
			ShowingP (Type.turtlequeries),
			XCor (Type.turtlequeries),
			YCor (Type.turtlequeries),
			And (Type.booleanoperations),
			Equal (Type.booleanoperations),
			GreaterThan (Type.booleanoperations),
			LessThan (Type.booleanoperations),
			Not (Type.booleanoperations),
			NotEqual (Type.booleanoperations),
			Or (Type.booleanoperations),
			ATan (Type.mathoperations),
			Cos (Type.mathoperations),
			Difference (Type.mathoperations),
			Log (Type.mathoperations),
			Minus (Type.mathoperations),
			PI (Type.mathoperations),
			Pow (Type.mathoperations),
			Product (Type.mathoperations),
			Quotient (Type.mathoperations),
			Random (Type.mathoperations),
			Remainder (Type.mathoperations),
			Sin (Type.mathoperations),
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

	
	
	public void setLanguage(){
		myLanguage = ToolBox.myLanguage;
	}

}
