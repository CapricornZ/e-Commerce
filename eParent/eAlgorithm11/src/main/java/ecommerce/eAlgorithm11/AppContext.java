package ecommerce.eAlgorithm11;

import ecommerce.eAlgorithm11.element.IElementBuilder;

public class AppContext {
	
	private IExpect element4Expect, element5Expect;
	private String destinationFolder;
	private IElementBuilder[] elementBuilders;
	
	public IExpect getElement4Expect() {
		return element4Expect;
	}
	public void setElement4Expect(IExpect element4Expect) {
		this.element4Expect = element4Expect;
	}
	public IExpect getElement5Expect() {
		return element5Expect;
	}
	public void setElement5Expect(IExpect element5Expect) {
		this.element5Expect = element5Expect;
	}
	public String getDestinationFolder() {
		return destinationFolder;
	}
	public void setDestinationFolder(String destinationFolder) {
		this.destinationFolder = destinationFolder;
	}
	public IElementBuilder[] getElementBuilders() {
		return elementBuilders;
	}
	public void setElementBuilders(IElementBuilder[] elementBuilders) {
		this.elementBuilders = elementBuilders;
	}
	
	
}
