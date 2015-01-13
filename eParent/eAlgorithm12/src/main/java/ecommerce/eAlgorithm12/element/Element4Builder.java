package ecommerce.eAlgorithm12.element;


public class Element4Builder implements IElementBuilder{
	@Override
	public IElement createElement(String source, int startOff) {
		int length = startOff+4<source.length()?4:source.length()-startOff;
		char[] data = source.substring(startOff, startOff+length).toCharArray();
		return new Element4(data);
	}
	
	@Override
	public int getLength(){
		return 4;
	}
}