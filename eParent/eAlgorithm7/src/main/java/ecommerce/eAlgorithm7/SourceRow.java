package ecommerce.eAlgorithm7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.sourcerow.ISubRow;

/***
 * Example : AAABBBABAABABA
 * @author martin
 *
 */
public class SourceRow extends ISourceRow {
	
	private static Logger logger = LoggerFactory.getLogger(SourceRow.class);
	private static ISubRow rowLimit;
	public static void setRowLimit(ISubRow rowLimit){
		SourceRow.rowLimit = rowLimit;
	}
	
	private String source;
	public SourceRow(String source){
		this.source = source;
	}

	@Override
	public String getSource() {
		return this.source;
	}
	
	@Override
	public IRow run() {
		
		char[] arrSource = SourceRow.rowLimit.subRow(this).toCharArray();
		int length = arrSource.length/4;
		StringBuilder[] sbs = new StringBuilder[]{new StringBuilder(), new StringBuilder(), new StringBuilder(), new StringBuilder()};
		for(int i=0; i<length; i++){
			sbs[0].append(arrSource[i*4]);
			sbs[1].append(arrSource[i*4+1]);
			sbs[2].append(arrSource[i*4+2]);
			sbs[3].append(arrSource[i*4+3]);
		}
		int x = arrSource.length % 4;
		for(int i=0; i<x; i++)
			sbs[i].append(arrSource[length*4 + i]);
		
		return new MultiCharRow(sbs[0].toString(), sbs[1].toString(), sbs[2].toString(), sbs[3].toString());
	}
}
