package ecommerce.eAlgorithm12;

import java.util.List;

public class Swap {
	
	private String type;
	public void setType(String type){
		this.type = type;
	}
	public Swap createSwap(){
		if(this.type.equals("XX-Once"))
			return new SwapMatchXXOnce();
		else
			return new SwapMatchXX();
	}
	
	/***
	 * match xx
	 * @param result
	 * @return
	 */
	public class SwapMatchXX extends Swap{
		@Override
		public boolean exam(List<Boolean> result){
			return result.size()==2 && !result.get(0) && !result.get(1);
		}
	}
	
	/***
	 * match xx once
	 * @param result
	 * @return
	 */
	public class SwapMatchXXOnce extends Swap{
		private int count = 0;
		@Override
		public boolean exam(List<Boolean> result){
			boolean rtn = result.size()==2 && !result.get(0) && !result.get(1) && count==0;
			if(rtn)
				count++;
			return rtn;
		}
	}
	
	public boolean exam(List<Boolean> result){
		return false;
	}
}
