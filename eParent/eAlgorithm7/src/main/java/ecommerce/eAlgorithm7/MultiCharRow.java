package ecommerce.eAlgorithm7;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.Decide;
import ecommerce.base.IRow;

/***
 * Example :
 * 		BBAAAAAAAB
 * 		BBBBBBABAA
 * 		BBABABBABA
 * 		BAABAAABBB
 * 	
 * @author martin
 *
 */
public class MultiCharRow implements IRow {
	
	private static Logger logger = LoggerFactory.getLogger(MultiCharRow.class);
	private static Pattern[] patternRows;
	static{
		patternRows = new Pattern[4];
		//参见算法七矩阵图
		patternRows[0] = new Pattern(
				new Position[]{new Position(0,0),new Position(0,1),new Position(0,2)}, 
				new Position[]{new Position(3,0),new Position(2,1),new Position(1,2)}, new Position(0,3));
		patternRows[1] = new Pattern(
				new Position[]{new Position(1,0),new Position(1,1),new Position(1,2)}, 
				new Position[]{new Position(0,2),new Position(2,2),new Position(3,1)}, new Position(1,3));
		patternRows[2] = new Pattern(
				new Position[]{new Position(2,0),new Position(2,1),new Position(2,2)}, 
				new Position[]{new Position(0,1),new Position(1,2),new Position(3,2)}, new Position(2,3));
		patternRows[3] = new Pattern(
				new Position[]{new Position(3,0),new Position(3,1),new Position(3,2)},
				new Position[]{new Position(0,0),new Position(1,1),new Position(2,2)}, new Position(3,3));
		
	}

	private String[] data;
	public MultiCharRow(String data0, String data1, String data2, String data3){
		
		this.data = new String[4];
		this.data[0] = data0;
		this.data[1] = data1;
		this.data[2] = data2;
		this.data[3] = data3;
	}
	
	@Override
	public IRow run() {

		List<Decide> result = new ArrayList<Decide>();
		int length=this.data[0].length();
		for(int column=0; column<length-3; column++){
			
			char[][] matrix = new char[4][4];
			for(int row=0; row<4; row++){
				matrix[row][0] = this.data[row].charAt(column+0);
				matrix[row][1] = this.data[row].charAt(column+1);
				matrix[row][2] = this.data[row].charAt(column+2);
				matrix[row][3] = (column==length-4&&length!=this.data[row].length())?' ':this.data[row].charAt(column+3);
			}
			
			for(Pattern rowDecide : patternRows)
				result.add(rowDecide.determine(matrix));
		}
		
		return new ResultRow(result);
	}

	@Override
	public void print() {

		logger.info("{}\r\n", this.data[0]);
		logger.info("{}\r\n", this.data[1]);
		logger.info("{}\r\n", this.data[2]);
		logger.info("{}\r\n", this.data[3]);
	}

	private static class Position{
		public int row, column;
		public Position(int row, int column){
			this.row = row;
			this.column = column;
		}
	}
	
	static private class Pattern{
		
		static private String[] modes = new String[]{"AAAA","AABB","ABAB","ABBA","BAAB","BABA","BBAA","BBBB"};
		
		private Position[] data1;
		private Position[] data2;
		private Position cross;
		
		public Pattern(Position[] line1, Position[] line2, Position cross){
			this.data1 = line1;
			this.data2 = line2;
			this.cross = cross;
		}
		
		public Decide determine(char[][] matrix){
			
			StringBuilder sb1 = new StringBuilder();
			for(Position pos:this.data1)
				sb1.append(matrix[pos.row][pos.column]);
			char expect1 = this.expect(sb1.toString());
			
			StringBuilder sb2 = new StringBuilder();
			for(Position pos:this.data2)
				sb2.append(matrix[pos.row][pos.column]);
			char expect2 = this.expect(sb2.toString());
			
			if(matrix[cross.row][cross.column] == ' ')
				return Decide.SKIP;

			if(expect1 == expect2)
				return expect1==matrix[cross.row][cross.column] ? Decide.TRUE:Decide.FALSE;
			
			return Decide.INVALID;
		}
		
		private char expect(String value){
			
			boolean bFound = false;
			int i=0;
			for(; !bFound && i<modes.length; i++){
				bFound = modes[i].startsWith(value);
			}
			return modes[i-1].charAt(3); 
		}
	}
}
