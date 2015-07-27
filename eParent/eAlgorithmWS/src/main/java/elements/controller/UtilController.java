package elements.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ecommerce.algorithm1.domain.Record;
import ecommerce.algorithm1.service.RecordService;

@Controller
@RequestMapping("/ajax/util")
public class UtilController {

	private RecordService service;
	public void setRecordService(RecordService service){
		this.service = service;
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public List<Record> searchRecord(@RequestParam("createDate")String createDate){
		
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
}
