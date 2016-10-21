package backend;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {

	private ProgramParser parse = new ProgramParser();
	private Tree commandTree = new Tree();
	private List<Node> nodeList = new ArrayList<Node>();
	private List<Command> commandList = new ArrayList<Command>();
	private Command tempCommand;

	public Tree interpretString(String input){

		//parse.addPatterns(command);
		//separateStrings(input);
		createCommandTree(createCommandList(separateStrings(input)));
		return commandTree;

	}

	private List<String> separateStrings(String input){
		List<String> stringList = new ArrayList<String>();
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
		System.out.println(stringList);
		return stringList;

	}

	public List<Command> createCommandList(List<String> list) {
		//  must handle all types when converting to commands

		for (int i = 0; i < list.size(); i++){

			if (list.get(i).matches("\\d*")){
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
			tempNode.value = currCommand;
			nodeList.add(tempNode);
		}
		// if tempCommand fits in a certain type, assign it to that type. If it doesn't fit in a type and is a bracket
		// start a new branch?

		commandTree.root = nodeList.get(0);
		Node currNode = commandTree.root;

		for (int i = 1; i < nodeList.size(); i++){
			if (nodeList.get(i).value.getType().equals("Math")){
			nodeList.get(i).parent = currNode;
			}
		}

		return commandTree;

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
		public Command value;
	}

//	public void setChild(Node parent, Node newChild){
//		newChild.parent = parent;
//		System.out.println(parent);
//		parent.children.add(newChild);
//		System.out.println(newChild.value);
//		System.out.println(parent.value);
//		System.out.println(newChild.parent.value);
//	}
	
}
