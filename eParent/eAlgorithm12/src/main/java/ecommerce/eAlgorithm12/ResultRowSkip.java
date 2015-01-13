package ecommerce.eAlgorithm12;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.IResultRow;
import ecommerce.base.IRow;
import ecommerce.base.ITrueAndFalse;
import ecommerce.eAlgorithm12.element.IElement;
import ecommerce.eAlgorithm12.element.IElementBuilder;

/***
 * 1. 动态生成Element（跟算法11相比）
 * 2. 出现双叉，列4->列5交换的时候，空一行
 * 3. 是右边新起列为基础，和左边的比较出结果
 * @author martin
 *
 */
public class ResultRowSkip implements IResultRow, IGetPositions{
	
	static private final Logger logger = LoggerFactory.getLogger(ResultRowSkip.class);
	static private IElementBuilder[] elementBuilder;
	static public void setElementBuilder(IElementBuilder[] builder){
		ResultRowSkip.elementBuilder = builder;
	}

	private String source;
	private List<IElement> elements;
	private int startOff, indexOfCreateElement;
	public ResultRowSkip(String source){
		this.source = source;
	}
	
	private List<RelayPosition> relayPos;
	@Override
	public List<RelayPosition> getRelayPositions(){return this.relayPos;}

	@Override
	public List<ITrueAndFalse> getResult() {
		
		this.relayPos = new ArrayList<RelayPosition>();
		List<Boolean> total = new ArrayList<Boolean>();
		this.startOff = 0;
		this.indexOfCreateElement = 0;
		this.elements = new ArrayList<IElement>();
		IElement element = ResultRowSkip.elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
		startOff += element.getSource().length;
		this.elements.add(element);
		
		for(;startOff<source.length();){
			
			IElement current = ResultRowSkip.elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
			IElement last = this.elements.get(this.elements.size()-1);
			List<Boolean> result = current.execute(last);
			total.addAll(result);
			
			startOff += current.getSource().length;
			this.elements.add(current);
			
			if(result.size()==2 && !result.get(0) && !result.get(1)){//swap
				indexOfCreateElement = (indexOfCreateElement+1)%2;
				this.relayPos.add(new RelayPosition(startOff-2, startOff-1));
				if(current.getSource().length == 4){//新建一列
					
					IElement newElement = ResultRowSkip.elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
					startOff += newElement.getSource().length;
					this.elements.add(newElement);
				}
			}
		}
		
		List<ITrueAndFalse> rtn = new ArrayList<ITrueAndFalse>();
		rtn.add(new TrueAndFalse(total));
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

}
