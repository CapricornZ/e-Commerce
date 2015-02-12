package ecommerce.eAlgorithm12;

import ecommerce.eAlgorithm12.element.Element4;
import ecommerce.eAlgorithm12.element.Element5;
import ecommerce.eAlgorithm12.element.IElementBuilder;

public class AppContext {

	public String getOutput(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.destinationFolder + countOfX+"x/");
		sb.append(this.expectType.equals("POSITIVE")?"正,":"反,");
		sb.append(this.elementBuilders[0] instanceof ecommerce.eAlgorithm12.element.Element4Builder?"先4":"先5");
		sb.append(".html");
		return sb.toString();
	}
	
	private int countOfX;
	public void setCountOfX(int value){
		this.countOfX = value;
		switch(this.countOfX){
		case 6:
			Skip.setLengthLimit(29);break;
		case 7:
			Skip.setLengthLimit(35);break;
		case 8:
			Skip.setLengthLimit(36);break;
		case 9:
			Skip.setLengthLimit(42);break;
		case 10:
			Skip.setLengthLimit(43);break;
		case 11:
			Skip.setLengthLimit(49);break;
		case 12:
			Skip.setLengthLimit(50);break;
		}
	}
	public int getCountOfX(){return this.countOfX;}
	
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
	private String expectType;
	private IExpect element4Expect, element5Expect;
	public void setExpectType(String type){
		this.expectType = type;
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
