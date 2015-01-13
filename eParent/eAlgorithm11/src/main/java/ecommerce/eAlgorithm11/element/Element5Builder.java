package ecommerce.eAlgorithm11.element;

import java.util.ArrayList;
import java.util.List;

public class Element5Builder implements IElementBuilder {

	@Override
	public List<IElement> createElement(String source) {

		List<IElement> rtn = new ArrayList<IElement>();
		for(int i=0; i<source.length()/5; i++)
			rtn.add(new Element5(source.substring(i*5, i*5+5).toCharArray()));
		if(source.length()%5>0){
			int i=source.length()/5;
			rtn.add(new Element5(source.substring(i*5, source.length()).toCharArray()));
		}
		return rtn;
	}
	
	@Override
	public int getLength() {
		return 5;
	}
}
