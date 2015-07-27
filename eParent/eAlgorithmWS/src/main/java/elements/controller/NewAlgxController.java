package elements.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.algorithm1.ITrueAndFalse;
import ecommerce.algorithm1.pairs.IPair;
import ecommerce.algorithm1.pairs.PairNegtive;
import ecommerce.algorithm1.pairs.PairPositive;
import ecommerce.algorithm4.Result;
import ecommerce.algorithm4.processor.v2.IProcessor;
import ecommerce.algorithm4.processor.v2.Start;

@Controller
@RequestMapping("/ajax/alg/new")
public class NewAlgxController {

	@RequestMapping("/{type}/preAccept")
	@ResponseBody
	/***
	 * @param type POSITIVE(新算法)|NEGTIVE(新算法`)
	 * @param source
	 * @return
	 */
	public String format(@PathVariable("type")String type, @RequestParam("source")String source){
		
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(type))
			pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow3 sourceRow = new ecommerce.algorithm1.SourceRow3(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();
		return taf.getFormated();
	}
	
	@RequestMapping("/{type}/obtainExpects")
	@ResponseBody
	public List<String> obtainExpects(@PathVariable("type")String type, @RequestParam("source")String source){
		
		final String positive="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
		final String negtive="----------------------------------------------------------------------";
		List<String> rtnExpect = new ArrayList<String>(){{add("");add("");add("");add("");add("");add("");add("");add("");}};
		try{
			
			IPair pairEngine = new PairPositive();
			if("NEGTIVE".equals(type))
				pairEngine = new PairNegtive();
			ecommerce.algorithm1.SourceRow3 sourceRow = new ecommerce.algorithm1.SourceRow3(source, pairEngine);
			ITrueAndFalse taf = sourceRow.execute();
			boolean lastVal = false;
			
			List<Result> rtnResult = new ArrayList<Result>();
			Result r6 = this.acceptSingle(taf, 6, positive, lastVal, "POSITIVE");rtnResult.add(r6);
			Result r7 = this.acceptSingle(taf, 7, positive, lastVal, "POSITIVE");rtnResult.add(r7);
			Result r8 = this.acceptSingle(taf, 8, positive, lastVal, "POSITIVE");rtnResult.add(r8);
			Result r9 = this.acceptSingle(taf, 9, positive, lastVal, "POSITIVE");rtnResult.add(r9);
			
			Result r6n = this.acceptSingle(taf, 6, negtive, lastVal, "NEGTIVE");rtnResult.add(r6n);
			Result r7n = this.acceptSingle(taf, 7, negtive, lastVal, "NEGTIVE");rtnResult.add(r7n);
			Result r8n = this.acceptSingle(taf, 8, negtive, lastVal, "NEGTIVE");rtnResult.add(r8n);
			Result r9n = this.acceptSingle(taf, 9, negtive, lastVal, "NEGTIVE");rtnResult.add(r9n);
			
			for(int i=0; i<4; i++){
				Result result = rtnResult.get(i);
				StringBuilder sb = new StringBuilder();
				for(int j=0; j<result.getSource().size(); j++)
					sb.append('+');
				rtnExpect.set(i, sb.toString());
			}
			for(int i=4; i<8; i++){
				Result result = rtnResult.get(i);
				StringBuilder sb = new StringBuilder();
				for(int j=0; j<result.getSource().size(); j++)
					sb.append('-');
				rtnExpect.set(i, sb.toString());
			}
		} catch (Exception ex){
			
		}
		return rtnExpect;
	}
	
