package ecommerce.eAlgorithm12.element;

import java.util.ArrayList;
import java.util.List;

import ecommerce.eAlgorithm12.Compare;
import ecommerce.eAlgorithm12.IExpect;

public class Element5 implements IElement{
	
	static private IExpect pattern;
	static public void setExpect(IExpect expect){
		pattern = expect;
	}
	
	/***
	 * 期待值总是两个较多元素
	 * 例如：
	 * 	++-期待++
	 * 	--+期待-- */
	static public class PatternPositive implements IExpect {
		static private Compare[] expectDifference=new Compare[]{Compare.difference, Compare.difference};
		static private Compare[] expectSame=new Compare[]{Compare.same, Compare.same};

		@Override
		public Compare[] expects(Compare[] depends) {
			int countOfSame=0, countOfDifference=0;
			for(Compare c : depends)
				if(c==Compare.same)
					countOfSame++;
				else
					countOfDifference++;
			
			return countOfSame<countOfDifference?expectDifference:expectSame;
		}
	}
	
	/***
	 * 期待值总是两个较少元素
	 * 例如：
	 * 	++-期待--
	 * 	--+期待++ */
	static public class PatternNegtive implements IExpect {
		static private Compare[] expectDifference=new Compare[]{Compare.difference, Compare.difference};
		static private Compare[] expectSame=new Compare[]{Compare.same, Compare.same};
		
		@Override
		public Compare[] expects(Compare[] depends) {
			int countOfSame=0, countOfDifference=0;
			for(Compare c : depends)
				if(c==Compare.same)
					countOfSame++;
				else
					countOfDifference++;
			
			return countOfSame>countOfDifference?expectDifference:expectSame;
		}
	}
	
	private char[] source;
	private char[] result;
	public Element5(char[] source){
		this.source = source;
		this.result = new char[]{'_','_','_','_','_'};
	}
	@Override
	public char[] getSource(){
		return this.source;
	}
	@Override
	public String getValue(int columnIndex){
		if(columnIndex<5 && columnIndex<this.source.length)
			return String.format("%c%c", this.source[columnIndex], this.result[columnIndex]);
		else
			return "__";
	}
	
	@Override
	public String getHtml(int columnIndex) {
		if(this.result[3] == this.result[4] && this.result[3]=='x' && columnIndex>2){
			if(columnIndex<5 && columnIndex<this.source.length)
				return String.format("<font color='red'>%c%c</font>", this.source[columnIndex], this.result[columnIndex]);
			else
				return "__";
		} else {
			if(columnIndex<5 && columnIndex<this.source.length)
				return String.format("%c%c", this.source[columnIndex], this.result[columnIndex]);
			else
				return "__";
		}
	}
	
	@Override
	public List<Boolean> execute(IElement other, boolean result2this) {
		
		List<Boolean> rtn = new ArrayList<Boolean>();
		if(this.source.length<=3)
			return rtn;
		
		Compare[] depends = new Compare[3];
		depends[0] = this.source[0]==other.getSource()[0]?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getSource()[1]?Compare.same:Compare.difference;
		depends[2] = this.source[2]==other.getSource()[2]?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2&r3得到期待值
		
		boolean val = expects[0] == (this.source[3]==other.getSource()[3]?Compare.same:Compare.difference);
		rtn.add(val);
		if(result2this)
			this.result[3] = val?'o':'x';
		else{
			char[] result = other.getResult();
			result[3] = val?'o':'x';
			other.setResult(result);
		}
		
		if(!rtn.get(0) && this.source.length==5){//若刚才的结果为true,则跳过第二个运算
			val = expects[1] == (this.source[4]==other.getSource()[4]?Compare.same:Compare.difference);
			rtn.add(val);
			if(result2this)
				this.result[4]=val?'o':'x';
			else{
				char[] result = other.getResult();
				result[4] = val?'o':'x';
				other.setResult(result);
			}
		}
		
		return rtn;
	}

	@Override
	public boolean needSkip(int offSet) {
		return offSet%5>2;
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
