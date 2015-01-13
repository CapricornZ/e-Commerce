package ecommerce.eAlgorithm12.element;


public class Element5Builder implements IElementBuilder{
	@Override
	public IElement createElement(String source, int startOff) {
		int length = startOff+5<source.length()?5:source.length()-startOff;
		char[] data = source.substring(startOff, startOff+length).toCharArray();
		return new Element5(data);
	}
	
	@Override
	public int getLength(){
		return 5;
	}
}