package eAlgorithmWS;

import java.util.ArrayList;
import java.util.List;

public abstract class ISourceRow {
	/***
	 * 返回Source
	 * @return
	 */
	abstract public String getRealSource();
	/***
	 * 返回Source（带预测位）
	 * @return
	 */
	abstract public String getSource();
	abstract public int getStartOff();
	
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
		
		char[] array=this.getRealSource().toCharArray();
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
}
