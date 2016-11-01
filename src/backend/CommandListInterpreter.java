package backend;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CommandListInterpreter {
	

	private List<Command> commandList;
	private Command tempCommand;
	String substr;
	private Turtle turtle;
	
	public List<Command> createCommandList(List<String> list) {
		//  must handle all types when converting to commands
		System.out.println(list);
		commandList = new ArrayList<Command>();
		for (int i = 0; i < list.size(); i++){

			if (list.get(i).equals("Constant")){
				tempCommand = new CommandNumber(Integer.parseInt(list.get(i)));

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
					File dir = new File("src/backend/");
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
							if (dirdir[k].toString().endsWith("\\"+list.get(i)+".java")||dirdir[k].toString().endsWith("/"+list.get(i)+".java")){
								
								substr = d.toString().substring(4, d.toString().length());
								substr = "backend."+substr.substring(8, substr.length());
								System.out.println(substr + "." + list.get(i));
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
		System.out.println(commandList.size());
		return commandList;

	}

}
