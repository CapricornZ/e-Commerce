package eAlgorithmWS;

import java.util.List;

import elements.patterns.IStop;

public class TAFBuilder {
	private IStop stop;
	public void setStop(IStop stop){ this.stop = stop; }
	
	public ITrueAndFalse create(List<List<eAlgorithmWS.Item>> totalResult){
		return new TrueAndFalseSeperate(totalResult, this.stop);
	}
}
