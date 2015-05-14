package ecommerce.algorithm4;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.algorithm4.perterns.trueandfalse.Next;

public class TrueAndFalse {
	
	private static final Logger logger = LoggerFactory.getLogger(TrueAndFalse.class);
	private static int[] metaData = new int[] { 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368 };
	
	private List<Boolean> result;
	public TrueAndFalse(List<Boolean> result){
		this.result = result;
	}

	private int sum;
	private int countTrue, countFalse;
	public int getSum(){return sum;}
	public Map<Integer, Integer> getMax(){return this.max;}
	public int getCountTrue(){return countTrue;}
	public int getCountFalse(){return countFalse;}
	public List<Boolean> getResult(){return this.result;}
	private Map<Integer, Integer> max = new TreeMap<Integer, Integer>();
	private Map<Integer, Boolean> bFoundMax = new TreeMap<Integer, Boolean>();
	
	public void run(){
		
		this.max.put(10, 0);this.max.put(15, 0);this.max.put(20, 0);
		this.bFoundMax.put(10, false);this.bFoundMax.put(15, false);this.bFoundMax.put(20, false);
		this.sum = 0;
		int indexSourceStep3 = 0;
		for (int indexSource = 0; indexSource < result.size(); indexSource++) {
			
			int current = metaData[indexSourceStep3];
			if(max.get(10)<current && !bFoundMax.get(10))
				max.put(10, current);
			if(max.get(15)<current && !bFoundMax.get(15))
				max.put(15, current);
			if(max.get(20)<current && !bFoundMax.get(20))
				max.put(20, current);
				
			if (result.get(indexSource)) {
				sum += metaData[indexSourceStep3];
				logger.info("+{}", metaData[indexSourceStep3]);
				if (indexSourceStep3 != 0)
					indexSourceStep3 -= 1;
			} else {
				sum -= metaData[indexSourceStep3];
				logger.info("-{}", metaData[indexSourceStep3]);
				indexSourceStep3 += 1;
			}
			
			if(!bFoundMax.get(10))
				bFoundMax.put(10, sum >= 10);
			if(!bFoundMax.get(15))
				bFoundMax.put(15, sum >= 15);
			if(!bFoundMax.get(20))
				bFoundMax.put(20, sum >= 20);
			
			if(Next.go2First(result, indexSource+1, current))
				indexSourceStep3 = 0;
		}
		logger.info(" = {} [ MAX(10):{}, MAX(15):{}, MAX(20):{} ]\r\n", sum, max.get(10), max.get(15), max.get(20));
	}
	
	public String getFormated(){
		
		int countFalse = 0, countTrue = 0;
		StringBuilder sb = new StringBuilder();
		//for(int i=0; i<=this.header; i++)
		//	sb.append("-");
		
		for(boolean val : this.result){
			if(val){
				sb.append("o");
				countTrue ++ ;
			} else {
				sb.append("x");
				countFalse ++ ;
			}
		}
		sb.append(String.format(" [x:%d (%f%%), o:%d (%f%%)]", 
				countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), countTrue, ((float)countTrue*100/(float)(countFalse+countTrue))));
		return sb.toString();
	}

	public void print(){
		
		StringBuilder sBuild = new StringBuilder();
		this.countTrue = 0;
		this.countFalse = 0;
		for(Boolean o:this.result){
			if(o){
				countTrue ++;
				sBuild.append("o");
			} else {
				countFalse ++;
				sBuild.append("x");
			}
		}
		
		logger.info("{}", sBuild.toString());
		//FileOutput.write(sBuild.toString());
		logger.info(" [ x:{} ({}%), o:{} ({}%) ]\r\n", 
				countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), 
				countTrue, ((float)countTrue*100/(float)(countFalse+countTrue)));
		//FileOutput.write(String.format(" [ x:%d (%f%%), o:%d (%f%%) ]\r\n", 
		//		countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), 
		//		countTrue, ((float)countTrue*100/(float)(countFalse+countTrue))));
	}
}
