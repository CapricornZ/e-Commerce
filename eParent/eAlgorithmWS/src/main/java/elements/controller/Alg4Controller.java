package elements.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.algorithm4.SourceRow;
import ecommerce.algorithm4.Result;
import ecommerce.algorithm4.IRow;
import ecommerce.algorithm4.TrueAndFalse;
import ecommerce.algorithm4.processor.IProcessor;
import ecommerce.algorithm4.processor.Start;

@Controller
@RequestMapping("/ajax/alg4")
public class Alg4Controller {
	
	@RequestMapping("/accept")
	@ResponseBody
    public List<String> accept(@RequestParam("source")String source) {
		
		List<String> rtnAccept = new ArrayList<String>();
		
		String class3O = "ecommerce.algorithm4.processor.CycleNegtive";
		String class3X = "ecommerce.algorithm4.processor.CyclePositive";
		
		SourceRow sRow = new SourceRow(source);
		sRow.print();
		IRow row = sRow.run();
		List<TrueAndFalse> rtn = row.run();
		
		rtnAccept.add(source);
		rtnAccept.add(sRow.getFormatSource());
		
		//***期待下一位***
		SourceRow sourceRowGuess = new SourceRow(source+"A");
		TrueAndFalse tafGuess = sourceRowGuess.run().run().get(0);
		boolean lastVal = tafGuess.getResult().get(tafGuess.getResult().size()-1);
		rtnAccept.add(lastVal?"A":"B");

		for(TrueAndFalse taf : rtn){
			taf.print();
			taf.run();
			
			rtnAccept.add(taf.getFormated());
			
			boolean[] result = new boolean[taf.getResult().size()];
			for(int i=0; i<taf.getResult().size(); i++)
				result[i] = taf.getResult().get(i);
			
			List<Result> resultsPositive = new ArrayList<Result>();
			List<Result> resultsNegtive = new ArrayList<Result>();
			//*** 6 ***
			int expect = 0;
			Result finalRes = null;
			IProcessor processor = Start.findProcessor(result, 6, class3X, class3O);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsPositive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 7 ***
			finalRes = null;
			processor = Start.findProcessor(result, 7, class3X, class3O);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsPositive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 8 ***
			finalRes = null;
			processor = Start.findProcessor(result, 8, class3X, class3O);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsPositive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 9 ***
			finalRes = null;
			processor = Start.findProcessor(result, 9, class3X, class3O);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsPositive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 6 反 ***
			finalRes = null;
			processor = Start.findProcessor(result, 6, class3O, class3X);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsNegtive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 7 反 ***
			finalRes = null;
			processor = Start.findProcessor(result, 7, class3O, class3X);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsNegtive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 8 反 ***
			finalRes = null;
			processor = Start.findProcessor(result, 8, class3O, class3X);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsNegtive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 9 反 ***
			finalRes = null;
			processor = Start.findProcessor(result, 9, class3O, class3X);
			if(null != processor){
				expect = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect);
			}
			resultsNegtive.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			int sumOfExpect = 0;
			for(Result item : resultsPositive){//正
				if(null!=item && item.hasExpect())
					sumOfExpect += item.getExpect();
			}
			for(Result item : resultsNegtive){//反
				if(null!=item && item.hasExpect())
					sumOfExpect -= item.getExpect();
			}
			
			//if(processor instanceof ecommerce.algorithm4.processor.Processor3O)
			//	rtnAccept.set(2, "A".equals(rtnAccept.get(2)) ? "B" : "A");
			//rtnAccept.set(2, String.format("%s*%d", rtnAccept.get(2), sumOfExpect));
			
			if(processor instanceof ecommerce.algorithm4.processor.Processor3X){
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtnAccept.set(2, String.format("A*%d", Math.abs(sumOfExpect)));
				else
					rtnAccept.set(2, String.format("B*%d", Math.abs(sumOfExpect)));
			}
			else{
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtnAccept.set(2, String.format("B*%d", Math.abs(sumOfExpect)));
				else
					rtnAccept.set(2, String.format("A*%d", Math.abs(sumOfExpect)));
			}
		}
		
		return rtnAccept;
	}

}
