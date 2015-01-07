package ecommerce.eAlgorithm9;

import java.util.ArrayList;
import java.util.List;

import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.sourcerow.ISubRow;

public class SourceRow extends ISourceRow {
	
	private static ISubRow rowLimit;
	public static void setRowLimit(ISubRow rowLimit){
		SourceRow.rowLimit = rowLimit;
	}

	private String source;
	public SourceRow(String source){
		this.source = source;
	}
	
	@Override
	public String getSource(){
		return this.source;
	}

	@Override
	public IRow run() {
		
		List<Element> elements = new ArrayList<Element>();
		String row = SourceRow.rowLimit.subRow(this);
		int length = row.length()/7;
		int i=0;
		for(; i<length; i++){
			String sub = row.substring(i*7, i*7+7);
			elements.add(new Element(sub.toCharArray()));
		}
		String sub = row.substring(i*7, row.length());
		elements.add(new Element(sub.toCharArray()));
		return new ResultRow(elements);
	}	
}
