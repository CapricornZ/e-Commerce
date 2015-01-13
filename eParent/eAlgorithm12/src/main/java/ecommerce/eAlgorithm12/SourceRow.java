package ecommerce.eAlgorithm12;

import java.lang.reflect.Constructor;

import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.sourcerow.ISubRow;

public class SourceRow extends ISourceRow {
	
	private static ISubRow rowLimit;
	public static void setRowLimit(ISubRow rowLimit){
		SourceRow.rowLimit = rowLimit;
	}
	
	static private String typeOfResultRow;
	static public void setTypeOfResultRow(String type){
		SourceRow.typeOfResultRow = type;
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
		IRow rtn = null;
		try {
			Constructor constructor = Class.forName(SourceRow.typeOfResultRow).getConstructor(String.class);
			rtn = (IRow)constructor.newInstance(row);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtn;
	}
}
