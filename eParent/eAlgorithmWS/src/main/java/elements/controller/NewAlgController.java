package elements.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.algorithm1.ITrueAndFalse;
import ecommerce.algorithm1.SourceRow;
import ecommerce.algorithm1.SourceRow3;
import ecommerce.algorithm1.pairs.IPair;
import ecommerce.algorithm1.pairs.PairNegtive;
import ecommerce.algorithm1.pairs.PairPositive;

@Controller
@RequestMapping("/ajax/newAlg")
public class NewAlgController {
	
	@RequestMapping("/accept")
	@ResponseBody
    public List<String> accept(@RequestParam("source")String source, @RequestParam("pair")String pairType) {
		
		List<String> rtn = new ArrayList<String>();
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(pairType))
			pairEngine = new PairNegtive();
		SourceRow3 sourceRow = new SourceRow3(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();
		List<ecommerce.algorithm1.Result> resultsPositive = new ArrayList<ecommerce.algorithm1.Result>();
		resultsPositive.add(taf.execute(6, "POSITIVE"));
		resultsPositive.add(taf.execute(7, "POSITIVE"));
		resultsPositive.add(taf.execute(8, "POSITIVE"));
		resultsPositive.add(taf.execute(9, "POSITIVE"));
		
		List<ecommerce.algorithm1.Result> resultsNegtive = new ArrayList<ecommerce.algorithm1.Result>();
		resultsNegtive.add(taf.execute(6, "NEGTIVE"));
		resultsNegtive.add(taf.execute(7, "NEGTIVE"));
		resultsNegtive.add(taf.execute(8, "NEGTIVE"));
		resultsNegtive.add(taf.execute(9, "NEGTIVE"));

		//***期待下一位***
		SourceRow3 sourceRowGuess = new SourceRow3(source+"A", pairEngine);
		ITrueAndFalse tafGuess = sourceRowGuess.execute();
		boolean lastVal = tafGuess.getSource()[tafGuess.getSource().length-1];
		int sumOfExpect = 0;
		for(ecommerce.algorithm1.Result result : resultsPositive){//正的期待
			if(result != null && result.hasExpect())
				sumOfExpect += result.getExpect();
		}
		
		for(ecommerce.algorithm1.Result result : resultsNegtive){//反的期待
			if(result != null && result.hasExpect())
				sumOfExpect -= result.getExpect();
		}
		
		rtn.add(source);
		if("POSITIVE".equals(pairType))
			rtn.add(sourceRow.getFormatSource());
		else
			rtn.add("");

		if(taf.getType() != null){
			if(taf.getType().equals("POSITIVE")){
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtn.add(String.format("A*%d", Math.abs(sumOfExpect)));
				else
					rtn.add(String.format("B*%d", Math.abs(sumOfExpect)));
			}
			else{
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtn.add(String.format("B*%d", Math.abs(sumOfExpect)));
				else
					rtn.add(String.format("A*%d", Math.abs(sumOfExpect)));
			}
				
		} else {
			rtn.add("");
		}
		rtn.add(taf.getFormated());

		for(ecommerce.algorithm1.Result result : resultsPositive)
			rtn.add(result.getFormated());
		for(ecommerce.algorithm1.Result result : resultsNegtive)
			rtn.add(result.getFormated());
		
		return rtn;
	}

}
