package eAlgorithmWS;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elements.patterns.IGoNext;
import elements.patterns.IStop;

public class TrueAndFalse implements ITrueAndFalse {

	// ----- static -----
	static private final Logger logger = LoggerFactory.getLogger(TrueAndFalse.class);
	private static int[] metaData = new int[] {1,3,7,15,31,63,127,255,511,1023,2047,4095,8191};
	
	static private IStop stop;
	static public void setStop(IStop stop){
		TrueAndFalse.stop = stop;
	}
	
	static private IGoNext goNext;
	static public void setGoNext(IGoNext next){
		TrueAndFalse.goNext = next;
	}
	// ----- static -----
	
	private List<eAlgorithmWS.Item> source;
	public TrueAndFalse(List<eAlgorithmWS.Item> source){
		this.source = source;
	}
	
	private int countTrue, countFalse;
	private int max, sum, metaIndex, sourceIndex, current;
	@Override
	public void run(int offset) {
		
		this.countTrue = 0;
		this.countFalse = 0;
		this.max = 0;
		this.sum = 0;
		metaIndex = 0;
		boolean shouldStop = false;
		for (this.sourceIndex = offset; !shouldStop && this.sourceIndex < this.source.size(); this.sourceIndex++) {
			
			int current = metaData[this.metaIndex];
			if(max < current)//记录最大值
				max = current;

			if (this.source.get(this.sourceIndex).getValue()) {	this.countTrue++;
				sum += metaData[this.metaIndex];
				logger.info("+{}", metaData[this.metaIndex]);
				this.current = metaData[this.metaIndex];
				//logger.info("+{}", metaData[this.metaIndex]);
				if (this.metaIndex != 0)
					this.metaIndex--;
			} else {	this.countFalse++;
				sum -= metaData[this.metaIndex];
				logger.info("-{}", metaData[this.metaIndex]);
				this.current = -metaData[this.metaIndex];
				//logger.info("-{}", metaData[this.metaIndex]);
				this.metaIndex++;
			}
			this.source.get(this.sourceIndex).setCount(this.current);

			this.metaIndex = TrueAndFalse.goNext.GetNext(this);
			shouldStop = TrueAndFalse.stop.match(this);
		}
	}
	
	@Override public int getSourcePos() { return this.sourceIndex+1; }
	@Override public List<eAlgorithmWS.Item> getSource() { return this.source; }
	@Override public int getMetaPos() { return this.metaIndex; }
}
