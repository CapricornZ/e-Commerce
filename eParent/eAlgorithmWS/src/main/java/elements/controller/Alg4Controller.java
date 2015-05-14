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
			
			List<Result> results = new ArrayList<Result>();
			//*** 6 ***
			boolean bStop = false;
			Result finalRes = null;
			IProcessor processor = Start.findProcessor(result, 6, class3X, class3O);
			if(null != processor){
				bStop = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), bStop);
			}
			results.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 7 ***
			finalRes = null;
			processor = Start.findProcessor(result, 7, class3X, class3O);
			if(null != processor){
				bStop = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), bStop);
			}
			results.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 8 ***
			finalRes = null;
			processor = Start.findProcessor(result, 8, class3X, class3O);
			if(null != processor){
				bStop = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), bStop);
			}
			results.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			//*** 9 ***
			finalRes = null;
			processor = Start.findProcessor(result, 9, class3X, class3O);
			if(null != processor){
				bStop = processor.execute();
				finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), bStop);
			}
			results.add(finalRes);
			rtnAccept.add(null==finalRes? "" : finalRes.getFormated());
			
			int sumOfExpect = 0;
			for(Result item : results){
				if(null!=item && !item.getStop() && item.getSource().size()>0)
					sumOfExpect += Math.abs(item.getSource().get(item.getSource().size()-1));
			}
			
			if(processor instanceof ecommerce.algorithm4.processor.Processor3O)
				rtnAccept.set(2, "A".equals(rtnAccept.get(2)) ? "B" : "A");
			rtnAccept.set(2, String.format("%s*%d", rtnAccept.get(2), sumOfExpect));
		}
		
		return rtnAccept;
	}

}
