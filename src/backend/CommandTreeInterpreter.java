//package backend;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import backend.observables.Communication;
//
//public class CommandTreeInterpreter {
//	private Tree commandTree;
//	private List<Node> nodeList;
//	private List<Command> commandList;
//	private HashMap<Node, Command> nodeCommandMap;
//	private Double store = 0.0;
//	
//	public Tree createCommandTree(List<Command> commandList){
//		nodeCommandMap = new HashMap<Node, Command>();
//		commandTree = new Tree();
//		nodeList = new ArrayList<Node>();
//		for (int i = 0; i < commandList.size(); i++){
//			Command currCommand = commandList.get(i);
//			Node tempNode = new Node(currCommand);
//			tempNode.type = currCommand.getType();
////			tempNode.value = stringList.get(i);
//			if (tempNode.type.equals("Constant")){
//				tempNode.returnValue = currCommand.compute(null);
//			}
//			tempNode.children = new ArrayList<Node>();
//			nodeList.add(tempNode);
//			nodeCommandMap.put(tempNode, currCommand);
//		}
//		// if tempCommand fits in a certain type, assign it to that type. If it doesn't fit in a type and is a bracket
//		// start a new branch?
//
//		commandTree.root = nodeList.get(0);
//		Node currNode = commandTree.root;
//
//		for (int i = 1; i < nodeList.size(); i++){
//			nodeList.get(i).parent = currNode;
//			currNode.children.add(nodeList.get(i));
//			if (!nodeList.get(i).type.equals("Constant")){
//				currNode = nodeList.get(i);
//			}
//		}
//
//		return commandTree;
//
//	}
//	
//	public Double parseTree(Node n){
//		//		Double store = 0.0;
//		Node myNode = n;
//		if (myNode.children.size()>0){
//			for (int i = myNode.children.size() - 1; i>=0; i--){
//				Node child = myNode.children.get(i);
//				if (child.type.equals("Constant")){
//
//				}
//				else{
//					if (child.returnValue == null){
//						Command x = new CommandNumber(parseTree(child));
//						Node child2 = new Node(x);
//						child2.returnValue = (x.compute(null));
//						child2.type = child2.returnValue;
//						child2.value = child2.returnValue;
//						myNode.children.remove(child);
//						myNode.children.add(child2);
//						nodeCommandMap.put(child2, x);
//					}
//				}
//				ArrayList<Command> tempList = new ArrayList<Command>();
//				for (int j = 0; j < myNode.children.size(); j++){
//					tempList.add(nodeCommandMap.get(myNode.children.get(j)));
//				}
//				store = updateTurtle(nodeCommandMap.get(myNode), tempList);
//
//			}
//		}
//		else{
//			store = updateTurtle(nodeCommandMap.get(myNode), null);
//		}
//		// update output
//		comm.setOutput(store.toString());
//
//		
//		try {
//			Communication.class.newInstance().setOutput(store.toString());
//		} catch (InstantiationException | IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return store;
//	}
//
//	private Double updateTurtle(Command command, ArrayList<Command> list) {
//
//		return Double.parseDouble(command.compute(list));
//
//	}
//	
//	public class Tree{
//		public Node root;
//	}
//
//	public class Node{
//		public Node(Command tempCommand) {
//			// TODO Auto-generated constructor stub
//		}
//		public ArrayList<Node> children;
//		public Node parent;
//		public String value;
//		public String type;
//		public String returnValue;
//	}
//
//}
