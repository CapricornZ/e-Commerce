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
public class RowSkip implements IAlgorithm {
	
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
				List<Boolean> result = current.execute(last);
				if(swap.exam(result)){
					indexOfBuilder = (indexOfBuilder+1)%2;
					if(current.getLength() == 4){//新建一列
						IElement skipElement = this.builders[indexOfBuilder].createElement(source, startOff);
						skipElement.setSkipFlag();
						startOff += skipElement.getLength();
						elements.add(skipElement);
					}
				}
			}
		}
		char rtn = 0;
		if(elements.size()>=2)
			rtn = elements.get(elements.size()-1).nextItem(elements.get(elements.size()-2));
		
		Element result = new Element();
		result.setAlgorithm("ROWSKIP@ALG12");
		result.setNextItem(rtn==0?"ANY":String.format("%c", rtn));
		return result;
	}
}
