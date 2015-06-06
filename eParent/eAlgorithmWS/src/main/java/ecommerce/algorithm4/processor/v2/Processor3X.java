package ecommerce.algorithm4.processor.v2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor3X implements IProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(Processor3X.class);

	private int offset;
	private boolean[] source;
	private char[] expect;
	private int cycleStep;
	private int maxStep;
	private List<Integer> procedure;
	
	public Processor3X(boolean[] source, int offset, int cycleStep, char[] expect){
		this.offset = offset;
		this.source = source;
		this.cycleStep = cycleStep;
		this.expect = expect;
	}
	
	@Override public int getMaxStep() { return maxStep; }	
	@Override public List<Integer> getProcedure(){ return this.procedure; }
	
	@Override
	public int execute() {
		
		int totalSum = 0;
		this.procedure = new ArrayList<Integer>();
		List<Integer> steps = new ArrayList<Integer>();
		ICycle cycle = null;
		for(int i=0; i+offset<this.source.length; i+=cycleStep){
			
			char[] subExpect = new char[cycleStep];
			for(int tmp=0; tmp<cycleStep && tmp+i<this.expect.length; tmp++)
				subExpect[tmp] = this.expect[tmp+i];

			if(steps.size() == 0){
				cycle = new Cycle(1, subExpect);
			} else {
				int step = 2;
				for(int var : steps)
					step += Math.abs(var);
				cycle = new Cycle(step, subExpect);
			}
			
			int length = this.offset+i+(cycleStep-1)<=this.source.length ? cycleStep : this.source.length-(this.offset+i);
			cycle.execute(this.source, this.offset+i, length);
			int sum = cycle.getSum();
			steps.add(sum);
			
			//bStop 代表 Cycle中间停止
			boolean bStop = false;
			for(int index=0; !bStop && index<cycle.getProcess().size(); index++){
				int val = cycle.getProcess().get(index);
				totalSum += val;
				if(this.maxStep < Math.abs(val))
					this.maxStep = Math.abs(val);
				
				logger.debug(String.format("%s%d", val<0?"":"+", val));
				this.procedure.add(val);
				if(totalSum >= 2)
					bStop = true;
			}
			//下面两种情况代表了处理结束,这两种情况下是没有期待值的
			if(bStop){//1.整个处理的SUM>=2
				logger.debug(String.format("=%d {MAX:%d}\r\n", totalSum, this.maxStep));
				return 0;
			}
			if(i==0 && sum == 0 && cycle.getProcess().size()==this.cycleStep){//第一个周期完成,并且SUM==0
				logger.debug(String.format("=%d {MAX:%d}\r\n", totalSum, this.maxStep));
				return 0;
			}
		}

		logger.debug(String.format("=%d {MAX:%d}\r\n", totalSum, this.maxStep));
		if(cycle == null){
			return 1;
		}
		
		if(cycle.getProcess().size() == this.cycleStep){//当前cycle已完成
			
			int step = 2;
			for(int var : steps)
				step += Math.abs(var);
			return step;
		} else {//当前Cycle未完成
			return cycle.getStep();
		}
	}
}