	@RequestMapping("/{type}/accept")
	@ResponseBody
    public List<Object> accept(@PathVariable("type")String type, @RequestParam("source")String source, @RequestParam("expects[]")String[] expectPatterns) {
		
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(type))
			pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow3 sourceRow = new ecommerce.algorithm1.SourceRow3(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();
		
		ecommerce.algorithm1.SourceRow3 sourceRowGuess = new ecommerce.algorithm1.SourceRow3(source+"A", pairEngine);
		ITrueAndFalse tafGuess = sourceRowGuess.execute();
		boolean lastVal = tafGuess.getSource()[tafGuess.getSource().length-1];
		
		List<Object> rtnResult = new ArrayList<Object>();
		Result r6 = this.acceptSingle(taf, 6, expectPatterns[0], lastVal, "POSITIVE");rtnResult.add(r6);
		Result r7 = this.acceptSingle(taf, 7, expectPatterns[1], lastVal, "POSITIVE");rtnResult.add(r7);
		Result r8 = this.acceptSingle(taf, 8, expectPatterns[2], lastVal, "POSITIVE");rtnResult.add(r8);
		Result r9 = this.acceptSingle(taf, 9, expectPatterns[3], lastVal, "POSITIVE");rtnResult.add(r9);
		
		Result r6n = this.acceptSingle(taf, 6, expectPatterns[4], lastVal, "NEGTIVE");rtnResult.add(r6n);
		Result r7n = this.acceptSingle(taf, 7, expectPatterns[5], lastVal, "NEGTIVE");rtnResult.add(r7n);
		Result r8n = this.acceptSingle(taf, 8, expectPatterns[6], lastVal, "NEGTIVE");rtnResult.add(r8n);
		Result r9n = this.acceptSingle(taf, 9, expectPatterns[7], lastVal, "NEGTIVE");rtnResult.add(r9n);

		int sumOfExpect = 0;
		//正
		for(int i=0; i<4; i++){
			Result result = (Result)rtnResult.get(i);
			if(result != null && result.hasExpect())
				sumOfExpect += result.getExpect();
		}
		
		//反
		for(int i=4; i<8; i++){
			Result result = (Result)rtnResult.get(i);
			if(result != null && result.hasExpect())
				sumOfExpect -= result.getExpect();
		}
		
		if(sumOfExpect != 0){
			if("3*O".equals(r6.getType())){
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtnResult.add(String.format("A*%d", Math.abs(sumOfExpect)));
				else
					rtnResult.add(String.format("B*%d", Math.abs(sumOfExpect)));
			}
			else{
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtnResult.add(String.format("B*%d", Math.abs(sumOfExpect)));
				else
					rtnResult.add(String.format("A*%d", Math.abs(sumOfExpect)));
			}
				
		} else {
			rtnResult.add("N/A*0");
		}
		
		return rtnResult;
	}
	
	/***
	 * 
	 * @param taf
	 * @param cycle
	 * @param expectPattern
	 * @param lastVal
	 * @param type 对应算法的正或反
	 * @return
	 */
	protected Result acceptSingle(ITrueAndFalse taf, int cycle, String expectPattern, boolean lastVal, String type){
		
		int expect = 0;
		Result finalRes = null;
		IProcessor processor = Start.findProcessor(taf.getSource(), cycle, expectPattern.toCharArray());
		if(null != processor){
			expect = processor.execute();
			if("POSITIVE".equals(type)){
				if(processor.getClassCycle().endsWith("Cycle4TripleO"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'B':'A', "3*O");
				else if(processor.getClassCycle().endsWith("Cycle4TripleX"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'A':'B', "3*X");
			} else {
				if(processor.getClassCycle().endsWith("Cycle4TripleO"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'A':'B', "3*O");
				else if(processor.getClassCycle().endsWith("Cycle4TripleX"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'B':'A', "3*X");
			}
			//finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, expectValue);
		}
		System.out.println(finalRes.getFormated());
		return finalRes;
	}
	
	@RequestMapping("/accept/{type}/{cycle}")
	@ResponseBody
	public Result xAccept(
			@PathVariable("type")String type, 
			@PathVariable("cycle")int cycle, 
			@RequestParam("source")String source, 
			@RequestParam("expect")String expectPattern){
		
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(type))
			pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow3 sourceRow = new ecommerce.algorithm1.SourceRow3(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();

		int expect = 0;
		Result finalRes = null;
		IProcessor processor = Start.findProcessor(taf.getSource(), cycle, expectPattern.toCharArray());
		if(null != processor){
			expect = processor.execute();
			finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, 'X', "");
		}
		System.out.println(finalRes.getFormated());
		return finalRes;
	}
}
