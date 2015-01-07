package ecommerce.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SourceRowConvert {
	
	public static class Scan0{
		public String convert(char[] source, int offset, char current){
			int count = (int)source[offset] - (int)'0';
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<count; i++)
				sb.append(current);
			return sb.toString();
		}
	}
	
	public static class Scan1{
		public String convert(char[] source, int offset, char current){
			StringBuilder sb = new StringBuilder();
			int sum = 0;
			boolean bStop = false;
			for(int i=offset+1; !bStop && i<source.length; i++){
				if(source[i] == ')')
					bStop = true;
				else{
					sum = sum*10;
					sum += (int)source[i] - (int)'0';
				}
			}
			for(int i=0; i<sum; i++)
				sb.append(current);
			return sb.toString();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IRow convert(String source, Class rowType) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		
		StringBuilder sb = new StringBuilder();
		char[] arraySource = source.toCharArray();
		char currentChar = arraySource[0];
		int i=1;
		boolean bStop = false;
		while(!bStop){
			String val;
			if(arraySource[i] == '(')
				val = new Scan1().convert(arraySource, i, currentChar);
			else
				val = new Scan0().convert(arraySource, i, currentChar);

			sb.append(val);
			currentChar = currentChar=='A'?'B':'A';
			if(val.length()>9)
				i+=4;
			else
				i++;
			bStop = i>=source.length();
		}
		
		Constructor constructor = rowType.getConstructor(new Class[]{String.class});
		IRow rtn = (IRow)constructor.newInstance(new Object[]{sb.toString()});
		return rtn;
	}
}
