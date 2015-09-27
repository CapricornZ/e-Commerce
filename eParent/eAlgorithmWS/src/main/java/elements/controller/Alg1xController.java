package elements.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ecommerce.algorithm1.ITrueAndFalse;
import ecommerce.algorithm1.pairs.IPair;
import ecommerce.algorithm1.pairs.PairNegtive;
import ecommerce.algorithm1.pairs.PairPositive;
import ecommerce.algorithm4.Result;
import ecommerce.algorithm4.processor.v2.IProcessor;
import ecommerce.algorithm4.processor.v2.Start;
import ecommerce.algorithm4.processor.v2.StartImmidiate;

@Controller
@RequestMapping("/ajax/alg/1")
public class Alg1xController {

	@RequestMapping("/{type}/preAccept")
	@ResponseBody
	/***
	 * @param type POSITIVE(算法1)|NEGTIVE(算法1`)
	 * @param source
	 * @return
	 */
	public String format(@PathVariable("type")String type, @RequestParam("source")String source){
		
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(type))
			pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(source, pairEngine);
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
			ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(source, pairEngine);
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
	
	@RequestMapping("/{type}/oxANDxo/accept")
	@ResponseBody
	public List<Object> accept1(@PathVariable("type")String type, @RequestParam("source")String source, @RequestParam("expects[]")String[] expectPatterns) {
		
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(type))
			pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();
		
		//***期待下一位***
		ecommerce.algorithm1.SourceRow sourceRowGuess = new ecommerce.algorithm1.SourceRow(source+"A", pairEngine);
		ITrueAndFalse tafGuess = sourceRowGuess.execute();
		boolean lastVal = tafGuess.getSource()[tafGuess.getSource().length-1];
		
		List<Object> rtnResult = new ArrayList<Object>();
		Result ox6 = this.acceptSingleNow(taf, 6, expectPatterns[0], lastVal, "oxox...");rtnResult.add((ox6));
		Result ox7 = this.acceptSingleNow(taf, 7, expectPatterns[1], lastVal, "oxox...");rtnResult.add((ox7));
		Result ox8 = this.acceptSingleNow(taf, 8, expectPatterns[2], lastVal, "oxox...");rtnResult.add((ox8));
		Result ox9 = this.acceptSingleNow(taf, 9, expectPatterns[3], lastVal, "oxox...");rtnResult.add((ox9));
		
		Result xo6 = this.acceptSingleNow(taf, 6, expectPatterns[4], lastVal, "xoxo...");rtnResult.add((xo6));
		Result xo7 = this.acceptSingleNow(taf, 7, expectPatterns[5], lastVal, "xoxo...");rtnResult.add((xo7));
		Result xo8 = this.acceptSingleNow(taf, 8, expectPatterns[6], lastVal, "xoxo...");rtnResult.add((xo8));
		Result xo9 = this.acceptSingleNow(taf, 9, expectPatterns[7], lastVal, "xoxo...");rtnResult.add((xo9));
		
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
		
