package ecommerce.eAlgorithm12;

import ecommerce.eAlgorithm12.element.Element4;
import ecommerce.eAlgorithm12.element.Element4.PatternPositive;
import ecommerce.eAlgorithm12.element.Element5;
import ecommerce.eAlgorithm12.element.IElementBuilder;

public class AppContext {
	
	//##### SWAP TYPE #####
	private String swapType;
	public void setSwapType(String val){this.swapType = val;}
	public Swap getSwap(){
		Swap rtn = new Swap();
		rtn.setType(this.swapType);
		return rtn;
	}
	
	//##### Row Type #####
	private String resultRowType;
	public String getResultRowType() {
		return resultRowType;
	}
	public void setResultRowType(String resultRowType) {
		this.resultRowType = resultRowType;
	}
	
	//##### Expect #####
	private IExpect element4Expect, element5Expect;
	public void setExpectType(String type){
		if(type.equals("POSITIVE")){
			this.element4Expect = new Element4.PatternPositive();
			this.element5Expect = new Element5.PatternPositive();
		} else if(type.equals("NEGTIVE")){
			this.element4Expect = new Element4.PatternNegtive();
			this.element5Expect = new Element5.PatternNegtive();
		}
	}
	
	public IExpect getElement4Expect() {
		return element4Expect;
	}	
	public IExpect getElement5Expect() {
		return element5Expect;
	}

	
	//##### FOLDER #####
	private String destinationFolder;
	public String getDestinationFolder() {
		StringBuilder sb = new StringBuilder();
		for(IElementBuilder eBuilder : this.getElementBuilders())
			sb.append(eBuilder.getLength());
		return destinationFolder+sb.toString();
	}
	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}
	
	//##### ElementBuilder #####
	private IElementBuilder[] elementBuilders;
	public IElementBuilder[] getElementBuilders() {
		return elementBuilders;
	}
	public void setElementBuilders(IElementBuilder[] elementBuilders) {
		this.elementBuilders = elementBuilders;
	}
}
