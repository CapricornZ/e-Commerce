package ecommerce.algorithm4.processor.v2;

import java.util.ArrayList;
import java.util.List;

/***
 * 在Cycle for 3o的基础上修改：
 * 第一位是+-2；
 *	后面如果累计值为负，则+-1；
 *	如果后面的累计值为0，则+02；
 * 
 * @author martin
 *
 */
public class Cycle4TripleXFirst implements ICycle {
	
	private int step;
	private int sum = 0;
	private List<Integer> process = new ArrayList<Integer>();
	
	private char[] expect;
	public Cycle4TripleXFirst(int step){
		this.step = step;
	}
	
	public Cycle4TripleXFirst(int step, char[] expect){
		this.step = step; 
		this.expect = expect;
	}

	@Override public int getSum(){ return this.sum; }
	@Override public int getStep() {
		if(sum<0)
			return 1;
		else
			return 2;
	}
	@Override public List<Integer> getProcess(){ return this.process; }
	@Override
	public boolean getExpectItem() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(boolean[] source, int offset, int length) {
		
		this.sum = 0;
		int maxLen = offset+length < source.length ? offset+length : source.length;
		for(int i=0; offset+i<maxLen; i++)
			if(this.expect[i] == '+'){
				
				if(sum<0){
					if(!source[offset+i]){//遇到X -》 -step
						this.sum -= 1;
						this.process.add(-1);
					} else {//遇到O -》 +step
						this.sum += 1;
						this.process.add(1);
					}
				} else if(sum==0){
					if(!source[offset+i]){//遇到X -》 -step
						this.sum -= 2;
						this.process.add(-2);
					} else {//遇到O -》 +step
						this.sum += 2;
						this.process.add(2);
					}
				}
			} else {
				
				if(sum<0){
					if(!source[offset+i]){//遇到X -》+step
						this.sum += 1;
						this.process.add(1);
					} else {//遇到O -》-step
						this.sum -= 1;
						this.process.add(-1);
					}					
				} else if (sum==0){
					if(!source[offset+i]){//遇到X -》+step
						this.sum += 2;
						this.process.add(2);
					} else {//遇到O -》-step
						this.sum -= 2;
						this.process.add(-2);
					}
				}
				
			}
	}
}
