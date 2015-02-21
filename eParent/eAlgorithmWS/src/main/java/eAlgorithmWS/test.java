package eAlgorithmWS;

import java.util.ArrayList;
import java.util.List;

import elements.Element4;
import elements.IElement;
import elements.algorithmX.RowSkip;
import elements.builder.Element4Builder;
import elements.builder.IElementBuilder;
import elements.patterns.GoNext;
import elements.patterns.IStop;
import elements.patterns.StopOR;
import elements.patterns.StopWhileNX;
import elements.patterns.StopWhileTrue;

public class test {

	public static void main(String[] args) {
		MultiSourceRow multi = new MultiSourceRow("BBABAABBBBBBBAABBBABABAAABBBAABABBBABABAAB");
		
		elements.builder.Element5Builder builder5 = new elements.builder.Element5Builder();
		builder5.setExpect(new elements.Element5.PatternPositive());
		elements.builder.Element4Builder builder4 = new elements.builder.Element4Builder();
		builder4.setExpect(new elements.Element4.PatternPositive());
		
		TrueAndFalse.setGoNext(new GoNext());
		StopOR stop = new StopOR();
		stop.setStops(new IStop[]{new StopWhileTrue(), new StopWhileNX(6)});
		TrueAndFalse.setStop(stop);
		
		RowSkip alg12 = new RowSkip();
		alg12.setBuilders(new IElementBuilder[]{builder5, builder4});
		alg12.setSwap("XX");
		
		List<List<Item>> total = new ArrayList<List<Item>>();
		for(eAlgorithmWS.ISourceRow single : multi.generate()){
			eAlgorithmWS.ITrueAndFalse taf = alg12.execute(single);
			List<Item> source = taf.getSource();
			total.add(source);
		}

		String source = "";
		for(int index=0; index<multi.getSource().length(); index++){
			int max = 0;
			for(List<Item> row:total){
				for(int i=0; i<row.size(); i++){
					if(row.get(i).getIndex() == index)
						max = max>row.get(i).getCount()?max:row.get(i).getCount();
				}
			}
			source += multi.getSource().charAt(index);
			int size = String.format("%d", max).length();
			for(int i=0; i<size-1; i++)
				source += " ";
		}
		System.out.println(source);
		for(List<Item> row:total){
			String result = "";
			int startOff = 0;
			for(Item item:row){
				for(int i=startOff; i<item.getIndex(); i++)
					result += " ";
				
				int count = Math.abs(item.getCount());
				result += count;
				
				startOff = item.getIndex()+1;
			}
			System.out.println(result);
		}
	}
}
