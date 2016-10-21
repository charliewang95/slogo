package backend;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {

	private ProgramParser parse = new ProgramParser();
	private Tree commandTree = new Tree();
	private List<Node> nodeList = new ArrayList<Node>();
	private List<Command> commandList = new ArrayList<Command>();
	private Command tempCommand;
	private List<String> stringList;

	public Tree interpretString(String input){

		//parse.addPatterns(command);
		List<String> stringList = separateStrings(input);
		List<String> parsedList = new ArrayList<String>();

		parse.addPatterns("resources.languages/English");
		parse.addPatterns("resources.languages/Syntax");
		for (int i = 0; i < stringList.size(); i++){
			parsedList.add(parse.getSymbol(stringList.get(i)));
			System.out.println(parsedList);
		}
		createCommandTree(createCommandList(parsedList));

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
				tempCommand = new MathOperations(list.get(i), null);
			}
			else{
				tempCommand = new TurtleCommands(list.get(i), null);
			}

			commandList.add(tempCommand);
		}
		return commandList;

	}

	public Tree createCommandTree(List<Command> commandList){

		for (int i = 0; i < commandList.size(); i++){
			Command currCommand = commandList.get(i);
			Node tempNode = new Node(currCommand);
			tempNode.type = currCommand.getString();
			tempNode.value = stringList.get(i);
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

	public void parseTree(Tree tree){

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
	}

}
