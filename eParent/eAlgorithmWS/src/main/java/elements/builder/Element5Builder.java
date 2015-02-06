package elements.builder;

import elements.Element5;
import elements.IElement;
import elements.IExpect;


public class Element5Builder implements IElementBuilder{
	@Override
	public IElement createElement(String source, int startOff) {
		int length = startOff+5<source.length()?5:source.length()-startOff;
		char[] data = source.substring(startOff, startOff+length).toCharArray();
		return new Element5(this.getExpect(), data);
	}
	
	private IExpect expect;
	@Override public IExpect getExpect(){ return this.expect; }
	@Override public void setExpect(IExpect expect){ this.expect = expect; }
}