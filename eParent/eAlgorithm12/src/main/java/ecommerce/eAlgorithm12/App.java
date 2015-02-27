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
import ecommerce.base.IResultRowX;
import ecommerce.base.IRow;
import ecommerce.base.ISourceRow;
import ecommerce.base.ITrueAndFalse;
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
		List<IResultRowX> totalResult = new ArrayList<IResultRowX>();
		List<List<ITrueAndFalse>> totalTAF = new ArrayList<List<ITrueAndFalse>>();
		int maxCountOfTaf = 1;
		int number = 1;
		int countOfSkip = 0;
		//连续x的个数(6x,7x ... 12x),每个下标代表正4，反4，正5，反5
		int[] countOfNx = new int[params.getTypeOfPatterns().length];
		while ((lineTxt = bufferedReader.readLine()) != null) {
			
			String source = lineTxt.trim();
			if(source.length() == 0){
				logger.info("skip row : {}\r\n", source);
				continue;
			}
			
			Context viewContext = new Context();
			
			int subNumber = 0;
			boolean valid = true;
			List<ISourceRow> tmpSourceRow = new ArrayList<ISourceRow>();
			List<IResultRowX> tmpResult = new ArrayList<IResultRowX>();
			List<List<ITrueAndFalse>> tmpTAF = new ArrayList<List<ITrueAndFalse>>();
			IRow sourceRow = null;
			
			for(int i=0; valid && i<params.getTypeOfPatterns().length; i++){

				IRow sRow = SourceRowBuilder.create(source, 0, params.getTypeOfPatterns()[i]);
				logger.warn("-----{}.{}-----\r\n", number, subNumber);
				viewContext.put("NO", String.format("-----%d-----", number, subNumber++));
				
				while(!IResultRowX.class.isAssignableFrom(sRow.getClass())){
					//if(subNumber == 1){
					//	sRow.print();
					//	sourceRow = sRow;
						//viewContext.mergeContext(sRow.getContext());
					//}
					sRow = sRow.run();
				}
				
				IResultRowX rowResult = (IResultRowX)sRow;
				List<ITrueAndFalse> rtn = rowResult.getResult();
				ISourceRow validSourceRow = rowResult.getSource();
				validSourceRow.print();
				
				rtn.get(0).run(0);
				rtn.get(0).print();
				if(rtn.get(0).getSum() == params.getSum())
					countOfNx[i] += 1;

				if(rowResult.isStopValid()){
					tmpSourceRow.add(validSourceRow);
					tmpResult.add(rowResult);
					tmpTAF.add(rtn);					
				} else
					valid = false;
			}
			if(valid){
				totalResult.addAll(tmpResult);
				totalTAF.addAll(tmpTAF);
				ResultRowSkip.CompositeView resultRows = new ResultRowSkip.CompositeView();
				for(IResultRowX resultRow : tmpResult)
					resultRows.append(resultRow);
				viewContext.mergeContext(resultRows.getContext());
				
				TrueAndFalseEx.CompositeView tafRows = new TrueAndFalseEx.CompositeView();
				for(List<ITrueAndFalse> tafs : tmpTAF)
					tafRows.append(tafs.get(0));
				viewContext.mergeContext(tafRows.getContext());
				
				java.io.StringWriter sw = new java.io.StringWriter();
				velocityEngine.mergeTemplate("main.vm", "utf-8", viewContext.getContext(), sw);
				String s = sw.toString();
				htmlWriter.write(s);
			}
			number++;
		}
		bufferedReader.close();
		//htmlWriter.close();
		
		StringBuilder sbStastic = new StringBuilder();
		sbStastic.append("\r\n--------------------------------------------------\r\n");
		//sbStastic.append("---------------------整个文件汇总-------------------\r\n");

		logger.info("\r\n--------------------------------------------------\r\n");
		logger.info("---------------------整个文件汇总-------------------\r\n");
		logger.info("----------------连续{}X分布情况----------------\r\n", params.getCountOfX());
		sbStastic.append(String.format("连续 %dX 分布情况\r\n", params.getCountOfX()));
		for(int i=0; i<params.getTypeOfPatterns().length; i++){
			logger.info("{}:{}\r\n", params.getTypeOfPatterns()[i], countOfNx[i]);
			sbStastic.append(String.format("%s : %d\r\n", params.getTypeOfPatterns()[i], countOfNx[i]));
		}
		
		//>>>>>>begin
		int countOfXX=0;
		Map<String, Integer> repoPositions = new HashMap<String, Integer>();
		for(IResultRowX rowResult:totalResult){
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
