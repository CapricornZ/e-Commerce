package ecommerce.eAlgorithm12;

import ecommerce.base.ISourceRow;
import ecommerce.eAlgorithm12.element.IElementBuilder;

public class SourceRowBuilder {
	
	public enum TypeOfPattern{
		POSITIVE45("正45"),NEGTIVE45("反45"),POSITIVE54("正54"),NEGTIVE54("反54"),
		POSITIVE56("正56"),NEGTIVE56("反56"),POSITIVE65("正65"),NEGTIVE65("反65"),
		POSITIVE67("正67"),NEGTIVE67("反67"),POSITIVE76("正76"),NEGTIVE76("反76");
		
		private String name;
		private TypeOfPattern(String name){
			this.name = name;
		}
	};
	
	// ----- STATIC -----
	static private IElementBuilder[] Positive45;
	static private IElementBuilder[] Positive54;
	static private IElementBuilder[] Negtive45;
	static private IElementBuilder[] Negtive54;
	static public void setPositive45(IElementBuilder[] positive4) {
		Positive45 = positive4;
	}
	static public void setPositive54(IElementBuilder[] positive5) {
		Positive54 = positive5;
	}
	static public void setNegtive45(IElementBuilder[] negtive4) {
		Negtive45 = negtive4;
	}
	static public void setNegtive54(IElementBuilder[] negtive5) {
		Negtive54 = negtive5;
	}
	
	static private IElementBuilder[] Positive56;
	static private IElementBuilder[] Positive65;
	static private IElementBuilder[] Negtive56;
	static private IElementBuilder[] Negtive65;
	public static void setPositive56(IElementBuilder[] positive56) {
		Positive56 = positive56;
	}
	public static void setPositive65(IElementBuilder[] positive65) {
		Positive65 = positive65;
	}
	public static void setNegtive56(IElementBuilder[] negtive56) {
		Negtive56 = negtive56;
	}
	public static void setNegtive65(IElementBuilder[] negtive65) {
		Negtive65 = negtive65;
	}
	
	static private IElementBuilder[] Positive67;
	static private IElementBuilder[] Positive76;
	static private IElementBuilder[] Negtive67;
	static private IElementBuilder[] Negtive76;
	public static void setPositive67(IElementBuilder[] positive67) {
		Positive67 = positive67;
	}
	public static void setPositive76(IElementBuilder[] positive76) {
		Positive76 = positive76;
	}
	public static void setNegtive67(IElementBuilder[] negtive67) {
		Negtive67 = negtive67;
	}
	public static void setNegtive76(IElementBuilder[] negtive76) {
		Negtive76 = negtive76;
	}
	//----- STATIC -----
	
	static public ISourceRow create(String source, int typeOfSource, TypeOfPattern typeOfPattern){
		
		ISourceRow rtn = null;
		try {
			IElementBuilder[] builders = null;
			switch(typeOfPattern){
				case POSITIVE45:builders = Positive45;break;
				case POSITIVE54:builders = Positive54;break;
				case NEGTIVE45:builders = Negtive45;break;
				case NEGTIVE54:builders = Negtive54;break;
				case POSITIVE56:builders = Positive56;break;
				case POSITIVE65:builders = Positive65;break;
				case NEGTIVE56:builders = Negtive56;break;
				case NEGTIVE65:builders = Negtive65;break;
				case POSITIVE67:builders = Positive67;break;
				case POSITIVE76:builders = Positive76;break;
				case NEGTIVE67:builders = Negtive67;break;
				case NEGTIVE76:builders = Negtive76;break;
			}
			rtn = new SourceRowX(source, builders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtn;
	}
	
}
