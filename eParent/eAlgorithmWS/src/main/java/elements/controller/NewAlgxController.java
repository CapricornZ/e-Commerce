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
	
	@RequestMapping("/{type}/accept")
	@ResponseBody
    public List<Result> accept(@PathVariable("type")String type, @RequestParam("source")String source, @RequestParam("expects[]")String[] expectPatterns) {
		
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(type))
			pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow3 sourceRow = new ecommerce.algorithm1.SourceRow3(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();
		
		ecommerce.algorithm1.SourceRow3 sourceRowGuess = new ecommerce.algorithm1.SourceRow3(source+"A", pairEngine);
		ITrueAndFalse tafGuess = sourceRowGuess.execute();
		boolean lastVal = tafGuess.getSource()[tafGuess.getSource().length-1];
		
		List<Result> rtnResult = new ArrayList<Result>();
		Result r6 = this.acceptSingle(taf, 6, expectPatterns[0], lastVal?'A':'B');rtnResult.add(r6);
		Result r7 = this.acceptSingle(taf, 7, expectPatterns[1], lastVal?'A':'B');rtnResult.add(r7);
		Result r8 = this.acceptSingle(taf, 8, expectPatterns[2], lastVal?'A':'B');rtnResult.add(r8);
		Result r9 = this.acceptSingle(taf, 9, expectPatterns[3], lastVal?'A':'B');rtnResult.add(r9);
		
		Result r6n = this.acceptSingle(taf, 6, expectPatterns[4], lastVal?'B':'A');rtnResult.add(r6n);
		Result r7n = this.acceptSingle(taf, 7, expectPatterns[5], lastVal?'B':'A');rtnResult.add(r7n);
		Result r8n = this.acceptSingle(taf, 8, expectPatterns[6], lastVal?'B':'A');rtnResult.add(r8n);
		Result r9n = this.acceptSingle(taf, 9, expectPatterns[7], lastVal?'B':'A');rtnResult.add(r9n);
		
		return rtnResult;
	}
	
	protected Result acceptSingle(ITrueAndFalse taf, int cycle, String expectPattern, char expectValue){
		
		int expect = 0;
		Result finalRes = null;
		IProcessor processor = Start.findProcessor(taf.getSource(), cycle, expectPattern.toCharArray());
		if(null != processor){
			expect = processor.execute();
			finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, expectValue);
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
			finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, 'X');
		}
		System.out.println(finalRes.getFormated());
		return finalRes;
	}
}
