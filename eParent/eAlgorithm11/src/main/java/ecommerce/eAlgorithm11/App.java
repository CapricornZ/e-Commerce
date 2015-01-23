package ecommerce.eAlgorithm11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ecommerce.FileAccess;
import ecommerce.base.IResultRow;
import ecommerce.base.IRow;
import ecommerce.base.ITrueAndFalse;
import ecommerce.base.SourceRowConvert;
import ecommerce.base.stastic.ISequentialStastic;
import ecommerce.base.stastic.SequentialForSection;

public class App {
	
	static private final Logger loggerSource = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			loggerSource.error("APP should be followed by ${file PATH}, ${file TYPE}\r\n");
			return;
		}

		ApplicationContext context = new ClassPathXmlApplicationContext( new String[] {"applicationContext.xml"});
		String filePath = args[0];
		String[] ss = filePath.split("/");
		String fileName = ss[ss.length-1];
		String fileType = args[1];
		loggerSource.info("----------------------------------------\r\n");
		loggerSource.info("start scanning {} ...\r\n", filePath);
		loggerSource.info("----------------------------------------\r\n");

		InputStreamReader read = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		List<IResultRow> totalResult = new ArrayList<IResultRow>();
		List<List<ITrueAndFalse>> totalTAF = new ArrayList<List<ITrueAndFalse>>();
		int maxCountOfTaf = 0;
		int number = 1;
		int countOfSkip = 0;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			
			String source = lineTxt.trim();
			if(source.length() == 0)
				continue;
			
			IRow sRow = null;
			if(fileType.equals("0"))
				sRow = new SourceRow(source);
			else
				sRow = SourceRowConvert.convert(source, SourceRow.class);
			
			//loggerSource.info("<Row>\r\n");
			//loggerSource.info("<No>\r\n");
			loggerSource.warn("{}.\r\n", number++);
			//loggerSource.info("</No>\r\n");
			
			while(sRow.getClass() != ResultRow.class){
				sRow.print();
				sRow = sRow.run();
			}
			
			ResultRow rowResult = (ResultRow)sRow;
			List<ITrueAndFalse> rtn = rowResult.getResult();
			sRow.print();
			
			int countOfTaf = 0;
			for(ITrueAndFalse taf : rtn){
				countOfTaf ++ ;
				if(Skip.exam(taf)){
					countOfSkip++;
					continue;
				}
				taf.run(0);
				taf.print();
			}
			if(countOfTaf > maxCountOfTaf)
				maxCountOfTaf = countOfTaf;
			
			totalResult.add(rowResult);
			totalTAF.add(rtn);
			
			//loggerSource.info("</Row>\r\n");
		}
		bufferedReader.close();
		//loggerSource.info("</ecommerce>\r\n");
		
		loggerSource.info("\r\n--------------------------------------------------\r\n");
		loggerSource.info("---------------------整个文件汇总-------------------\r\n");
		loggerSource.info("----------------数据源中{}条记录xx跳过----------------\r\n", countOfSkip);
		
		//>>>>>>begin
		int countOfXX=0;
		Map<String, Integer> repoPositions = new HashMap<String, Integer>();
		for(IResultRow rowResult:totalResult){
			List<RelayPosition> positions = ((ResultRow)rowResult).getRelayPositions();
			countOfXX+=positions.size();
			for(RelayPosition pos : positions){
				Integer count = repoPositions.get(pos.getIdentity());
				if(count != null)
					repoPositions.put(pos.getIdentity(), count+1);
				else
					repoPositions.put(pos.getIdentity(), +1);
			}
		}
		loggerSource.info("出现xx的位置和数量\r\n[ ");
		for(Map.Entry<String, Integer> entry : repoPositions.entrySet())
			loggerSource.info("{}:{}, ", entry.getKey(), entry.getValue());
		loggerSource.info("]\r\n");
		//>>>>>>end
		
		for(int i=0; i<maxCountOfTaf; i++){//每一段的汇总
			int sum = 0, max = 0;
			int countTrue = 0, countFalse = 0;
			Map<Integer, Integer> mapMaxCount = new HashMap<Integer, Integer>();//MAX的出现个数
			for(List<ITrueAndFalse> list : totalTAF){
				if(list.size() > i){
					ITrueAndFalse taf = list.get(i);
					
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
			loggerSource.info("第{}段 \r\n\t[ SUM:{}, MAX:{}, x:{}({}%), o:{}({}%) ]\r\n", i+1, sum, max, 
					countFalse, (float)countFalse*100/(float)(countFalse+countTrue),
					countTrue, (float)countTrue*100/(float)(countFalse+countTrue));
			loggerSource.info("\t[ ");
			for(Map.Entry<Integer, Integer> entry : mapMaxCount.entrySet())
				loggerSource.info("{}:{}, ", entry.getKey(), entry.getValue());
			loggerSource.info("]\r\n");
			
			//统计连续o/x的个数
			ISequentialStastic seqStastic = new SequentialForSection();
			seqStastic.run(totalTAF, i);
			for(int seq=1; seq<=seqStastic.getMaxCountOfSeq(); seq++){
				loggerSource.info("\tSEQ {} {x:{}, o:{}}\r\n", seq,
						seqStastic.getCountOfSeqX().get(seq)==null?0:seqStastic.getCountOfSeqX().get(seq),
						seqStastic.getCountOfSeqO().get(seq)==null?0:seqStastic.getCountOfSeqO().get(seq));
			}
		}
		loggerSource.info("--------------------------------------------------\r\n");
		
		FileAccess.Move("/home/martin/数据", "数据"+fileName);
		FileAccess.Move("/home/martin/结果", "结果"+fileName);
	}
}
