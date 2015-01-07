package ecommerce.eAlgorithm6;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		result.add(new ArrayList<Integer>());
		result.add(new ArrayList<Integer>());
		result.add(new ArrayList<Integer>());
		result.add(new ArrayList<Integer>());
		for(int iData=0; iData<4; iData++){
			int length = this.data[iData].length();
			for(int i=3; i<length; i++){
				String sub = this.data[iData].substring(i-3, i+1);
				int value = Pattern.execute(sub);
				result.get(iData).add(value);
			}
		}
		
		return new NumberRow(result);
	}

	@Override
	public void print() {

		logger.info("{}\r\n", this.data[0]);
		logger.info("{}\r\n", this.data[1]);
		logger.info("{}\r\n", this.data[2]);
		logger.info("{}\r\n", this.data[3]);
	}

	static private class Pattern {
		
		private static Pattern[] PATTERNS;
		static{
			PATTERNS = new Pattern[2];
			PATTERNS[0] = new Pattern(new String[]{"AAAA","AABB","ABAB","ABBA","BAAB","BABA","BBAA","BBBB"}, 2);
			PATTERNS[1] = new Pattern(new String[]{"AAAB","AABA","ABAA","ABBB","BAAA","BABB","BBAB","BBBA"}, 3);
		}
		
		public static int execute(String value){
			int result = PATTERNS[0].pair(value);
			return result > 0 ? result : PATTERNS[1].pair(value);
		}
		
		private String[] patterns;
		private int result;
		public Pattern(String[] patterns, int result){
			this.patterns = patterns;
			this.result = result;
		}
		
		public int pair(String value){
			
			int rtn = 0;
			boolean bFound = false;
			for(int i=0; i<this.patterns.length && !bFound; i++)
				bFound = this.patterns[i].equals(value);
			if(bFound)
				rtn = this.result;
			return rtn;
		}
	}
}
