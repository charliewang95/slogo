package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.lang.reflect.*;

import main.Communication;
import main.Playground;
import frontend.left.ToolBox;
/**
 * 
 * @author Tripp Whaley
 *
 */
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
	private Double store = 0.0;
	String substr;

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

			}
			else if (list.get(i).equals("ListStart") || list.get(i).equals("ListEnd")){
				tempCommand = new CommandOperator(list.get(i));
			}
			else{
				try {
					Class<?> cls;
					Constructor<?> cst;
					Object instance;
					int n = 0;
					File dir = new File("src/backend./");

					File[] dirArr = dir.listFiles();
					List<File> dirList = new ArrayList<File>();
					for (int j = 0; j < dirArr.length; j ++){
						if (dirArr[j].isDirectory()){
							dirList.add(dirArr[j]);
						}
					}
					cls = null;
						for (File d : dirList){
							File[] dirdir = d.listFiles();
							for (int k = 0; k < dirdir.length; k++){
							if (dirdir[k].toString().endsWith("\\"+list.get(i)+".java")){
								substr = d.toString().substring(4, d.toString().length());
								substr = "backend."+substr.substring(9, substr.length());
								cls = Class.forName(substr + "." + list.get(i));//Class.forName("backend." + getType(list.get(i))+"." + list.get(i));
								break;
							}
							}
						}
					/*
					 * refactor this later
					 */
					if (substr.contains("turtlecommands")||substr.contains("turtlequeries")||substr.contains("booleanoperations")){//(getType(list.get(i)).equals("turtlecommands")||getType(list.get(i)).equals("turtlequeries")||getType(list.get(i)).equals("booleanoperations")){		
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
			if (!nodeList.get(i).type.equals("Constant")){
				currNode = nodeList.get(i);
			}
		}

		return commandTree;

	}

	public Double parseTree(Node n){
		//		Double store = 0.0;
		Node myNode = n;
		if (myNode.children.size()>0){
			for (int i = myNode.children.size() - 1; i>=0; i--){
				Node child = myNode.children.get(i);
				if (child.type.equals("Constant")){

				}
				else{
					if (child.returnValue == null){
						Command x = new CommandNumber(parseTree(child));
						Node child2 = new Node(x);
						child2.returnValue = (x.compute(null));
						child2.type = child2.returnValue;
						child2.value = child2.returnValue;
						myNode.children.remove(child);
						myNode.children.add(child2);
						nodeCommandMap.put(child2, x);
					}
				}
				ArrayList<Command> tempList = new ArrayList<Command>();
				for (int j = 0; j < myNode.children.size(); j++){
					tempList.add(nodeCommandMap.get(myNode.children.get(j)));
				}
				store = updateTurtle(nodeCommandMap.get(myNode), tempList);

			}
		}
		else{
			store = updateTurtle(nodeCommandMap.get(myNode), null);
		}


		try {
			Communication.class.newInstance().setOutput(store.toString());
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return store;
	}

	private Double updateTurtle(Command command, ArrayList<Command> list) {

		return Double.parseDouble(command.compute(list));

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
