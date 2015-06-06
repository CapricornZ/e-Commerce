package ecommerce.algorithm4.processor.v2;

import java.util.ArrayList;
import java.util.List;

/***
 * Cycle for 3x
 * 根据给定的expect,动态运算每一位
 * @author martin
 *
 */
public class Cycle implements ICycle {
	
	private int step;
	private int sum = 0;
	private List<Integer> process = new ArrayList<Integer>();
	
	private char[] expect;
	public Cycle(int step){
		this.step = step;
	}
	
	public Cycle(int step, char[] expect){
		this.step = step; 
		this.expect = expect;
	}

	@Override public int getSum(){ return this.sum; }
	@Override public int getStep() { return this.step; }
	@Override public List<Integer> getProcess(){ return this.process; }

	@Override
	public void execute(boolean[] source, int offset, int length) {
		
		this.sum = 0;
		int maxLen = offset+length < source.length ? offset+length : source.length;
		for(int i=0; offset+i<maxLen; i++)
			if(this.expect[i] == '+'){
				
				if(source[offset+i]){// "+"&"true" -> +step
					this.sum += this.step;
					this.process.add(this.step);
				} else {// "+"&"false" -> +step
					this.sum -= this.step;
					this.process.add(-this.step);
				}
			} else {
				
				if(source[offset+i]){// "-"&"true" -> +step
					this.sum -= this.step;
					this.process.add(-this.step);
				} else {// "-"&"false" -> +step
					this.sum += this.step;
					this.process.add(this.step);
				}
			}
	}
}
