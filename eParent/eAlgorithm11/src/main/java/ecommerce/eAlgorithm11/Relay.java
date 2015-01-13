package ecommerce.eAlgorithm11;

import java.util.ArrayList;
import java.util.List;

import ecommerce.eAlgorithm11.element.IElement;

/***
 * 接力
 * @author martin
 *
 */
public class Relay {
	
	public class Result{
		public int startOff;
		public List<Boolean> result;
		public RelayPosition position;
	}
	
	private List<IElement> source;
	private int totalSize, lengthOfElement;
	public Relay(List<IElement> source, int length, int lengthOfElement){
		this.source = source;
		this.totalSize = length;
		this.lengthOfElement = lengthOfElement;
	}
	
	public int getLengthOfElement(){
		return this.lengthOfElement;
	}
	
	public List<IElement> getElements(){
		return this.source;
	}
	
	public String getValue(int row, int column){
		return this.source.get(column).getValue(row);
	}
	
	public Result run(int startOff){
		
		Result rtn = new Result(); 
		if(startOff>=this.totalSize-1){
			rtn.result = new ArrayList<Boolean>();
			return rtn;
		}
		
		int length = this.source.get(0).getSource().length;
		int index = startOff/length;//find element where startOff indicates
		index += this.source.get(index).needSkip(startOff)?1:0;//determine if this element should be skip
		int newStartOff = index*length-1;
		List<Boolean> result = new ArrayList<Boolean>();
		for(int i=index; i<this.source.size(); i++){
			
			IElement current = this.source.get(i);
			List<Boolean> tmp = current.execute(this.source.get(i-1));
			result.addAll(tmp);
			newStartOff += length;
			if(tmp.size()==2 && tmp.get(0)==tmp.get(1) && !tmp.get(0)){
				rtn.position = new RelayPosition(newStartOff-1, newStartOff);
				break;
			}
		}
		rtn.startOff = newStartOff;
		rtn.result = result;
		return rtn;
	}
}
