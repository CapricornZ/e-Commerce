package ecommerce.algorithm4;


import java.util.ArrayList;
import java.util.List;

public class Row implements IRow{
	
	private List<Integer> source0;
	private List<Integer> source1;
	public Row(List<Integer> source0, List<Integer> source1){
		this.source0 = source0;
		this.source1 = source1;
	}
	
	public List<TrueAndFalse> run(){
		
		List<TrueAndFalse> rtn = new ArrayList<TrueAndFalse>();
		List<Boolean> result0 = new ArrayList<Boolean>();
		for(int i=0; i<source0.size()-4; i++){
			int first = this.source0.get(i);
			int second = this.source0.get(i+4);
			result0.add(first==second);
		}rtn.add(new TrueAndFalse(result0));
		
		List<Boolean> result1 = new ArrayList<Boolean>();
		for(int i=0; i<source1.size()-8; i++){
			int first = this.source1.get(i);
			int second = this.source1.get(i+8);
			result1.add(first==second);
		}rtn.add(new TrueAndFalse(result1));
		return rtn;
	}

}
