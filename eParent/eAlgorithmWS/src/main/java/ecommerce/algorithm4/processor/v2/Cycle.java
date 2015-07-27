package ecommerce.algorithm4.processor.v2;

import java.util.ArrayList;
import java.util.List;

/***
 * 期待oxoxoxox……
 * @author martin
 *
 */
public class Cycle implements ICycle {
	
	private int step;
	private int sum = 0;
	private List<Integer> process = new ArrayList<Integer>();
	
	private boolean[] pattern;/*oxoxox...或者xoxoxo...*/
	/***
	 * 对应的是页面上的正反开关
	 */
	private char[] expect;
	public Cycle(int step, char[] expect, boolean[] pattern){
		this.step = step; 
		this.expect = expect;
		this.pattern = pattern;
	}
	
	@Override public int getSum(){ return this.sum; }
	@Override public int getStep() { return this.step; }
	@Override public List<Integer> getProcess(){ return this.process; }

	@Override
	public void execute(boolean[] source, int offset, int length) {

		this.sum = 0;
		int maxLen = offset+length < source.length ? offset+length : source.length;
		for(int i=0; offset+i<maxLen; i++){
			
			if(this.expect[i] == '+'){//如果相同则+step
				
				if(source[offset+i] != this.pattern[i]){
					this.sum -= this.step;
					this.process.add(-this.step);
				} else {
					this.sum += this.step;
					this.process.add(+this.step);
				}
			} else {//如果不同则+step
				
				if(source[offset+i] == this.pattern[i]){
					this.sum -= this.step;
					this.process.add(-this.step);
				} else {
					this.sum += this.step;
					this.process.add(+this.step);
				}
			}
		}
	}

}
