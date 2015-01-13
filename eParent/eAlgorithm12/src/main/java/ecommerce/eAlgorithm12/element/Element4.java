package ecommerce.eAlgorithm12.element;

import java.util.ArrayList;
import java.util.List;

import ecommerce.eAlgorithm12.Compare;
import ecommerce.eAlgorithm12.IExpect;

public class Element4 implements IElement{

	static private IExpect pattern;
	static public void setExpect(IExpect expect){
		pattern = expect;
	}
	
	/***
	 * +：相同
	 * -：不同
	 * ++：--
	 * --：++
	 * +-：++
	 * -+：-- */
	static public class PatternNegtive implements IExpect {
		static private Compare[] expectDifference=new Compare[]{Compare.difference, Compare.difference};
		static private Compare[] expectSame=new Compare[]{Compare.same, Compare.same};
		
		public Compare[] expects(Compare[] depends){
			if(depends[0]==depends[1]){
				if(depends[0]==Compare.same)
					return expectDifference;
				else
					return expectSame;
			} else {
				if(depends[0]==Compare.difference)
					return expectDifference;
				else
					return expectSame;
			}
		}
	}
	
	/***
	 * +：相同
	 * -：不同
	 * ++：++
	 * --：--
	 * +-：--
	 * -+：++ */
	static public class PatternPositive implements IExpect {
		static private Compare[] expectDifference=new Compare[]{Compare.difference, Compare.difference};
		static private Compare[] expectSame=new Compare[]{Compare.same, Compare.same};
		
		public Compare[] expects(Compare[] depends){
			if(depends[0]==depends[1]){
				if(depends[0]==Compare.same)
					return expectSame;
				else
					return expectDifference;
			} else {
				if(depends[0]==Compare.difference)
					return expectSame;
				else
					return expectDifference;
			}
		}
	}
	
	static public IElement createElement(String source, int startOff){
		
		int length = startOff+4<source.length()?4:source.length()-startOff;
		char[] data = source.substring(startOff, startOff+length).toCharArray();
		return new Element4(data);
	}
	
	private char[] source;
	private char[] result;
	public Element4(char[] source){
		this.source = source;
		this.result = new char[]{'_','_','_','_'};
	}
	@Override
	public char[] getSource(){
		return this.source;
	}
	@Override
	public String getValue(int columnIndex){
		if(columnIndex<4 && columnIndex<this.source.length)
			return String.format("%c%c", this.source[columnIndex], this.result[columnIndex]);
		else
			return "__";
	}
	
	@Override
	public List<Boolean> execute(IElement other){
		List<Boolean> rtn = new ArrayList<Boolean>();
		
		if(this.source.length<=2)
			return rtn;
		
		Compare[] depends = new Compare[2];
		depends[0] = this.source[0]==other.getSource()[0]?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getSource()[1]?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2得到期待值
		
		boolean val = expects[0] == (this.source[2]==other.getSource()[2]?Compare.same:Compare.difference);
		rtn.add(val);this.result[2] = val?'o':'x';
		
		if(!rtn.get(0) && this.source.length==4){//若刚才的结果为true,则跳过第二个运算
			val = expects[1] == (this.source[3]==other.getSource()[3]?Compare.same:Compare.difference);
			rtn.add(val);this.result[3] = val?'o':'x';
		}
		return rtn;
	}
	
	@Override
	public boolean needSkip(int offSet) {
		return offSet%4>1;
	}
}
