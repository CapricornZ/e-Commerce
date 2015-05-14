package ecommerce.algorithm4;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.algorithm4.perterns.Pattern1;

public class SourceRow {
	
	private static Logger logger = LoggerFactory.getLogger(SourceRow.class);
	
	private String source;
	public SourceRow(String source){
		this.source = source;
	}
	
	public void print(){
		logger.info("{}\r\n", this.source);
	}
	
	public String getFormatSource() {

		StringBuilder sb = new StringBuilder();
		String[] rows = this.format();
		for(String row : rows){
			sb.append(row);
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
	protected String[] format(){
		
		char[] array=this.source.toCharArray();
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
	
	public IRow run(){
		char[] array = this.source.toCharArray();
		int length = this.source.length()/2;
		char[] arrRow0 = new char[length+this.source.length() % 2];
		char[] arrRow1 = new char[length];
		
		//一行数据，根据数组中index坐标，分成奇偶两列
		for(int i=0; i<length; i++){
			arrRow0[i] = array[i*2];
			arrRow1[i] = array[i*2 + 1];
		}
		if(this.source.length() % 2 > 0)
			arrRow0[length] = array[array.length-1];
		
		//通过Pattern1，从两行A/B数组，转换成3/2单行数组
		String row0 = String.valueOf(arrRow0);
		String row1 = String.valueOf(arrRow1);
		
		StringBuilder sbResult0 = new StringBuilder("---"), sbResult1 = new StringBuilder("---");
		List<Integer> result = new ArrayList<Integer>();
		for(int i=3; i<length; i++){
			String sub = row0.substring(i-3, i+1);
			int value = Pattern1.execute(sub);
			result.add(value); sbResult0.append(value);
			
			sub = row1.substring(i-3, i+1);
			value = Pattern1.execute(sub);
			result.add(value); sbResult1.append(value);
		}
		if(this.source.length() % 2 > 0){
			String sub = row0.substring(length-3, length+1);
			int value = Pattern1.execute(sub);
			result.add(value);
			sbResult0.append(value);
		}
		
		{// for logger.debug
			logger.debug("{}\r\n", row0);
			logger.debug("{}\r\n", row1);
			logger.debug("{}\r\n", sbResult0.toString());
			logger.debug("{}\r\n", sbResult1.toString());
		}
		
		//return new Row(result0, result1);
		return new Row0(result);
	}
}
