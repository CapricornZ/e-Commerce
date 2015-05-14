package ecommerce.algorithm4.processor;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor3X implements IProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(Processor3X.class);
	
	private int offset;
	private boolean[] source;
	private int cycleStep;
	private String class3X;
	public Processor3X(boolean[] source, int offset, int cycleStep, String class3X){
		this.offset = offset;
		this.source = source;
		this.cycleStep = cycleStep;
		this.class3X = class3X;
	}
	
	private int maxStep;
	@Override
	public int getMaxStep() { return maxStep; }
	
	private List<Integer> procedure;
	@Override
	public List<Integer> getProcedure(){ return this.procedure; }
	
	@Override
	public boolean execute() {

		boolean finished = false;
		this.procedure = new ArrayList<Integer>();
		Constructor<ICycle> constructor = null;
		try {
			@SuppressWarnings("unchecked")
			Class<ICycle> Cycle = (Class<ICycle>) Class.forName(this.class3X);
			constructor = Cycle.getConstructor(int.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int cycleStep = this.cycleStep;
		logger.debug("3X FOUND");
		
		int totalSum = 0;
		List<Integer> steps = new ArrayList<Integer>();
		for(int i=0; i+offset<this.source.length; i+=cycleStep){

			ICycle cycle = null;
			if(steps.size() == 0){
				
				try {
					cycle = (ICycle) constructor.newInstance(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
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

			boolean bStop = false;
			for(int index=0; !bStop && index<cycle.getProcess().size(); index++){
				int val = cycle.getProcess().get(index);
				totalSum += val;
				if(this.maxStep < Math.abs(val))
					this.maxStep = Math.abs(val);
				
				logger.debug(String.format("%s%d", val<0?"":"+", val));
				this.procedure.add(val);
				if(totalSum >= 2){
					bStop = true;
					finished = true;
				}
			}
			if(bStop)
				break;
			if(i==0 && sum == 0){
				finished = true;
				break;
			}
		}

		logger.debug(String.format("=%d {MAX:%d}\r\n", totalSum, this.maxStep));
		
		/*if(this.offset+this.procedure.size()<this.source.length)
			return true;
		else
			return false;*/
		return finished;
	}

}