		/*if(sumOfExpect != 0){
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
		}*/
		rtnResult.add("N/A*0");
		return rtnResult;
	}
	
	@RequestMapping("/{type}/accept")
	@ResponseBody
    public List<Object> accept(@PathVariable("type")String type, @RequestParam("source")String source, @RequestParam("expects[]")String[] expectPatterns) {
		
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(type))
			pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();
		
		//***期待下一位***
		ecommerce.algorithm1.SourceRow sourceRowGuess = new ecommerce.algorithm1.SourceRow(source+"A", pairEngine);
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
	
	protected Result acceptSingleNow(ITrueAndFalse taf, int cycle, String expectPattern, boolean lastVal, String type){
		
		Result finalRes = null;
		IProcessor processor = StartImmidiate.findProcessor(taf.getSource(), cycle, expectPattern.toCharArray(), type);
		int expect = processor.execute();
		finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, lastVal == processor.getExpect() ? 'A':'B', type);
		
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
		ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(source, pairEngine);
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

	@RequestMapping(method=RequestMethod.POST, value="/accept", consumes = "application/json")
	@ResponseBody
    public List<Result> accept(@RequestParam("source")String source, @RequestParam(value="expect[]")String[] expectPattern) {

		List<Result> rtn = new ArrayList<Result>();
		IPair pairEngine = new PairPositive();
		ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(source, pairEngine);
		ITrueAndFalse taf = sourceRow.execute();

		int expect = 0;
		Result finalRes = null;
		/*** 算法1.周期6 ***/
		IProcessor processor6 = Start.findProcessor(taf.getSource(), 6, expectPattern[0].toCharArray());
		if(null != processor6){
			expect = processor6.execute();
			finalRes = new Result(processor6.getProcedure(), processor6.getMaxStep(), expect, 'X', "");
		}
		System.out.println(finalRes.getFormated());
		rtn.add(finalRes);
		
		/*** 算法1.周期7 ***/
		IProcessor processor7 = Start.findProcessor(taf.getSource(), 7, expectPattern[1].toCharArray());
		if(null != processor7){
			expect = processor7.execute();
			finalRes = new Result(processor7.getProcedure(), processor7.getMaxStep(), expect, 'X', "");
		}
		System.out.println(finalRes.getFormated());
		rtn.add(finalRes);
		
		/*** 算法1.周期8 ***/
		IProcessor processor8 = Start.findProcessor(taf.getSource(), 8, expectPattern[2].toCharArray());
		if(null != processor8){
			expect = processor8.execute();
			finalRes = new Result(processor8.getProcedure(), processor8.getMaxStep(), expect, 'X', "");
		}
		System.out.println(finalRes.getFormated());
		rtn.add(finalRes);
		
		/*** 算法1.周期9 ***/
		IProcessor processor9 = Start.findProcessor(taf.getSource(), 9, expectPattern[3].toCharArray());
		if(null != processor9){
			expect = processor9.execute();
			finalRes = new Result(processor9.getProcedure(), processor9.getMaxStep(), expect, 'X', "");
		}
		System.out.println(finalRes.getFormated());
		rtn.add(finalRes);
		
		return rtn;
	}
	
	@RequestMapping(value="/{algType}/acceptFile")
	public void acceptFile(
		@RequestParam("dataFile") MultipartFile file,
		@PathVariable("algType")String algType,/*POSITIVE（算法1）|NEGTIVE（算法1`）*/
		@RequestParam("cycle")int Cycle/*周期*/,
		@RequestParam("type")String type,/*POSITIVE|NEGTIVE*/
		HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		
		response.setContentType("text/html;charset=utf-8");  
		response.setContentType("application/x-msdownload;");
		String filename = String.format("ALG1%s(%d-%s).txt", algType.equals("NEGTIVE")?"`":"", Cycle, type);
        response.setHeader("Content-disposition", "attachment; filename="+filename);
        FileOutput.init(response.getOutputStream());
        
		final String patternPositive = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
		final String patternNegtive = "----------------------------------------------------------------------------------------------------";
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(algType))
			pairEngine = new PairNegtive();
		InputStreamReader read = new InputStreamReader(file.getInputStream(), "UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String strSource;
		List<Integer> maxSteps = new ArrayList<Integer>();
		List<Integer> countOfCycles = new ArrayList<Integer>();
		while ((strSource = bufferedReader.readLine()) != null) {
			
			FileOutput.writeline(strSource);
			ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(strSource, pairEngine);
			ITrueAndFalse taf = sourceRow.execute();
			FileOutput.writeline(taf.getFormated());

			Result r = this.acceptSingle(taf, Cycle, "POSITIVE".equals(type)?patternPositive:patternNegtive, false, "POSITIVE");
			if( null != r){
				FileOutput.writeline(r.getFormated() + String.format(" COUNT:%d", r.getCountOfCycle(Cycle)));
				maxSteps.add(r.getMaxCycleStep());
				countOfCycles.add(r.getCountOfCycle(Cycle));
			}
			else
				FileOutput.writeline("SKIP : XXX or OOO not found.");
		}
		
		int sumOfMax = maxSteps.size();
		HashMap<Integer, Integer> maxStepMap = new HashMap<Integer, Integer>();
		for(int maxStep : maxSteps){
			if(null == maxStepMap.get(maxStep))
				maxStepMap.put(maxStep, 1);
			else
				maxStepMap.put(maxStep, maxStepMap.get(maxStep)+1);
		}
		for(Map.Entry entry : maxStepMap.entrySet()){
			FileOutput.write(String.format("MAX %d : %d [%f%%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfMax));
		}
		
		int sumOfCycle = countOfCycles.size();
		HashMap<Integer, Integer> cycleMap = new HashMap<Integer, Integer>();
		for(int countOfCycle : countOfCycles){
			if(null == cycleMap.get(countOfCycle))
				cycleMap.put(countOfCycle, 1);
			else
				cycleMap.put(countOfCycle, cycleMap.get(countOfCycle)+1);
		}
		for(Map.Entry entry : cycleMap.entrySet()){
			FileOutput.write(String.format("COUNT %d : %d [%f%%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfCycle));
		}
	}
			
	@RequestMapping(value="/{algType}/oxANDxo/acceptFile")
	public void acceptFile1(
			@RequestParam("dataFile") MultipartFile file, 
			@PathVariable("algType")String algType,/*POSITIVE（算法1）|NEGTIVE（算法1`）*/
			@RequestParam("cycle")int Cycle/*周期*/,
			@RequestParam("type")String type,/*oxox...|xoxo...|oooo...|xxxx...*/
			HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		
		response.setContentType("text/html;charset=utf-8");  
		response.setContentType("application/x-msdownload;");
		String filename = String.format("ALG1%s(%d-%s).txt", algType.equals("NEGTIVE")?"`":"", Cycle, type);
        response.setHeader("Content-disposition", "attachment; filename="+filename);
        FileOutput.init(response.getOutputStream());
        
		final String pattern = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(algType))
			pairEngine = new PairNegtive();
		InputStreamReader read = new InputStreamReader(file.getInputStream(), "UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String strSource;
		List<Integer> maxSteps = new ArrayList<Integer>();
		List<Integer> countOfCycles = new ArrayList<Integer>();
		while ((strSource = bufferedReader.readLine()) != null) {
			
			FileOutput.writeline(strSource);
			ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(strSource, pairEngine);
			
			ITrueAndFalse taf = sourceRow.execute();
			FileOutput.writeline(taf.getFormated());
			
			Result r = this.acceptSingleNow(taf, Cycle, pattern, false, type);
			FileOutput.writeline(r.getFormated() + String.format(" COUNT:%d", r.getCountOfCycle(Cycle)));
			
			maxSteps.add(r.getMaxCycleStep());
			countOfCycles.add(r.getCountOfCycle(Cycle));
		}
		
		int sumOfMax = maxSteps.size();
		HashMap<Integer, Integer> maxStepMap = new HashMap<Integer, Integer>();
		for(int maxStep : maxSteps){
			if(null == maxStepMap.get(maxStep))
				maxStepMap.put(maxStep, 1);
			else
				maxStepMap.put(maxStep, maxStepMap.get(maxStep)+1);
		}
		for(Map.Entry entry : maxStepMap.entrySet()){
			//logger.debug("MAX {} : {} [{}%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfMax);
			FileOutput.write(String.format("MAX %d : %d [%f%%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfMax));
		}
		
		int sumOfCycle = countOfCycles.size();
		HashMap<Integer, Integer> cycleMap = new HashMap<Integer, Integer>();
		for(int countOfCycle : countOfCycles){
			if(null == cycleMap.get(countOfCycle))
				cycleMap.put(countOfCycle, 1);
			else
				cycleMap.put(countOfCycle, cycleMap.get(countOfCycle)+1);
		}
		for(Map.Entry entry : cycleMap.entrySet()){
			//logger.debug("COUNT {} : {} [{}%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfCycle);
			FileOutput.write(String.format("COUNT %d : %d [%f%%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfCycle));
		}
		
		//response.setHeader("Content-Length", String.valueOf(fileLength));
	}
}
