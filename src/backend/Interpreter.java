package backend;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;

import backend.turtlecommands.*;

public class Interpreter {
	
	public Interpreter(){
		
	}

	private ProgramParser parse = new ProgramParser();
	private Tree commandTree = new Tree();
	private List<Node> nodeList = new ArrayList<Node>();
	private List<Command> commandList = new ArrayList<Command>();
	private Command tempCommand;
	private List<String> stringList;
	private int output = 0;
	private String myLanguage;

	public Tree interpretString(String input){

		List<String> stringList = separateStrings(input);
		List<String> parsedList = new ArrayList<String>();
		myLanguage = "English";
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

		for (int i = 0; i < list.size(); i++){

			if (list.get(i).equals("Constant")){
				tempCommand = new CommandNumber(Integer.parseInt(stringList.get(i)));

				System.out.println(tempCommand.compute(null));
			}
			else{
				/*
				 * this is where we need reflection
				 */
				try {
					Class<?> cls = Class.forName("backend.turtlecommands." + list.get(i));
					Constructor<?> cst = cls.getConstructor(Turtle.class);
					Object instance = cst.newInstance(new Turtle(0,0));
					
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
				
//				tempCommand = new Forward(new Turtle(0,0));
			}

			commandList.add(tempCommand);
		}
		return commandList;

	}

	private Command obj(Turtle turtle) {
		// TODO Auto-generated method stub
		return null;
	}

	private Command cls(Turtle turtle) {
		// TODO Auto-generated method stub
		return null;
	}

	public Tree createCommandTree(List<Command> commandList){

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
					output+= Integer.parseInt(child.value);
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
	
	public void setLanguage(String language){
		myLanguage = language;
	}

}
