package ecommerce.eAlgorithm12;

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
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			logger.error("APP should be followed by ${file PATH}, ${file TYPE}\r\n");
			return;
		}

		ApplicationContext context = new ClassPathXmlApplicationContext( new String[] {"applicationContext.xml"});
		String filePath = args[0];
		String[] ss = filePath.split("/");
		String fileName = ss[ss.length-1];
		String fileType = args[1];
		logger.info("----------------------------------------\r\n");
		logger.info("start scanning {} ...\r\n", filePath);
		logger.info("----------------------------------------\r\n");
		InputStreamReader read = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		List<IResultRow> totalResult = new ArrayList<IResultRow>();
		List<List<ITrueAndFalse>> totalTAF = new ArrayList<List<ITrueAndFalse>>();
		int maxCountOfTaf = 0;
		int number = 1;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			
			String source = lineTxt.trim();
			if(source.length() == 0){
				logger.info("skip row : {}\r\n", source);
				continue;
			}
			
			IRow sRow = null;
			if(fileType.equals("0"))
				sRow = new SourceRow(source);
			else
				sRow = SourceRowConvert.convert(source, SourceRow.class);
			
			logger.warn("{}.\r\n", number++);

			while(!IResultRow.class.isAssignableFrom(sRow.getClass())){
				sRow.print();
				sRow = sRow.run();
			}
			
			IResultRow rowResult = (IResultRow)sRow;
			List<ITrueAndFalse> rtn = rowResult.getResult();
			sRow.print();
			int countOfTaf = 0;
			for(ITrueAndFalse taf : rtn){
				countOfTaf ++ ;
				taf.run(0);
				taf.print();
			}
			if(countOfTaf > maxCountOfTaf)
				maxCountOfTaf = countOfTaf;
			
			totalResult.add(rowResult);
			totalTAF.add(rtn);
		}
		bufferedReader.close();
		
		logger.info("--------------------------------------------------\r\n");
		logger.info("---------------------整个文件汇总-------------------\r\n");
		
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
		logger.info("出现xx的位置和数量\r\n[ ");
		for(Map.Entry<String, Integer> entry : repoPositions.entrySet())
			logger.info("{}:{}, ", entry.getKey(), entry.getValue());
		logger.info("]\r\n");
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
			logger.info("第{}段 \r\n\t[ SUM:{}, MAX:{}, x:{}({}%), o:{}({}%) ]\r\n", i+1, sum, max, 
					countFalse, (float)countFalse*100/(float)(countFalse+countTrue),
					countTrue, (float)countTrue*100/(float)(countFalse+countTrue));
			logger.info("\t[ ");
			for(Map.Entry<Integer, Integer> entry : mapMaxCount.entrySet())
				logger.info("{}:{}, ", entry.getKey(), entry.getValue());
			logger.info("]\r\n");
			
			//统计连续o/x的个数
			ISequentialStastic seqStastic = new SequentialForSection();
			seqStastic.run(totalTAF, i);
			for(int seq=1; seq<=seqStastic.getMaxCountOfSeq(); seq++){
				logger.info("\tSEQ {} {x:{}, o:{}}\r\n", seq,
						seqStastic.getCountOfSeqX().get(seq)==null?0:seqStastic.getCountOfSeqX().get(seq),
						seqStastic.getCountOfSeqO().get(seq)==null?0:seqStastic.getCountOfSeqO().get(seq));
			}
		}
		logger.info("--------------------------------------------------\r\n");
		
		FileAccess.Move("/home/martin/数据", "数据"+fileName);
		FileAccess.Move("/home/martin/结果", "结果"+fileName);
	}
}
