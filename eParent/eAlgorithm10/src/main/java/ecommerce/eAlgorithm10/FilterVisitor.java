package ecommerce.eAlgorithm10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecommerce.base.ISourceRow;
import ecommerce.base.IVisitor;

class FilterVisitor implements IVisitor{
	
	private static Logger logger = LoggerFactory.getLogger(FilterVisitor.class);
	
	@Override
	public boolean filter(ISourceRow row) {

		char[] data = row.getSource().substring(0,7).toCharArray();
		char last = data[0];
		int count = 1;
		StringBuilder sb = new StringBuilder();
		for(int i=1; i<data.length; i++){
			if(data[i]==last)
				count++;
			else{
				sb.append(count);
				last = data[i];
				count=1;
			}
			
		}
		sb.append(count);
		logger.debug("{}\r\n", sb.toString());
		return this.match(sb.toString());
	}
	
	static String[] PATTERNS = new String[]{"7","331","313","133"};
	private boolean match(String ratio){
		boolean bFound = false;
		for(int i=0; !bFound && i<PATTERNS.length; i++)
			bFound = ratio.equals(PATTERNS[i]);
		return bFound;
	}
}