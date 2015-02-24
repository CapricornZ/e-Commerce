package ecommerce.eAlgorithm12.element;

import ecommerce.eAlgorithm12.IExpect;

public class Element4Builder implements IElementBuilder{
	@Override
	public IElement createElement(String source, int startOff) {
		int length = startOff+4<source.length()?4:source.length()-startOff;
		char[] data = source.substring(startOff, startOff+length).toCharArray();
		return new Element4(data, this.expect);
	}
	
	@Override public int getLength(){ return 4; }
	
	private IExpect expect;
	@Override public IExpect getExpect(){ return this.expect; }
	@Override public void setExpect(IExpect expect){ this.expect = expect; }
}