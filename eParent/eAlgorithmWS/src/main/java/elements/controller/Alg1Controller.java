package elements.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.algorithm1.SourceRow;
import ecommerce.algorithm1.ITrueAndFalse;
import ecommerce.algorithm1.domain.Record;
import ecommerce.algorithm1.pairs.IPair;
import ecommerce.algorithm1.pairs.PairNegtive;
import ecommerce.algorithm1.pairs.PairPositive;
import ecommerce.algorithm1.service.RecordService;


@Controller
@RequestMapping("/ajax/alg1")
public class Alg1Controller {

	static String rule = "A,A;B,B|AA,BB;AB,BA|AAA,ABA;AAA,BBB;AAA,BAB;ABA,BBB;ABA,BAB;BBB,BAB;AAB,BAA;AAB,ABB;AAB,BBA;BAA,ABB;BAA,BBA;ABB,BBA|AAAA,ABAB;AAAA,ABBA;AAAA,AABB;AAAA,BBBB;AAAA,BABA;AAAA,BAAB;AAAA,BBAA;ABAB,ABBA;ABAB,AABB;ABAB,BBBB;ABAB,BABA;ABAB,BAAB;ABAB,BBAA;ABBA,AABB;ABBA,BBBB;ABBA,BABA;ABBA,BAAB;ABBA,BBAA;AABB,BBBB;AABB,BABA;AABB,BAAB;AABB,BBAA;BBBB,BABA;BBBB,BAAB;BBBB,BBAA;BABA,BAAB;BABA,BBAA;BAAB,BBAA;AAAB,AABA;AAAB,ABAA;AAAB,BAAA;AAAB,BBBA;AAAB,BBAB;AAAB,BABB;AAAB,ABBB;AABA,ABAA;AABA,BAAA;AABA,BBBA;AABA,BBAB;AABA,BABB;AABA,ABBB;ABAA,BAAA;ABAA,BBBA;ABAA,BBAB;ABAA,BABB;ABAA,ABBB;BAAA,BBBA;BAAA,BBAB;BAAA,BABB;BAAA,ABBB;BBBA,BBAB;BBBA,BABB;BBBA,ABBB;BBAB,BABB;BBAB,ABBB;BABB,ABBB";
	
	private RecordService service;
	public void setRecordService(RecordService service){
		this.service = service;
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public List<Record> searchRecord(@RequestParam("createDate")String createDate){
		
		/*java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String s = format1.format(new Date());System.out.println(s);*/
		return this.service.queryByCreateTime(createDate);		
	}
	
	@RequestMapping("/obtain")
	@ResponseBody
	public Record obtainRecord(@RequestParam("id")int id){
		
		return this.service.queryByID(id);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String updateRecord(@RequestParam("id")int id, @RequestParam("source")String source){
		
		Record record = new Record();
    	record.setId(id);
    	record.setSource(source);
		this.service.update(record);
		return "SUCCESS";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteRecord(@RequestParam("id")int id){
		
		Record record = new Record();
    	record.setId(id);
		this.service.delete(record);
		return "SUCCESS";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String saveRecord(String source, String trueANDfalse, String result){
		
		Record record = new Record();
		record.setCreateTime(new Date());
		record.setResult(result);
		record.setSource(source);
		record.setTrueANDfalse(trueANDfalse);
		
		this.service.save(record);
		
		return "SUCCESS";
	}
	
	@RequestMapping("/format")
	@ResponseBody
	public String format(@RequestParam("source")String source){
		
		IPair pairEngine = new PairPositive();
		SourceRow sourceRow = new SourceRow(source, pairEngine);
		return sourceRow.getFormatSource();
	}
	
	@RequestMapping("/accept")
	@ResponseBody
    public List<String> accept(@RequestParam("source")String source, @RequestParam("pair")String pairType) {
		
		List<String> rtn = new ArrayList<String>();
		IPair pairEngine = new PairPositive();
		if("NEGTIVE".equals(pairType))
			pairEngine = new PairNegtive();
		SourceRow sourceRow = new SourceRow(source, pairEngine);
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
		SourceRow sourceRowGuess = new SourceRow(source+"A", pairEngine);
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

		/*if(taf.getType() != null){
			if(taf.getType().equals("POSITIVE"))
				rtn.add(String.format("%s*%d", lastVal?"A":"B", sumOfExpect));
			else
				rtn.add(String.format("%s*%d", lastVal?"B":"A", sumOfExpect));
		} else {
			rtn.add("");
		}*/
		if(taf.getType() != null){
			if(taf.getType().equals("POSITIVE")){
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtn.add(String.format("A*%d", Math.abs(sumOfExpect)));
				else
					rtn.add(String.format("B*%d", Math.abs(sumOfExpect)));
			}
			else{
				//rtn.add(String.format("%s*%d", lastVal?"B":"A", sumOfExpect));
				if((lastVal && sumOfExpect > 0) || (!lastVal && sumOfExpect<0))
					rtn.add(String.format("B*%d", Math.abs(sumOfExpect)));
				else
					rtn.add(String.format("A*%d", Math.abs(sumOfExpect)));
			}
				
		} else {
			rtn.add("");
		}
		rtn.add(taf.getFormated());
		
		//rtn.add(null == results.get(0) ? "" : results.get(0).getFormated());
		//rtn.add(null == results.get(1) ? "" : results.get(1).getFormated());
		//rtn.add(null == results.get(2) ? "" : results.get(2).getFormated());
		//rtn.add(null == results.get(3) ? "" : results.get(3).getFormated());
		for(ecommerce.algorithm1.Result result : resultsPositive)
			rtn.add(result.getFormated());
		for(ecommerce.algorithm1.Result result : resultsNegtive)
			rtn.add(result.getFormated());
		
		return rtn;
	}
}
