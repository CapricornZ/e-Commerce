package elements.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.algorithm4.IRow;
import ecommerce.algorithm4.Result;
import ecommerce.algorithm4.SourceRow;
import ecommerce.algorithm4.TrueAndFalse;

@Controller
@RequestMapping("/ajax/alg/4")
public class Alg4xController {

	@RequestMapping("/preAccept")
	@ResponseBody
	public List<String> format(@RequestParam("source")String source){
		
		List<String> rtnAccept = new ArrayList<String>();

		SourceRow sRow = new SourceRow(source);
		sRow.print();
		IRow row = sRow.run();
		List<TrueAndFalse> rtn = row.run();

		rtnAccept.add(sRow.getFormatSource());
		rtnAccept.add(rtn.get(0).getFormated());
		return rtnAccept;
	}
	
	@RequestMapping("/accept")
	@ResponseBody
    public List<Result> accept(@RequestParam("source")String source, @RequestParam("expects[]")String[] expectPatterns) {
		
		SourceRow sRow = new SourceRow(source);
		sRow.print();
		IRow row = sRow.run();
		List<TrueAndFalse> rtn = row.run();
		rtn.get(0).print();
		
		//***期待下一位***
		SourceRow sourceRowGuess = new SourceRow(source+"A");
		TrueAndFalse tafGuess = sourceRowGuess.run().run().get(0);
		boolean lastVal = tafGuess.getResult().get(tafGuess.getResult().size()-1);
		
		List<Result> rtnResult = new ArrayList<Result>();
		Result r6 = this.acceptSingle(rtn.get(0), 6, expectPatterns[0], lastVal?'A':'B');rtnResult.add(r6);
		Result r7 = this.acceptSingle(rtn.get(0), 7, expectPatterns[1], lastVal?'A':'B');rtnResult.add(r7);
		Result r8 = this.acceptSingle(rtn.get(0), 8, expectPatterns[2], lastVal?'A':'B');rtnResult.add(r8);
		Result r9 = this.acceptSingle(rtn.get(0), 9, expectPatterns[3], lastVal?'A':'B');rtnResult.add(r9);
		
		Result r6n = this.acceptSingle(rtn.get(0), 6, expectPatterns[4], lastVal?'B':'A');rtnResult.add(r6n);
		Result r7n = this.acceptSingle(rtn.get(0), 7, expectPatterns[5], lastVal?'B':'A');rtnResult.add(r7n);
		Result r8n = this.acceptSingle(rtn.get(0), 8, expectPatterns[6], lastVal?'B':'A');rtnResult.add(r8n);
		Result r9n = this.acceptSingle(rtn.get(0), 9, expectPatterns[7], lastVal?'B':'A');rtnResult.add(r9n);
		
		return rtnResult;
	}
	
	protected Result acceptSingle(TrueAndFalse taf, int cycle, String expectPattern, char expectValue){

		boolean[] result = taf.getSource();
		int expect = 0;
		Result finalRes = null;
		ecommerce.algorithm4.processor.v2.IProcessor processor = 
				ecommerce.algorithm4.processor.v2.Start.findProcessor(result, cycle, expectPattern.toCharArray());
		if(null != processor){
			expect = processor.execute();
			finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, expectValue);
		}
		return finalRes;
	}
}
