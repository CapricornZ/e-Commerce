package ecommerce.algorithm1;

import java.util.ArrayList;
import java.util.List;

import ecommerce.algorithm1.pairs.IPair;

public class SourceRow3 {
	
	private String source;
	private IPair pairEngine;
	public SourceRow3(String source, IPair pair){
		this.source = source;
		this.pairEngine = pair;
	}
	
	public ITrueAndFalse execute(){
	
		int header = 3;
		boolean[] rtn = new boolean[source.length() - header];
		int index = 0;
		while (true) {

			int i = 0;
			boolean isPair = false;

			for (; i < 3 && !isPair && index + header + (i + 1) <= source.length(); i++) {
				String first = source.substring(index, index + (i + 1));
				String second = source.substring(index + header, index + header + (i + 1));
				isPair = this.pairEngine.pair(first, second);

				rtn[index+i] = isPair;
			}
			if (i == 0)
				break;
			index += i;
		}

		//ITrueAndFalse result = new TrueAndFalse(rtn, header);
		ITrueAndFalse result = new TrueAndFalse1(rtn, header);
		return result;
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
	
	private int findHeader(){
		
		char[] array = this.source.toCharArray();
		int rtn = 1;
		int column = 0;
		int COLUMN = 4;
		char last = array[0];
		for (int index = 1; index < array.length && column < COLUMN; index++) {

			if (array[index] != last) {
				last = array[index];
				column++;
			}

			if (column <= COLUMN - 1)
				rtn++;
		}
		return rtn;
	}
}
