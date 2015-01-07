package ecommerce.eAlgorithm9;

import ecommerce.base.ISourceRow;
import ecommerce.base.IVisitor;

public class FilterVisitor implements IVisitor {

	@Override
	public boolean filter(ISourceRow row) {
		
		String sub = row.getSource().substring(0,14);
		char[] data = sub.toCharArray();
		int countOfA=0,countOfB=0;
		for(int i=0; i<data.length; i++){
			if(data[i] == 'A')
				countOfA++;
			else
				countOfB++;
		}
		if(countOfA<=5 || countOfB<=5)
			return true;
			
		if(!sub.contains("AA") || !sub.contains("BB"))
			return true;
		
		return false;
	}

}
