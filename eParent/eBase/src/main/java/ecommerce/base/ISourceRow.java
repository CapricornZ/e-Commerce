package ecommerce.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class ISourceRow implements IRow {
	
	private static Logger logger = LoggerFactory.getLogger(ISourceRow.class);
	
	abstract public String getSource();
	
	public boolean acceptFilter(IVisitor visitor){
		return visitor.filter(this);
	}
	
	@Override 
	public Context getContext(){
		String[] rows = this.format();
		Context context = new Context();
		context.put("SOURCE_ROW", rows);
		return context;
	}
	
	private String[] format(){
		
		char[] array=this.getSource().toCharArray();
		char last = array[0];
		int i=1, maxColumn=0;
		List<char[]> matrix = new ArrayList<char[]>();
		StringBuilder sb = new StringBuilder();sb.append(last);
		while(i<array.length){
			if(array[i] != last){
				
				char[] column = sb.toString().toCharArray();
				matrix.add(column);
				maxColumn = maxColumn>column.length?maxColumn:column.length;
				
				last = array[i];
				sb = new StringBuilder();sb.append(last);
			} else
				sb.append(last);
			i++;
		}
		
		char[] column = sb.toString().toCharArray();
		matrix.add(column);
		maxColumn = maxColumn>column.length?maxColumn:column.length;
		
		String[] rtn = new String[maxColumn];
		for(i=0; i<maxColumn; i++){
			StringBuilder sbRow = new StringBuilder();
			for(char[] columnLine : matrix)
				sbRow.append(columnLine.length>i?columnLine[i]:' ');
			rtn[i] = sbRow.toString();
		}
		return rtn;
	}

	@Override
	public void print() {

		String[] rows = this.format();
		for(String row : rows)
			logger.info("{}\r\n", row);
		
		/*char[] array=this.getSource().toCharArray();
		char last = array[0];
		int i=1, maxColumn=0;
		List<char[]> matrix = new ArrayList<char[]>();
		StringBuilder sb = new StringBuilder();sb.append(last);
		while(i<array.length){
			if(array[i] != last){
				
				char[] column = sb.toString().toCharArray();
				matrix.add(column);
				maxColumn = maxColumn>column.length?maxColumn:column.length;
				
				last = array[i];
				sb = new StringBuilder();sb.append(last);
			} else
				sb.append(last);
			i++;
		}
		
		char[] column = sb.toString().toCharArray();
		matrix.add(column);
		maxColumn = maxColumn>column.length?maxColumn:column.length;
		
		for(i=0; i<maxColumn; i++){
			StringBuilder sbRow = new StringBuilder();
			for(char[] columnLine : matrix)
				sbRow.append(columnLine.length>i?columnLine[i]:' ');
				//logger.info("{}", columnLine.length>i?columnLine[i]:' ');
			logger.info("{}\r\n", sbRow.toString());
		}*/
	}
}
