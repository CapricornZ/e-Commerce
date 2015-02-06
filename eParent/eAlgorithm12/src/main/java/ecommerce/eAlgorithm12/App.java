package ecommerce.eAlgorithm12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ecommerce.FileAccess;
import ecommerce.base.Context;
import ecommerce.base.IResultRow;
import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.ITrueAndFalse;
import ecommerce.base.SourceRowConvert;
import ecommerce.base.stastic.ISequentialStastic;
import ecommerce.base.stastic.SequentialForSection;

public class App {
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			logger.error("APP should be followed by ${file PATH}, ${file TYPE}\r\n");
			return;
		}
		
		Properties properties=new Properties();
        properties.setProperty("resource.loader", "class");//设置velocity资源加载方式为class
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");//设置velocity资源加载方式为file时的处理类
        properties.setProperty(Velocity.ENCODING_DEFAULT, "GBK");
        properties.setProperty(Velocity.INPUT_ENCODING, "GBK");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "GBK");    
        VelocityEngine velocityEngine=new VelocityEngine(properties);

		ApplicationContext context = new ClassPathXmlApplicationContext( new String[] {"applicationContext.xml"});
		AppContext params = (AppContext)context.getBean("AppContext");
		String filePath = args[0];
		String[] ss = filePath.split("/");
		String fileName = ss[ss.length-1];
		String fileType = args[1];
		logger.info("----------------------------------------\r\n");
		logger.info("start scanning {} ...\r\n", filePath);
		logger.info("----------------------------------------\r\n");
		InputStreamReader read = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		//OutputStreamWriter htmlWriter = new OutputStreamWriter(new FileOutputStream(filePath+".html"), "GBK");
		OutputStreamWriter htmlWriter = new OutputStreamWriter(new FileOutputStream(params.getOutput()), "GBK");
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		List<IResultRow> totalResult = new ArrayList<IResultRow>();
		List<List<ITrueAndFalse>> totalTAF = new ArrayList<List<ITrueAndFalse>>();
		int maxCountOfTaf = 0;
		int number = 1;
		int countOfSkip = 0;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			
			String source = lineTxt.trim();
			if(source.length() == 0){
				logger.info("skip row : {}\r\n", source);
				continue;
			}
			
			Context viewContext = new Context();
			
			IRow sRow = null;
			if(fileType.equals("0"))
				sRow = new SourceRow(source);
			else
				sRow = SourceRowConvert.convert(source, SourceRow.class);
			
			logger.warn("{}.\r\n", number);
			viewContext.put("NO", number++);
			
			if(Skip.exam((ISourceRow)sRow)){
				countOfSkip++;
				sRow.print();
				viewContext.mergeContext(sRow.getContext());
				java.io.StringWriter sw = new java.io.StringWriter();
				velocityEngine.mergeTemplate("main.vm", "utf-8", viewContext.getContext(), sw);
				String s = sw.toString();
				htmlWriter.write(s);
				continue;
			}

			while(!IResultRow.class.isAssignableFrom(sRow.getClass())){
				sRow.print();
				viewContext.mergeContext(sRow.getContext());
				sRow = sRow.run();
			}
			
			IResultRow rowResult = (IResultRow)sRow;
			List<ITrueAndFalse> rtn = rowResult.getResult();
			sRow.print();
			viewContext.mergeContext(sRow.getContext());
			int countOfTaf = 0;
			for(ITrueAndFalse taf : rtn){
				countOfTaf ++ ;
				taf.run(0);
				taf.print();
				viewContext.mergeContext(taf.getContext());
			}
			if(countOfTaf > maxCountOfTaf)
				maxCountOfTaf = countOfTaf;
			
			totalResult.add(rowResult);
			totalTAF.add(rtn);
			
			java.io.StringWriter sw = new java.io.StringWriter();
			velocityEngine.mergeTemplate("main.vm", "utf-8", viewContext.getContext(), sw);
			String s = sw.toString();
			htmlWriter.write(s);
		}
		bufferedReader.close();
		//htmlWriter.close();
		
		StringBuilder sbStastic = new StringBuilder();
		sbStastic.append("\r\n--------------------------------------------------\r\n");
		//sbStastic.append("---------------------整个文件汇总-------------------\r\n");

		logger.info("\r\n--------------------------------------------------\r\n");
		logger.info("---------------------整个文件汇总-------------------\r\n");
		logger.info("----------------数据源中{}条记录xx跳过----------------\r\n", countOfSkip);
		
		//>>>>>>begin
		int countOfXX=0;
		Map<String, Integer> repoPositions = new HashMap<String, Integer>();
		for(IResultRow rowResult:totalResult){
			List<RelayPosition> positions = ((IGetPositions)rowResult).getRelayPositions();
			countOfXX+=positions.size();
			for(RelayPosition pos : positions){
				Integer count = repoPositions.get(pos.getIdentity());
				if(count != null)
					repoPositions.put(pos.getIdentity(), count+1);
				else
					repoPositions.put(pos.getIdentity(), +1);
			}
		}
		logger.info("出现xx的位置和数量\r\n[ ");sbStastic.append("\r\n[");
		for(Map.Entry<String, Integer> entry : repoPositions.entrySet()){
			logger.info("{}:{}, ", entry.getKey(), entry.getValue());
			sbStastic.append(entry.getKey()+":"+entry.getValue()+", ");
		}
		logger.info("]\r\n");sbStastic.append("]\r\n");
		//>>>>>>end
		
		for(int i=0; i<maxCountOfTaf; i++){//每一段的汇总
			int sum = 0, max = 0;
			int countTrue = 0, countFalse = 0;
			Map<Integer, Integer> mapMaxCount = new HashMap<Integer, Integer>();//MAX的出现个数
			for(List<ITrueAndFalse> list : totalTAF){
				if(list.size() > i){
					ITrueAndFalse taf = list.get(i);
					
					if(taf.isValid()){
						sum += taf.getSum();
						countTrue += taf.getCountTrue();
						countFalse += taf.getCountFalse();
						if(max < taf.getMax())
							max = taf.getMax();
						
						//统计MAX数的个数
						Integer countOfMax = mapMaxCount.get(taf.getMax());
						if(countOfMax != null)
							mapMaxCount.put(taf.getMax(), countOfMax+1);
						else
							mapMaxCount.put(taf.getMax(), +1);
					}
					
				}	
			}
			logger.info("第{}段 \r\n\t[ SUM:{}, MAX:{}, x:{}({}%), o:{}({}%) ]\r\n", i+1, sum, max, 
					countFalse, (float)countFalse*100/(float)(countFalse+countTrue),
					countTrue, (float)countTrue*100/(float)(countFalse+countTrue));
			String tmp = String.format("第%d段 \r\n\t[ SUM:%d, MAX:%d, x:%d(%f%%), o:%d(%f%%) ]\r\n", i+1, sum, max, 
					countFalse, (float)countFalse*100/(float)(countFalse+countTrue),
					countTrue, (float)countTrue*100/(float)(countFalse+countTrue));
			sbStastic.append(tmp);
			sbStastic.append("\t[ ");
			logger.info("\t[ ");
			for(Map.Entry<Integer, Integer> entry : mapMaxCount.entrySet()){
				logger.info("{}:{}, ", entry.getKey(), entry.getValue());
				sbStastic.append(entry.getKey() + ":" + entry.getValue() + ", ");
			}
			logger.info("]\r\n");
			sbStastic.append("]\r\n");
			
			//统计连续o/x的个数
			ISequentialStastic seqStastic = new SequentialForSection();
			seqStastic.run(totalTAF, i);
			for(int seq=1; seq<=seqStastic.getMaxCountOfSeq(); seq++){
				logger.info("\tSEQ {} {x:{}, o:{}}\r\n", seq,
						seqStastic.getCountOfSeqX().get(seq)==null?0:seqStastic.getCountOfSeqX().get(seq),
						seqStastic.getCountOfSeqO().get(seq)==null?0:seqStastic.getCountOfSeqO().get(seq));
				
				sbStastic.append(String.format("\tSEQ %d {x:%d, o:%d}\r\n", seq,
						seqStastic.getCountOfSeqX().get(seq)==null?0:seqStastic.getCountOfSeqX().get(seq),
						seqStastic.getCountOfSeqO().get(seq)==null?0:seqStastic.getCountOfSeqO().get(seq)));
			}
		}
		logger.info("--------------------------------------------------\r\n");
		
		Context viewContext = new Context();
		viewContext.put("STASTIC", sbStastic.toString());
		java.io.StringWriter sw = new java.io.StringWriter();
		velocityEngine.mergeTemplate("stastic.vm", "utf-8", viewContext.getContext(), sw);
		String s = sw.toString();
		htmlWriter.write(s);
		htmlWriter.close();
		
		FileAccess.Move("/home/martin/数据", "数据"+fileName);
		FileAccess.Move("/home/martin/结果", "结果"+fileName);
	}
}
