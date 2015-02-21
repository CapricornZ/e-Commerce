package elements.algorithmX;

import java.util.ArrayList;
import java.util.List;

import eAlgorithmWS.ISourceRow;
import eAlgorithmWS.ITrueAndFalse;
import eAlgorithmWS.Item;
import eAlgorithmWS.TrueAndFalseSeperate;
import elements.IElement;
import elements.builder.IElementBuilder;

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
	public ITrueAndFalse execute(ISourceRow source) {
		
		int startOff = 0;
		int indexOfBuilder = 0;
		boolean stop = false;
		List<IElement> elements = new ArrayList<IElement>();
		Swap swap = Swap.createSwap(this.typeOfSwap);
		List<List<eAlgorithmWS.Item>> totalResult = new ArrayList<List<eAlgorithmWS.Item>>();
		while(!stop){
			
			IElement current = this.builders[indexOfBuilder].createElement(source.getSource(), startOff);
			elements.add(current);
			startOff += current.getLength();
			stop = startOff>=source.getSource().length();
			
			if(elements.size()>1){

				IElement last = elements.get(elements.size()-2);
				List<Item> result = current.execute(last);
				for(Item item:result)
					item.setIndex(item.getIndex()+source.getStartOff());
				totalResult.add(result);
				
				if(swap.exam(result)){
					indexOfBuilder = (indexOfBuilder+1)%2;
					if(current.getLength() == 4){//新建一列
						IElement skipElement = this.builders[indexOfBuilder].createElement(source.getSource(), startOff);
						skipElement.setSkipFlag();
						startOff += skipElement.getLength();
						elements.add(skipElement);
					}
				}
			}
		}
		
		ITrueAndFalse taf = new TrueAndFalseSeperate(totalResult);
		taf.run(0);
		
		return taf;
	}

}
