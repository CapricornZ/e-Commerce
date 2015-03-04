package ecommerce.eAlgorithm12;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.Context;
import ecommerce.base.IResultRowX;
import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.ITrueAndFalse;
import ecommerce.base.IView;
import ecommerce.eAlgorithm12.element.IElement;
import ecommerce.eAlgorithm12.element.IElementBuilder;
import ecommerce.patterns.resultrow.stop.IStop;

/***
 * 1. 动态生成Element（跟算法11相比）
 * 2. 出现双叉，列4->列5交换的时候，空一行
 * 3. 是右边新起列为基础，和左边的比较出结果
 * @author martin
 *
 */
public class ResultRowSkip implements IResultRowX, IGetPositions{
	
	static public class CompositeView implements IView{
		private List<IView> views = new ArrayList<IView>();
		public void append(IView view){
			this.views.add(view);
		}

		@Override
		public Context getContext() {
			List<Object> content = new ArrayList<Object>();
			for(IView view:this.views){
				Object obj = view.getContext().getContext().get("RESULT_ROW");
				content.add(obj);
			}
			
			Context rtn = new Context();
			rtn.put("RESULT_ROW", content);
			return rtn;
		}
	}
	
	//----- static -----
	static private final Logger logger = LoggerFactory.getLogger(ResultRowSkip.class);
	
	static private Swap swap;
	static public void setSwap(Swap swap){ResultRowSkip.swap = swap;}
	
	static private IStop stop;
	static public void setStop(IStop stop){ResultRowSkip.stop = stop;}
	//----- static -----

	private IElementBuilder[] elementBuilder;
	public void setElementBuilder(IElementBuilder[] builder){this.elementBuilder = builder;}
	
	private String source;
	private List<IElement> elements;
	private int startOff, indexOfCreateElement;
	public ResultRowSkip(String source){
		this.source = source;
	}
	public ResultRowSkip(String source, IElementBuilder[] elementBuilder){
		this.source = source;
		this.elementBuilder = elementBuilder;
	}
	
	private List<RelayPosition> relayPos;
	@Override
	public List<RelayPosition> getRelayPositions(){return this.relayPos;}

	@Override
	public List<ITrueAndFalse> getResult() {

		List<List<Boolean>> total = new ArrayList<List<Boolean>>();
		this.relayPos = new ArrayList<RelayPosition>();
		this.startOff = 0;
		this.indexOfCreateElement = 0;
		this.elements = new ArrayList<IElement>();
		IElement element = elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
		startOff += element.getSource().length;
		this.elements.add(element);
		Swap swap = ResultRowSkip.swap.createSwap();
		
		this.isStop = false;
		for(;startOff<source.length() && !this.isStop;){
			
			IElement current = elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
			IElement last = this.elements.get(this.elements.size()-1);
			List<Boolean> result = current.execute(last, true);
			total.add(result);
			
			startOff += current.getSource().length;
			this.elements.add(current);
			
			//if(result.size()==2 && !result.get(0) && !result.get(1) && bFirst){//swap
			//	bFirst = false;
			if(swap.exam(result)){//swap
				indexOfCreateElement = (indexOfCreateElement+1)%2;
				this.relayPos.add(new RelayPosition(startOff-2, startOff-1));
				int min = this.elementBuilder[0].getLength()<this.elementBuilder[1].getLength() ? this.elementBuilder[0].getLength() : this.elementBuilder[1].getLength();
				if(current.getSource().length == min){//当前列的长度比后一列短,则新建一列
					
					IElement newElement = elementBuilder[indexOfCreateElement].createElement(this.source, this.startOff);
					startOff += newElement.getSource().length;
					this.elements.add(newElement);
				}
			}
			
			this.isStop = ResultRowSkip.stop.match(total);
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
	
	private boolean isStop = false;
	@Override public boolean isStopValid() { return this.isStop; }
	@Override public ISourceRow getSource() {
		StringBuilder sb = new StringBuilder();
		for(IElement element : this.elements)
			sb.append(element.getSource());
		return new SourceRow(sb.toString());
	}
}
