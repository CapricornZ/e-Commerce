package elements.builder;

import elements.IElement;
import elements.IExpect;


public interface IElementBuilder {
	IElement createElement(String source, int startOff);
	IExpect getExpect();
	void setExpect(IExpect expect);
}
