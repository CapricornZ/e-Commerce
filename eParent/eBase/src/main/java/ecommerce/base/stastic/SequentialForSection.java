package ecommerce.base.stastic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ecommerce.base.ITrueAndFalse;

public class SequentialForSection implements ISequentialStastic {
	
	private Map<Integer, Integer> countOfSeqX = new HashMap<Integer, Integer>(); 
	private Map<Integer, Integer> countOfSeqO = new HashMap<Integer, Integer>();
	private int maxCountOfSeq;
	
	public Map<Integer, Integer> getCountOfSeqX(){return countOfSeqX;}
	public Map<Integer, Integer> getCountOfSeqO(){return countOfSeqO;}
	public int getMaxCountOfSeq(){return this.maxCountOfSeq;}
	
	@Override
	public void run(List<List<ITrueAndFalse>> totalResult, int section){
		
		countOfSeqX.clear();
		countOfSeqO.clear();
		maxCountOfSeq = 0;
		
		//统计连续o/x的个数
		for(int row=0; row<totalResult.size(); row++){
			//System.out.println("--" + row + "--");
			List<ITrueAndFalse> Row = totalResult.get(row);

			if(Row.size() > section){

				List<Boolean> result = Row.get(section).getResult();
				if(result.size() <=0 )
					continue;
				
				Boolean last = result.get(0);
				int count = 1;
				for(int index=1; index<result.size(); index++){
					if(result.get(index) == last){
						count ++ ;
					} else {
						maxCountOfSeq = maxCountOfSeq<count?count:maxCountOfSeq;
						
						if(last){
							Integer seq = countOfSeqO.get(count);
							if(seq == null)
								countOfSeqO.put(count, 1);
							else
								countOfSeqO.put(count, seq+1);
						} else {
							Integer seq = countOfSeqX.get(count);
							if(seq == null)
								countOfSeqX.put(count, 1);
							else
								countOfSeqX.put(count, seq+1);
						}
						
						last = result.get(index);
						count = 1;
					}
				}//end of for
				
				if(last){
					Integer seq = countOfSeqO.get(count);
					if(seq == null)
						countOfSeqO.put(count, 1);
					else
						countOfSeqO.put(count, seq+1);
				} else {
					Integer seq = countOfSeqX.get(count);
					if(seq == null)
						countOfSeqX.put(count, 1);
					else
						countOfSeqX.put(count, seq+1);
				}
			}
		}
	}
}
