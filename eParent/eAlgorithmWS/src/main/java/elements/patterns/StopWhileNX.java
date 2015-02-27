package elements.patterns;

import java.util.List;

import eAlgorithmWS.ITrueAndFalse;

public class StopWhileNX implements IStop {

	private int n;
	public void setN(int value){this.n = value;}
	
	public StopWhileNX(){
	}
	public StopWhileNX(int n){
		this.n = n;
	}
	
	@Override
	public boolean match(ITrueAndFalse taf) {
		List<eAlgorithmWS.Item> result = taf.getSource();
		int pos = taf.getSourcePos() - 1;
		
		boolean bMatch = false;
		if(pos<this.n-1)
			return bMatch;
		
		for(int i=0;i<this.n && !bMatch;i++)
			bMatch = bMatch || result.get(pos-i).getValue();
		
		return !bMatch;
	}
}
