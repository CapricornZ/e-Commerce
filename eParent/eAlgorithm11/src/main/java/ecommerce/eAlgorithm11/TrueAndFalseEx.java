package ecommerce.eAlgorithm11;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.ITrueAndFalse;
import ecommerce.patterns.trueandfalse.gonext.IGoNext;
import ecommerce.patterns.trueandfalse.stop.IStop;


public class TrueAndFalseEx implements ITrueAndFalse {
	
	private static final Logger logger = LoggerFactory.getLogger(TrueAndFalseEx.class);
	
	private ITrueAndFalse taf;
	private List<List<Boolean>> source;
	public TrueAndFalseEx(List<List<Boolean>> source){
		this.source = source;
		List<Boolean> total = new ArrayList<Boolean>();
		for(List<Boolean> item : this.source)
			total.addAll(item);
		this.taf = new TrueAndFalse(total);
	}
	@Override
	public void print() {
		for(List<Boolean> swap : this.source){
			for(Boolean val : swap)
				logger.info(val?"o":"x");
			logger.info(" ");
		}
		logger.info("\r\n");
		this.taf.print();
	}

	@Override
	public void run(int offset) {
		this.taf.run(0+this.source.get(0).size());
	}

	@Override
	public int getSum() {return this.taf.getSum();}

	@Override
	public int getMax() {return this.taf.getMax();}

	@Override
	public int getCurrent() {return this.taf.getCurrent();}

	@Override
	public int getResultPos() {return this.taf.getResultPos();}

	@Override
	public int getMetaPos() {return this.taf.getMetaPos();}

	@Override
	public int getCountTrue() {return this.taf.getCountTrue();}

	@Override
	public int getCountFalse() {return this.taf.getCountFalse();}

	@Override
	public List<Boolean> getResult() {return this.taf.getResult();}

	@Override
	public void setStop(IStop stop) {this.taf.setStop(stop);}

	@Override
	public void setGoNext(IGoNext next) {this.taf.setGoNext(next);}

	@Override
	public List<Integer> getProcess() {return this.taf.getProcess();}

}
