package ecommerce.eAlgorithm12;

import java.lang.reflect.Constructor;

import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.eAlgorithm12.element.IElementBuilder;

public class SourceRowX extends ISourceRow {
	
	static private String typeOfResultRow;
	static public void setTypeOfResultRow(String type){
		SourceRowX.typeOfResultRow = type;
	}
	
	private String source;
	private IElementBuilder[] builders;
	public SourceRowX(){}
	public SourceRowX(String source, IElementBuilder[] builders){
		this.source = source;
		this.builders = builders;
	}
	
	@Override
	public String getSource() {
		return this.source;
	}
	
	@Override
	public IRow run() {

		IRow rtn = null;
		try {
			Constructor constructor = Class.forName(SourceRowX.typeOfResultRow).getConstructor(String.class, IElementBuilder[].class);
			rtn = (IRow)constructor.newInstance(this.source, this.builders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtn;
	}
}
