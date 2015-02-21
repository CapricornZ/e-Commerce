package elements.controller;

import java.util.List;

import eAlgorithmWS.ISourceRow;
import eAlgorithmWS.Item;

public class Phase {
	
	public Phase(List<Item> result, ISourceRow singleRow){
		this.result = result;
		this.sourceRow = singleRow;
	}

	private ISourceRow sourceRow;
	public ISourceRow getSourceRow(){
		return this.sourceRow;
	}
	
	private List<Item> result;
	public List<Item> getResult() {
		return result;
	}
}
