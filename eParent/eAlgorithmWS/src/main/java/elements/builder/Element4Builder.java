package elements.builder;

import elements.Element4;
import elements.IElement;
import elements.IExpect;


public class Element4Builder implements IElementBuilder{
	@Override
	public IElement createElement(String source, int startOff) {
		int length = startOff+4<source.length()?4:source.length()-startOff;
		char[] data = source.substring(startOff, startOff+length).toCharArray();
		return new Element4(this.getExpect(), data);
	}
	
	private IExpect expect;
	@Override public IExpect getExpect(){ return this.expect; }
	@Override public void setExpect(IExpect expect){ this.expect = expect; }
}