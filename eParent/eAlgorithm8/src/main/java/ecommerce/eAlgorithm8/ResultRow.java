package ecommerce.eAlgorithm8;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.Decide;
import ecommerce.base.IResultRow;
import ecommerce.base.IRow;
import ecommerce.base.ITrueAndFalse;

public class ResultRow implements IResultRow {
	
	private static Logger logger = LoggerFactory.getLogger(ResultRow.class);
	
	private List<Decide> data;
	public ResultRow(List<Decide> data){
		this.data = data;			
	}
	
	@Override
	public List<ITrueAndFalse> getResult() {
		
		List<Boolean> tmp = new ArrayList<Boolean>();
		for(Decide decide:data)
			if(decide != Decide.INVALID && decide != Decide.SKIP)
				tmp.add(decide==Decide.TRUE?true:false);
		
		List<ITrueAndFalse> rtn = new ArrayList<ITrueAndFalse>();
		rtn.add(new TrueAndFalse(tmp));
		return rtn;
	}

	@Override
	public IRow run() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print() {
		/*int length = this.data.size()/4;
		for(int i=0; i<4; i++)
			for(int j=0; j<length; j++){
				if(j==0)
					logger.debug("---{}", this.data.get(j*4+i).getVal());
				else if(j==length-1)
					logger.debug("{}\r\n", this.data.get(j*4+i).getVal());
				else
					logger.debug("{}", this.data.get(j*4+i).getVal());
			}*/
		logger.info("------------");
		for(int i=0; i<this.data.size(); i++)
			logger.info("{}", this.data.get(i).getVal());
		logger.info("\r\n");
	}
}
