package eAlgorithmWS;

import java.util.ArrayList;
import java.util.List;

import elements.patterns.IStop;

public class TrueAndFalseSeperate implements ITrueAndFalse {

	private ITrueAndFalse taf;
	private List<List<Item>> source;
	
	public TrueAndFalseSeperate(List<List<Item>> source, IStop stop){
		
		this.source = source;
		List<Item> continuity = new ArrayList<Item>();
		for(List<Item> pos:this.source)
			continuity.addAll(pos);
		this.taf = new TrueAndFalse(continuity, stop);
	}
	public TrueAndFalseSeperate(List<List<Item>> source){
		this.source = source;
		List<Item> continuity = new ArrayList<Item>();
		for(List<Item> pos:this.source)
			continuity.addAll(pos);
		taf = new TrueAndFalse(continuity);
	}
	
	@Override
	public void run(int offset) {
		this.taf.run(0+this.source.get(0).size());
	}

	@Override public int getSourcePos() {return this.taf.getSourcePos();}
	@Override public int getMetaPos() {return this.taf.getMetaPos();}
	@Override public List<Item> getSource() {
		List<Item> rtn = new ArrayList<Item>();
		boolean stop = false;
		int index = this.source.get(0).size();
		while(!stop && index<this.taf.getSource().size()){
			Item item = this.taf.getSource().get(index++);
			if(item.getCount()==0)
				stop = true;
			else
				rtn.add(item);
		}
		return rtn;
	}

}
