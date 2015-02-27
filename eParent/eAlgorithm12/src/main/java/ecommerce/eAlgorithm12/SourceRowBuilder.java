package ecommerce.eAlgorithm12;

import ecommerce.base.ISourceRow;
import ecommerce.eAlgorithm12.element.IElementBuilder;

public class SourceRowBuilder {
	
	public enum TypeOfPattern{
		POSITIVE4("正4"),NEGTIVE4("反4"),POSITIVE5("正5"),NEGTIVE5("反5");
		
		private String name;
		private TypeOfPattern(String name){
			this.name = name;
		}
	};
	
	// ----- STATIC -----
	static private IElementBuilder[] Positive4;
	static private IElementBuilder[] Positive5;
	static private IElementBuilder[] Negtive4;
	static private IElementBuilder[] Negtive5;
	static public void setPositive4(IElementBuilder[] positive4) {
		Positive4 = positive4;
	}
	static public void setPositive5(IElementBuilder[] positive5) {
		Positive5 = positive5;
	}
	static public void setNegtive4(IElementBuilder[] negtive4) {
		Negtive4 = negtive4;
	}
	static public void setNegtive5(IElementBuilder[] negtive5) {
		Negtive5 = negtive5;
	}
	// ----- STATIC -----
	
	static public ISourceRow create(String source, int typeOfSource, TypeOfPattern typeOfPattern){
		
		ISourceRow rtn = null;
		try {
			IElementBuilder[] builders = null;
			switch(typeOfPattern){
				case POSITIVE4:builders = Positive4;break;
				case POSITIVE5:builders = Positive5;break;
				case NEGTIVE4:builders = Negtive4;break;
				case NEGTIVE5:builders = Negtive5;break;
			}
			rtn = new SourceRowX(source, builders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtn;
	}
	
}
