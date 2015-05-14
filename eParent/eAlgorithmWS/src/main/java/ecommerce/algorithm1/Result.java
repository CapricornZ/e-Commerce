package ecommerce.algorithm1;

import java.util.List;

public class Result {

	private List<Integer> source;
	private int sum;
	private int max;
	private boolean stop;
	public Result(List<Integer> source, int sum, int max, boolean stop){
		this.source = source;
		this.sum = sum;
		this.max = max;
		this.stop = stop;
	}
	
	public List<Integer> getSource(){ return this.source; }
	public boolean getStop(){ return this.stop; }
	
	public Result(List<Integer>source, int max, boolean stop){
		
		this.source = source;
		this.max = max;
		this.stop = stop;
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
