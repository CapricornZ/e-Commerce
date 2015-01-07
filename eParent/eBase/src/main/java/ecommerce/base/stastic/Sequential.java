package ecommerce.base.stastic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ecommerce.base.ITrueAndFalse;

public class Sequential implements ISequentialStastic{
	
	private Map<Integer, Integer> countOfSeqX = new HashMap<Integer, Integer>(); 
	private Map<Integer, Integer> countOfSeqO = new HashMap<Integer, Integer>();
	private int maxCountOfSeq;
	
	public Map<Integer, Integer> getCountOfSeqX(){return countOfSeqX;}
	public Map<Integer, Integer> getCountOfSeqO(){return countOfSeqO;}
	public int getMaxCountOfSeq(){return this.maxCountOfSeq;}
	
	@Override
	public void run(List<List<ITrueAndFalse>> totalResult, int maxSection){
		
		for(int section=0; section<maxSection; section++){
			
			ISequentialStastic seqStastic = new SequentialForSection();
			seqStastic.run(totalResult, section);
			
			maxCountOfSeq = maxCountOfSeq<seqStastic.getMaxCountOfSeq()?seqStastic.getMaxCountOfSeq():maxCountOfSeq;
			
			for(Map.Entry<Integer, Integer> entry : seqStastic.getCountOfSeqO().entrySet()){
				Integer key = entry.getKey();
				if(this.countOfSeqO.containsKey(key))
					this.countOfSeqO.put(key, this.countOfSeqO.get(key) + entry.getValue());
				else
					this.countOfSeqO.put(key, entry.getValue());
			}
			
			for(Map.Entry<Integer, Integer> entry : seqStastic.getCountOfSeqX().entrySet()){
				
				Integer key = entry.getKey();
				if(this.countOfSeqX.containsKey(key))
					this.countOfSeqX.put(key, this.countOfSeqX.get(key) + entry.getValue());
				else
					this.countOfSeqX.put(key, entry.getValue());
				
			}
		}
	}

}
