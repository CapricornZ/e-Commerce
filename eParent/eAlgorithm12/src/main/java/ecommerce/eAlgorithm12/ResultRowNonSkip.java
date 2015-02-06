package ecommerce.eAlgorithm12;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.Context;
import ecommerce.base.IResultRow;
import ecommerce.base.IRow;
import ecommerce.base.ITrueAndFalse;
import ecommerce.eAlgorithm12.element.IElement;
import ecommerce.eAlgorithm12.element.IElementBuilder;

/***
 * 1. 动态生成Element（跟算法11相比）
 * 2. 出现双叉，列4->列5交换的时候，不空一行
 * 3. 右边新起列和左边作长度比较，以长度较短的为基础和另一列比较出结果
 * @author martin
 *
 */
public class ResultRowNonSkip implements IResultRow,IGetPositions {
	
	//----- static -----
	static private final Logger logger = LoggerFactory.getLogger(ResultRowNonSkip.class);
	
	static private IElementBuilder[] elementBuilder;
	static public void setElementBuilder(IElementBuilder[] builder){ResultRowNonSkip.elementBuilder = builder;}
	
	static private Swap swap;
	static public void setSwap(Swap swap){ResultRowNonSkip.swap = swap;}
	//----- static -----

	private String source;
	private List<IElement> elements;
	private int startOff, indexOfCreateElement;
	public ResultRowNonSkip(String source){
		this.source = source;
	}
	
	private List<RelayPosition> relayPos;
	@Override
	public List<RelayPosition> getRelayPositions(){return this.relayPos;}
	
	@Override
	public List<ITrueAndFalse> getResult(){
		
		List<List<Boolean>> total = new ArrayList<List<Boolean>>();
		this.relayPos = new ArrayList<RelayPosition>();
		this.startOff = 0;
		this.indexOfCreateElement = 0;
		this.elements = new ArrayList<IElement>();
		IElement element = ResultRowNonSkip.elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
		startOff += element.getSource().length;
		this.elements.add(element);
		Swap swap = ResultRowNonSkip.swap.createSwap();
		
		for(;startOff<source.length();){
			
			IElement current = ResultRowNonSkip.elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
			IElement last = this.elements.get(this.elements.size()-1);
			List<Boolean> result = null;
			if(current.getSource().length>last.getSource().length)
				result = last.execute(current, false);//write result to current
			else
				result = current.execute(last, true);//write result to current
			total.add(result);
			
			startOff += current.getSource().length;
			this.elements.add(current);
			
			//if(result.size()==2 && !result.get(0) && !result.get(1)){//swap
			if(swap.exam(result)){//swap
				indexOfCreateElement = (indexOfCreateElement+1)%2;
				this.relayPos.add(new RelayPosition(startOff-2, startOff-1));
			}
		}
		
		List<ITrueAndFalse> rtn = new ArrayList<ITrueAndFalse>();
		rtn.add(new TrueAndFalseEx(total));
		return rtn;
	}
	
	@Override
	public IRow run() {
		return null;
	}

	@Override
	public void print() {
		
		int max=0;
		for(IElementBuilder eb : elementBuilder)
			max = eb.getLength()>max?eb.getLength():max;
		for(int rowIndex=0; rowIndex<max; rowIndex++){
			StringBuilder sbRow = new StringBuilder();
			for(int column=0; column<this.elements.size(); column++)
				sbRow.append(this.elements.get(column).getValue(rowIndex));
			logger.debug("{}\r\n", sbRow.toString());
		}
	}
	
	@Override
	public Context getContext() {

		int max=0;
		for(IElementBuilder eb : elementBuilder)
			max = eb.getLength()>max?eb.getLength():max;
		List<String> rows = new ArrayList<String>();
		for(int rowIndex=0; rowIndex<max; rowIndex++){
			StringBuilder sbRow = new StringBuilder();
			for(int column=0; column<this.elements.size(); column++)
				sbRow.append(this.elements.get(column).getHtml(rowIndex));
			rows.add(sbRow.toString());
		}
		Context rtn = new Context();
		rtn.put("RESULT_ROW", rows);
		return rtn;
	}
}
