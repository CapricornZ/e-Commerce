package elements.algorithmX;

import java.util.List;

public class Swap {
	
	static public Swap createSwap(String type){
		if(type.equals("XX-Once"))
			return new SwapMatchXXOnce();
		else
			return new SwapMatchXX();
	}

	/***
	 * match xx
	 * @param result
	 * @return
	 */
	static public class SwapMatchXX extends Swap{
		
		public SwapMatchXX(){}
		@Override
		public boolean exam(List<eAlgorithmWS.Item> result){
			return result.size()==2 && !result.get(0).getValue() && !result.get(1).getValue();
		}
	}
	
	/***
	 * match xx once
	 * @param result
	 * @return
	 */
	static public class SwapMatchXXOnce extends Swap{
		
		public SwapMatchXXOnce(){}
		private int count = 0;
		@Override
		public boolean exam(List<eAlgorithmWS.Item> result){
			boolean rtn = result.size()==2 && !result.get(0).getValue() && !result.get(1).getValue() && count==0;
			if(rtn)
				count++;
			return rtn;
		}
	}
	
	public boolean exam(List<eAlgorithmWS.Item> result){
		return false;
	}
}
