package ecommerce.eAlgorithm12.element;

import java.util.ArrayList;
import java.util.List;

import ecommerce.eAlgorithm12.Compare;
import ecommerce.eAlgorithm12.IExpect;

public class Element7 implements IElement{
	
	/***
	 * 返回两个多数，如果“相同”vs“不同”的数量1:1，则返回null
	 * @author martin
	 */
	static public class PatternPositive implements IExpect{

		static private Compare[] expectDifference=new Compare[]{Compare.difference, Compare.difference};
		static private Compare[] expectSame=new Compare[]{Compare.same, Compare.same};
		
		@Override
		public Compare[] expects(Compare[] depends) {
			int countDiff=0, countSame=0;
			for(int i=0; i<depends.length; i++){
				if(depends[i] == Compare.same)
					countSame ++ ;
				else
					countDiff ++ ;
			}
			if(countDiff>countSame)
				return expectDifference;
			else if(countSame>countDiff)
				return expectSame;
			else
				return null;
		}
	}
	
	/***
	 * 返回两个少数，如果“相同”vs“不同”的数量1:1，则返回null
	 * @author martin
	 */
	static public class PatternNegtive implements IExpect{

		static private Compare[] expectDifference=new Compare[]{Compare.difference, Compare.difference};
		static private Compare[] expectSame=new Compare[]{Compare.same, Compare.same};
		
		@Override
		public Compare[] expects(Compare[] depends) {
			int countDiff=0, countSame=0;
			for(int i=0; i<depends.length; i++){
				if(depends[i] == Compare.same)
					countSame ++ ;
				else
					countDiff ++ ;
			}
			if(countDiff>countSame)
				return expectSame;
			else if(countSame>countDiff)
				return expectDifference;
			else
				return null;
		}
	}
	
	protected char[] source;
	protected char[] result;
	protected IExpect pattern;
	public Element7(char[] source, IExpect pattern){
		this.source = source;
		this.pattern = pattern;
		this.result = new char[]{'_','_','_','_','_','_','_'};
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
		if(this.result[5]==this.result[6] && this.result[5]=='x' && columnIndex>1){
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
		
		if(this.source.length<=5)
			return rtn;
		
		Compare[] depends = new Compare[5];
		depends[0] = this.source[0]==other.getSource()[0]?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getSource()[1]?Compare.same:Compare.difference;
		depends[2] = this.source[2]==other.getSource()[2]?Compare.same:Compare.difference;
		depends[3] = this.source[3]==other.getSource()[3]?Compare.same:Compare.difference;
		depends[4] = this.source[4]==other.getSource()[3]?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2得到期待值
		
		boolean val = expects[0] == (this.source[5]==other.getSource()[5]?Compare.same:Compare.difference);
		rtn.add(val);
		if(result2this)
			this.result[5] = val?'o':'x';
		else{
			char[] result = other.getResult();
			result[5] = val?'o':'x';
			other.setResult(result);
		}	
		
		if(!rtn.get(0) && this.source.length==7){//若刚才的结果为true,则跳过第二个运算
			val = expects[1] == (this.source[6]==other.getSource()[6]?Compare.same:Compare.difference);
			rtn.add(val);
			if(result2this)
				this.result[6] = val?'o':'x';
			else{
				char[] result = other.getResult();
				result[6] = val?'o':'x';
				other.setResult(result);
			}
		}
		return rtn;
	}
	
	@Override
	public boolean needSkip(int offSet) {
		//TODO:忘记了，skip的规则
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
