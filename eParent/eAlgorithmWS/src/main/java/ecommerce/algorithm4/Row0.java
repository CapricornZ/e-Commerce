package ecommerce.algorithm4;
import java.util.ArrayList;
import java.util.List;

public class Row0 implements IRow{
	
	private List<Integer> source;
	public Row0(List<Integer> source){
		this.source = source;
	}
	
	public List<TrueAndFalse> run(){
		
		List<TrueAndFalse> rtn = new ArrayList<TrueAndFalse>();
		List<Boolean> result0 = new ArrayList<Boolean>();
		for(int i=0; i<source.size()-8; i++){
			int first = this.source.get(i);
			int second = this.source.get(i+8);
			result0.add(first==second);
		}rtn.add(new TrueAndFalse(result0));
		
		return rtn;
	}

}
