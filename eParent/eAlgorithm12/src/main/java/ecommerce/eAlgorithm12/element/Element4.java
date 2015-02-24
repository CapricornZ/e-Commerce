package ecommerce.eAlgorithm12.element;

import java.util.ArrayList;
import java.util.List;

import ecommerce.eAlgorithm12.Compare;
import ecommerce.eAlgorithm12.IExpect;

public class Element4 implements IElement{
	
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
	
	private char[] source;
	private char[] result;
	private IExpect pattern;
	public Element4(char[] source, IExpect pattern){
		this.source = source;
		this.pattern = pattern;
		this.result = new char[]{'_','_','_','_'};
	}
	@Override
	public char[] getSource(){
		return this.source;
	}
	@Override
	public String getValue(int columnIndex){
		if(columnIndex<this.source.length)
			return String.format("%c%c", this.source[columnIndex], this.result[columnIndex]);
		else
			return "__";
	}
	
	@Override
	public String getHtml(int columnIndex) {
		if(this.result[2]==this.result[3] && this.result[2]=='x' && columnIndex>1){
			if(columnIndex<this.source.length)
				return String.format("<font color='red'>%c%c</font>", this.source[columnIndex], this.result[columnIndex]);
			else
				return "__";
		}
		else{
			if(columnIndex<this.source.length)
				return String.format("%c%c", this.source[columnIndex], this.result[columnIndex]);
			else
				return "__";
		}
	}
	
	@Override
	public List<Boolean> execute(IElement other, boolean result2this){
		List<Boolean> rtn = new ArrayList<Boolean>();
		
		if(this.source.length<=2)
			return rtn;
		
		Compare[] depends = new Compare[2];
		depends[0] = this.source[0]==other.getSource()[0]?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getSource()[1]?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2得到期待值
		
		boolean val = expects[0] == (this.source[2]==other.getSource()[2]?Compare.same:Compare.difference);
		rtn.add(val);
		if(result2this)
			this.result[2] = val?'o':'x';
		else{
			char[] result = other.getResult();
			result[2] = val?'o':'x';
			other.setResult(result);
		}	
		
		if(!rtn.get(0) && this.source.length==4){//若刚才的结果为true,则跳过第二个运算
			val = expects[1] == (this.source[3]==other.getSource()[3]?Compare.same:Compare.difference);
			rtn.add(val);
			if(result2this)
				this.result[3] = val?'o':'x';
			else{
				char[] result = other.getResult();
				result[3] = val?'o':'x';
				other.setResult(result);
			}
		}
		return rtn;
	}
	
	@Override
	public boolean needSkip(int offSet) {
		return offSet%4>1;
	}
	@Override
	public void setResult(char[] result) {
		this.result = result;
	}
	@Override
	public char[] getResult() {
		return this.result;
	}
	@Override
	public void append(char val) {
		char[] result = this.result;
		this.result = new char[result.length+1];
		this.result[result.length] = '_';
		System.arraycopy(result, 0, this.result, 0, result.length);
		
		char[] source = this.source;
		this.source = new char[source.length+1];
		this.source[source.length] = val;
		System.arraycopy(source, 0, this.source, 0, source.length);
	}
}
