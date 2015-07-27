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
	
	@RequestMapping("/obtainExpects")
	@ResponseBody
	public List<String> obtainExpects(@RequestParam("source")String source){
		
		final String positive="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
		final String negtive="----------------------------------------------------------------------";
		List<String> rtnExpect = new ArrayList<String>(){{add("");add("");add("");add("");add("");add("");add("");add("");}};
		try{
			SourceRow sRow = new SourceRow(source);
			sRow.print();
			IRow row = sRow.run();
			List<TrueAndFalse> rtn = row.run();
			rtn.get(0).print();
			
			List<Result> rtnResult = new ArrayList<Result>();
			Result r6 = this.acceptSingle(rtn.get(0), 6, positive, false, "POSITIVE");rtnResult.add(r6);
			Result r7 = this.acceptSingle(rtn.get(0), 7, positive, false, "POSITIVE");rtnResult.add(r7);
			Result r8 = this.acceptSingle(rtn.get(0), 8, positive, false, "POSITIVE");rtnResult.add(r8);
			Result r9 = this.acceptSingle(rtn.get(0), 9, positive, false, "POSITIVE");rtnResult.add(r9);
			
			Result r6n = this.acceptSingle(rtn.get(0), 6, negtive, false, "NEGTIVE");rtnResult.add(r6n);
			Result r7n = this.acceptSingle(rtn.get(0), 7, negtive, false, "NEGTIVE");rtnResult.add(r7n);
			Result r8n = this.acceptSingle(rtn.get(0), 8, negtive, false, "NEGTIVE");rtnResult.add(r8n);
			Result r9n = this.acceptSingle(rtn.get(0), 9, negtive, false, "NEGTIVE");rtnResult.add(r9n);

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
	
	@RequestMapping("/accept")
	@ResponseBody
    public List<Object> accept(@RequestParam("source")String source, @RequestParam("expects[]")String[] expectPatterns) {
		
		SourceRow sRow = new SourceRow(source);
		sRow.print();
		IRow row = sRow.run();
		List<TrueAndFalse> rtn = row.run();
		rtn.get(0).print();
		
		//***期待下一位***
		SourceRow sourceRowGuess = new SourceRow(source+"A");
		TrueAndFalse tafGuess = sourceRowGuess.run().run().get(0);
		boolean lastVal = tafGuess.getResult().get(tafGuess.getResult().size()-1);
		
		List<Object> rtnResult = new ArrayList<Object>();
		Result r6 = this.acceptSingle(rtn.get(0), 6, expectPatterns[0], lastVal, "POSITIVE");rtnResult.add(r6);
		Result r7 = this.acceptSingle(rtn.get(0), 7, expectPatterns[1], lastVal, "POSITIVE");rtnResult.add(r7);
		Result r8 = this.acceptSingle(rtn.get(0), 8, expectPatterns[2], lastVal, "POSITIVE");rtnResult.add(r8);
		Result r9 = this.acceptSingle(rtn.get(0), 9, expectPatterns[3], lastVal, "POSITIVE");rtnResult.add(r9);
		
		Result r6n = this.acceptSingle(rtn.get(0), 6, expectPatterns[4], lastVal, "NEGTIVE");rtnResult.add(r6n);
		Result r7n = this.acceptSingle(rtn.get(0), 7, expectPatterns[5], lastVal, "NEGTIVE");rtnResult.add(r7n);
		Result r8n = this.acceptSingle(rtn.get(0), 8, expectPatterns[6], lastVal, "NEGTIVE");rtnResult.add(r8n);
		Result r9n = this.acceptSingle(rtn.get(0), 9, expectPatterns[7], lastVal, "NEGTIVE");rtnResult.add(r9n);
		
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
	
	protected Result acceptSingle(TrueAndFalse taf, int cycle, String expectPattern, boolean lastVal, String type){

		boolean[] result = taf.getSource();
		int expect = 0;
		Result finalRes = null;
		ecommerce.algorithm4.processor.v2.IProcessor processor = 
				ecommerce.algorithm4.processor.v2.Start.findProcessor(result, cycle, expectPattern.toCharArray());
		if(null != processor){
			expect = processor.execute();
			if("POSITIVE".equals(type)){
				if(processor.getClassCycle().endsWith("Cycle4TripleO"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'B':'A', "3*O");//20:45:27
				else if(processor.getClassCycle().endsWith("Cycle4TripleX"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'A':'B', "3*X");//20:45:27
			} else {
				if(processor.getClassCycle().endsWith("Cycle4TripleO"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'A':'B', "3*O");
				else if(processor.getClassCycle().endsWith("Cycle4TripleX"))
					finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal?'B':'A', "3*X");
			}
			//finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, expectValue);
		}
		return finalRes;
	}
}
