package elements.controller;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.IOException;

public class FileOutput {
	
	static public OutputStream out;
	
	static public void init(OutputStream out) throws FileNotFoundException{
		FileOutput.out = out;
	}
	
	static public void writeline(String content){
		
		try {
			out.write(content.getBytes());
			out.write("\r\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static public void write(String content){
		try {
			out.write(content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public void close() throws IOException{
		out.close();
	}
}
