package ecommerce.patterns.trueandfalse.gonext;

import java.util.ArrayList;
import java.util.List;

import ecommerce.base.ITrueAndFalse;

/***
 * 一旦出现Taf运算过程中,出现于target相同的数字,则从metaPosition继续运算
 * @author martin
 *
 */
public class GoNextByProcess implements IGoNext {
	
	private List<Integer> target;
	public void setTarget(String target){
		String[] vals = target.split(",");
		this.target = new ArrayList<Integer>();
		for(String val:vals){
			if(val.startsWith("+"))
				val = val.substring(1, val.length());
			this.target.add(Integer.parseInt(val));
		}		
	}
	
	private int metaPosition;
	public void setMetaPosition(int metaPosition){
		this.metaPosition = metaPosition;
	}

	@Override
	public int GetNext(ITrueAndFalse taf) {
		List<Integer> source = taf.getProcess();
		int position = taf.getResultPos();
		if(source.size()<this.target.size())
			return taf.getMetaPos();
		boolean bMatch = true;
		for(int i=0; bMatch && i<this.target.size(); i++)
			bMatch = this.target.get(this.target.size()-i-1) == source.get(source.size()-i-1);
		if(bMatch)
			return this.metaPosition;
		return taf.getMetaPos();
	}

}
