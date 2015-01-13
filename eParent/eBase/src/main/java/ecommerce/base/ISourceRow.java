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
	public void print() {

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
		
		//logger.info("<Source>\r\n");
		for(i=0; i<maxColumn; i++){
			for(char[] columnLine : matrix)
				logger.info("{}", columnLine.length>i?columnLine[i]:' ');
			logger.info("\r\n");
		}
		//logger.info("</Source>\r\n");
	}
}
