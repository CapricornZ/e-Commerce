package ecommerce.eAlgorithm9;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.IResultRow;
import ecommerce.base.IRow;
import ecommerce.base.ITrueAndFalse;

public class ResultRow implements IResultRow {
	
	private static Logger logger = LoggerFactory.getLogger(ResultRow.class);
	
	private List<Element> data;
	public ResultRow(List<Element> data){
		this.data = data;
	}

	@Override
	public IRow run() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
	}

	@Override
	public List<ITrueAndFalse> getResult() {
		
		List<Boolean> result = new ArrayList<Boolean>();
		for(int i=1; i<this.data.size()-1; i++){
			Element currentElement = this.data.get(i+1);
			Paired pairedElement = this.findMostPaired(i);
			if( pairedElement != null ){
				
				List<Boolean> rtn = currentElement.execute(pairedElement.pairElement, pairedElement.pair);
				result.addAll(rtn);
			}
		}
		
		List<ITrueAndFalse> rtn = new ArrayList<ITrueAndFalse>();
		rtn.add(new TrueAndFalse(result));
		return rtn;
	}
	
	private Paired findMostPaired(int index){
		
		Paired rtn = null;
		Element element = this.data.get(index);
		boolean foundPositive = false, foundNegtive = false;
		for(int i=index-1; !foundPositive && i>=0; i--){
			
			Element compare = this.data.get(i);
			if(compare.pair(element) == Pair.POSITIVE){
				foundPositive = true;
				if(index-1==i)
					rtn = new Paired(compare, Pair.POSITIVE);
				else
					rtn = new Paired(this.data.get(i+1), Pair.POSITIVE);
			}
			
			if(!foundNegtive && compare.pair(element) == Pair.NEGTIVE){
				foundNegtive = true;
				if(index-1==i)
					rtn = new Paired(compare, Pair.NEGTIVE);
				else
					rtn = new Paired(this.data.get(i+1), Pair.NEGTIVE);
			}
		}

		return rtn;
	}
	
	class Paired{
		public Pair pair;
		public Element pairElement;
		
		public Paired(Element element, Pair pair){
			this.pair = pair;
			this.pairElement = element;
		}
	}
}
