package ecommerce.eAlgorithm10;

import ecommerce.base.ISourceRow;
import ecommerce.base.SourceRowConvert;

public class SourceRowBuilder {
	
	protected IExpect expectA12, expectA5, expectB345;
	
	public void setExpectA12(IExpect expectA12) {
		this.expectA12 = expectA12;
	}

	public void setExpectA5(IExpect expectA5) {
		this.expectA5 = expectA5;
	}

	public void setExpectB345(IExpect expectB345) {
		this.expectB345 = expectB345;
	}

	public ISourceRow build(String source, int rowFormat, String algorithm) throws Exception{
		
		ISourceRow sRow = null;
		if(algorithm.equals("A")){
			if (rowFormat==0)
				sRow = new SourceRowA(source);
			else
				sRow = (ISourceRow)SourceRowConvert.convert(source, SourceRowA.class);
			((SourceRowA)sRow).setPattern12(this.expectA12);
			((SourceRowA)sRow).setPattern5(this.expectA5);
			
		} else if(algorithm.equals("B")){
			if (rowFormat==0)
				sRow = new SourceRowB(source);
			else
				sRow = (ISourceRow)SourceRowConvert.convert(source, SourceRowB.class);
			((SourceRowB)sRow).setPattern12(this.expectA12);
			((SourceRowB)sRow).setPattern5(this.expectA5);
			((SourceRowB)sRow).setPatternB(this.expectB345);
		}
		
		return sRow;
	}
}
