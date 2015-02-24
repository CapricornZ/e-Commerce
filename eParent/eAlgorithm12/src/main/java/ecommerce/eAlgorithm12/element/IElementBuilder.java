package ecommerce.eAlgorithm12.element;

import ecommerce.eAlgorithm12.IExpect;


public interface IElementBuilder {
	IElement createElement(String source, int startOff);
	int getLength();
	
	IExpect getExpect();
	void setExpect(IExpect expect);
}
