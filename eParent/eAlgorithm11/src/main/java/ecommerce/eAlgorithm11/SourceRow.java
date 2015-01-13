package ecommerce.eAlgorithm11;

import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.sourcerow.ISubRow;
import ecommerce.eAlgorithm11.element.IElementBuilder;

public class SourceRow extends ISourceRow {
	
	static private IElementBuilder[] elementBuilder;
	static public void setElementBuilder(IElementBuilder[] builders){
		SourceRow.elementBuilder = builders;
	}
	
	static private ISubRow rowLimit;
	static public void setRowLimit(ISubRow rowLimit){
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
		
		String row = SourceRow.rowLimit.subRow(this);
		Relay r0 = new Relay(SourceRow.elementBuilder[0].createElement(row), row.length(), SourceRow.elementBuilder[0].getLength());
		Relay r1 = new Relay(SourceRow.elementBuilder[1].createElement(row), row.length(), SourceRow.elementBuilder[1].getLength());
		return new ResultRow(r0, r1, SourceRow.elementBuilder[0].getLength());
	}
}
