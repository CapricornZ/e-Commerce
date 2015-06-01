package ecommerce.algorithm4.processor;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor3O implements IProcessor {

	private static final Logger logger = LoggerFactory.getLogger(Processor3O.class);

	private int offset;
	private boolean[] source;
	private int cycleStep;
	private String class3O;
	public Processor3O(boolean[] source, int offset, int cycleStep, String class3O){
		this.offset = offset;
		this.source = source;
		this.cycleStep = cycleStep;
		this.class3O = class3O;
	}
	
	private int maxStep;
	@Override
	public int getMaxStep() { return maxStep; }
	
	private List<Integer> procedure;
	@Override
	public List<Integer> getProcedure(){ return this.procedure; }
	
	@Override
	public int execute() {

		this.procedure = new ArrayList<Integer>();
		Constructor<ICycle> constructor = null;
		try {
			@SuppressWarnings("unchecked")
			Class<ICycle> Cycle = (Class<ICycle>) Class.forName(this.class3O);
			constructor = Cycle.getConstructor(int.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.debug("3O FOUND : ");
		int totalSum = 0;
		List<Integer> steps = new ArrayList<Integer>();
		ICycle cycle = null;
		for(int i=0; i+offset<this.source.length; i+=cycleStep){
			
			if(steps.size() == 0){
				try {
					cycle = (ICycle) constructor.newInstance(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				int step = 2;
				for(int var : steps)
					step += Math.abs(var);
				try {
					cycle = (ICycle) constructor.newInstance(step);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
			//return true;
			return step;
		} else {//当前Cycle未完成
			//return true;
			return cycle.getStep();
		}
	}

}
