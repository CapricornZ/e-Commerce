package ecommerce.algorithm4;

import java.util.List;

public class Result {

	private List<Integer> source;
	private int sum;
	private int max;
	private boolean hasExpect;
	private int expect;
	private char expectItem;
	private String type;
	
	public String getType(){return this.type;}
	
	public int getMaxCycleStep(){
		int max = 0;
		for(int i=0; i<this.source.size(); i++)
			max = max < Math.abs(this.source.get(i)) ? Math.abs(this.source.get(i)) : max; 
		return max;
	}
	public int getCountOfCycle(int cycle){
		return this.source.size() / cycle + (this.source.size() % cycle == 0 ? 0 : 1);
	}

	public Result(List<Integer> source, int sum, int max, boolean hasExpect){
		this.source = source;
		this.sum = sum;
		this.max = max;
		this.hasExpect = hasExpect;
	}
	
	public Result(List<Integer>source, int max, int expect){
		
		this.source = source;
		this.max = max;
		this.hasExpect = expect==0?false:true;
		this.expect = expect;
		for(int val : source)
			this.sum += val;
	}

	public Result(List<Integer>source, int max, int expect, char item, String type){
		
		this.source = source;
		this.type = type;
		this.max = max;
		this.hasExpect = expect==0?false:true;
		this.expect = expect;
		for(int val : source)
			this.sum += val;
		this.expectItem = item;
	}
	
	public List<Integer> getSource(){ return this.source; }
	/***
	 * 是否有期待（算法是否结束）
	 * @return
	 */
	public boolean hasExpect(){ return this.hasExpect; }
	/***
	 * 期待的个数
	 * @return
	 */
	public int getExpect(){ return this.expect; }
	
	public char getExpectItem() { return expectItem; }
	
	public String getFormated(){
		
		StringBuilder sb = new StringBuilder();
		for(int val : this.source)
			if(val>0)
				sb.append(String.format("+%d", val));
			else
				sb.append(String.format("%d", val));
		sb.append(String.format(" = %d MAX:%d", sum, max));
		return sb.toString();
	}
}
