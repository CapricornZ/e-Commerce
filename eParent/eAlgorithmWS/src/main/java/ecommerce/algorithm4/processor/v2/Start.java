package ecommerce.algorithm4.processor.v2;

public class Start{

	static public IProcessor findProcessor(boolean[] source, int cycleStep, char[] expectPattern) {
		
		//char[] expectSame = new char[]{'+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+'};
		//char[] expectDifferent = new char[]{'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', };
		IProcessor processor = null;
		boolean bFound = false;
		for(int offset=2; !bFound && offset<source.length; offset++){
			
			if(offset>1 && source[offset] && source[offset-1] && source[offset-2])//3*o
				processor = new Processor3O(source, offset+1, cycleStep, expectPattern);
				//processor = new Processor(source, offset+1, cycleStep, expectDifferent);
			
			if(offset>1 && !source[offset] && !source[offset-1] && !source[offset-2])//3*x
				processor = new Processor3O(source, offset+1, cycleStep, expectPattern);
				//processor = new Processor(source, offset+1, cycleStep, expectSame);
			
			bFound = processor != null;
		}
		
		return processor;
	}

}
