package ecommerce.eAlgorithm10;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.sourcerow.ISubRow;

public class SourceRowA extends ISourceRow {
	
	private static Logger logger = LoggerFactory.getLogger(SourceRowA.class);

	private String source;	
	private IExpect pattern12, pattern5;
	public void setPattern12(IExpect pattern12) {
		this.pattern12 = pattern12;
	}

	public void setPattern5(IExpect pattern5) {
		this.pattern5 = pattern5;
	}

	public SourceRowA(String source){
		this.source = source;
	}
	
	private static ISubRow rowLimit;
	public static void setRowLimit(ISubRow rowLimit){
		SourceRowA.rowLimit = rowLimit;
	}
	
	@Override
	public String getSource(){
		return this.source;
	}
	
	@Override
	public IRow run() {
		
		List<ElementA> elements = new ArrayList<ElementA>();
		String row = SourceRowA.rowLimit.subRow(this);
		int length = row.length()/7;
		int i=0;
		for(; i<length; i++){
			String sub = row.substring(i*7, i*7+7);
			elements.add(new ElementA(sub.toCharArray(), this.pattern12, this.pattern5));
		}
		
		if(row.length()%7 != 0){
			String sub = row.substring(i*7, row.length());
			elements.add(new ElementA(sub.toCharArray(), this.pattern12, this.pattern5));
		}
		return new ResultRow(elements);
	}
}
