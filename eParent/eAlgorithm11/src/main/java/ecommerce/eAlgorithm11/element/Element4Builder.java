package ecommerce.eAlgorithm11.element;

import java.util.ArrayList;
import java.util.List;

public class Element4Builder implements IElementBuilder {

	@Override
	public List<IElement> createElement(String source) {
		
		List<IElement> rtn = new ArrayList<IElement>();
		for(int i=0; i<source.length()/4; i++)
			rtn.add(new Element4(source.substring(i*4, i*4+4).toCharArray()));
		if(source.length()%4>0){
			int i=source.length()/4;
			rtn.add(new Element4(source.substring(i*4, source.length()).toCharArray()));
		}
		return rtn;
	}

	@Override
	public int getLength() {
		return 4;
	}

}
