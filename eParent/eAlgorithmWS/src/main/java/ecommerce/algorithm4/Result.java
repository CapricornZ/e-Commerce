package ecommerce.algorithm4;

import java.util.List;

public class Result {

	private List<Integer> source;
	private int sum;
	private int max;
	private boolean hasExpect;
	private int expect;
	public Result(List<Integer> source, int sum, int max, boolean hasExpect){
		this.source = source;
		this.sum = sum;
		this.max = max;
		this.hasExpect = hasExpect;
	}
	
	public List<Integer> getSource(){ return this.source; }
	public boolean hasExpect(){ return this.hasExpect; }
	public int getExpect(){ return this.expect; }
	
	public Result(List<Integer>source, int max, int expect){
		
		this.source = source;
		this.max = max;
		this.hasExpect = expect==0?false:true;
		this.expect = expect;
		for(int val : source)
			this.sum += val;
	}
	
	public String getFormated(){
		
		StringBuilder sb = new StringBuilder();
		for(int val : this.source)
			if(val>0)
				sb.append(String.format("+%d", val));
			else
				sb.append(String.format("%d", val));
		sb.append(String.format(" = %d  MAX:%d", sum, max));
		return sb.toString();
	}
}
