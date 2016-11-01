package backend;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * code from http://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
 * reads input file by taking in classpath as string, outputs file contents as a string;
 * 
 */
public class FileToString {
	
	static String readFile(String path, Charset encoding) throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
}
