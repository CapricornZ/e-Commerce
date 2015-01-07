package ecommerce.eAlgorithm6;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.Decide;
import ecommerce.base.IRow;

/***
 * Example : 223232232222333232322323323
 * @author martin
 *
 */
public class NumberRow implements IRow {
	
	private static Logger logger = LoggerFactory.getLogger(NumberRow.class);
	
	private List<List<Integer>> result;
	private List<Integer> finalRS;
	public NumberRow(List<List<Integer>> data){
		this.result = data;
		
		this.finalRS = new ArrayList<Integer>();
		for(int i=0; i<result.get(3).size(); i++){
			finalRS.add(result.get(0).get(i));
			finalRS.add(result.get(1).get(i));
			finalRS.add(result.get(2).get(i));
			finalRS.add(result.get(3).get(i));
		}
		
		if(result.get(0).size() != result.get(3).size())
			finalRS.add(result.get(0).get(result.get(0).size()-1));
		
		if(result.get(1).size() != result.get(3).size())
			finalRS.add(result.get(1).get(result.get(1).size()-1));
		
		if(result.get(2).size() != result.get(3).size())
			finalRS.add(result.get(2).get(result.get(2).size()-1));
	}

	@Override
	public IRow run() {
		
		List<Decide> decides = new ArrayList<Decide>();
		/*
		 * 2 vs 3 比例
		 * 当2的个数大于3，vs>0
		 * 当2的个数小于3，vs<0
		 */
		int vs = 0;
		boolean match2=false,match3=false;
		for(Integer val : this.finalRS){
			if(match3){
				decides.add(val==3?Decide.TRUE:Decide.FALSE);
				//logger.debug("{}", val==3?"o":"x");
			}
			else if(match2){
				decides.add(val==2?Decide.TRUE:Decide.FALSE);
				//logger.debug("{}", val==2?"o":"x");
			}
			else{
				decides.add(Decide.INVALID);
				//logger.debug("_");
			}
			
			vs += val==2?+1:-1;
			if(vs >= 2){
				match3=true;
				match2=false;
			} else if(vs <=-2){
				match3=false;
				match2=true;
			} else {
				match3=false;
				match2=false;
			}
		}
		//logger.debug("\r\n");

		return new ResultRow(decides);
	}

	@Override
	public void print() {
		for(List<Integer> row : this.result){
			StringBuilder sb = new StringBuilder();
			for(Integer val : row)
				sb.append(val);
			logger.info("---{}\r\n", sb.toString());
		}
	}

}
