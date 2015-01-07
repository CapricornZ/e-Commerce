package ecommerce.eAlgorithm8;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.Decide;
import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.sourcerow.ISubRow;

public class SourceRow extends ISourceRow {
	
	private static Logger logger = LoggerFactory.getLogger(SourceRow.class);
	private static ISubRow rowLimit;
	public static void setRowLimit(ISubRow rowLimit){
		SourceRow.rowLimit = rowLimit;
	}
	
	private String source;
	public SourceRow(){}
	public SourceRow(String source){
		this.source = source;
	}
	
	@Override
	public String getSource() {
		return this.source;
	}
	
	@Override
	public IRow run() {
		
		List<Decide> result = new ArrayList<Decide>();
		String row = SourceRow.rowLimit.subRow(this);
		for(int i=11; i<row.length(); i++){
			String val = source.substring(i-11, i+1);
			logger.debug(val);
			Decide rtn = new Element(val).execute();
			logger.debug("{}\r\n", rtn.getVal());
			result.add(rtn);
		}
		
		return new ResultRow(result);
	}
}
