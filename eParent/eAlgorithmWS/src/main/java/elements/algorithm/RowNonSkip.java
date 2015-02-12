package elements.algorithm;

import java.util.ArrayList;
import java.util.List;

import elements.IElement;
import elements.builder.IElementBuilder;
import elements.controller.Element;

/***
 * SKIP ROW
 * @author martin
 *
 */
public class RowNonSkip implements IAlgorithm {
	
	private IElementBuilder[] builders;
	public void setBuilders(IElementBuilder[] builders){
		this.builders = builders;
	}
	
	private String typeOfSwap;
	public void setSwap(String type){
		this.typeOfSwap = type;
	}

	@Override
	public Element nextItem(String source) {
		
		int startOff = 0;
		int indexOfBuilder = 0;
		boolean stop = false;
		Swap swap = Swap.createSwap(this.typeOfSwap);
		List<IElement> elements = new ArrayList<IElement>();
		while(!stop){
			
			IElement current = this.builders[indexOfBuilder].createElement(source, startOff);
			elements.add(current);
			startOff += current.getLength();
			stop = startOff>=source.length();
			
			if(elements.size()>1){
				IElement last = elements.get(elements.size()-2);
				List<Boolean> result = null;
				if(current.getSource().length>last.getSource().length)
					result = last.execute(current);//write result to current
				else
					result = current.execute(last);//write result to current
				if(swap.exam(result))
					indexOfBuilder = (indexOfBuilder+1)%2;
			}
		}
		
		char rtn = 0;
		if(elements.size()>=2)
			rtn = elements.get(elements.size()-1).nextItem(elements.get(elements.size()-2));
		
		Element result = new Element();
		result.setAlgorithm("RowNonSkip@ALG12");
		result.setNextItem(rtn==0?"ANY":String.format("%c", rtn));
		return result;
	}

}
