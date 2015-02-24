package ecommerce.patterns.resultrow.stop;

import java.util.List;

/***
 * match whether last n Elements all are FALSE
 * @author martin
 *
 */
public class StopWhileNX implements IStop {

	private int n;
	public void setN(int value){this.n = value;}

	@Override
	public boolean match(List<List<Boolean>> result) {
		
		int count = 0;
		boolean bMatch = false;
		for(int i=result.size()-1; i>0 && count<6; i--){
			for(int j=result.get(i).size()-1; j>=0 && count<6; j--){	
				count++;
				bMatch = bMatch || result.get(i).get(j);
			}
		}

		return count==n && !bMatch;
	}
}
