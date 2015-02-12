package elements;

import java.util.ArrayList;
import java.util.List;

public class Element5 implements IElement {
	
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

	public Element5(IExpect expectMode, char[] source){
		this.pattern = expectMode;
		this.source = source;
	}
	@Override public int getLength(){return this.source.length;}
	private IExpect pattern;
	private char[] source;
	private boolean skipFlag = false;
	
	@Override
	public char nextItem(IElement other) {
		
		char rtn = 0;
		if(this.skipFlag || this.source.length<3)
			return rtn;
		
		Compare[] depends = new Compare[3];
		depends[0] = this.source[0]==other.getPositiveItem(0)?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getPositiveItem(1)?Compare.same:Compare.difference;
		depends[2] = this.source[2]==other.getPositiveItem(2)?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2&r3得到期待值
		if(this.source.length==3){
			if(expects[0]==Compare.same)
				rtn = other.getPositiveItem(3);
			else
				rtn = other.getNegtiveItem(3);
		} else if(this.source.length==4){
			if((expects[0]==Compare.same && other.getPositiveItem(3)==this.source[3])
					||(expects[0]==Compare.difference && other.getNegtiveItem(3)==this.source[3]))
				rtn = 0;
			else
				if(expects[1]==Compare.same)
					rtn = other.getPositiveItem(4);
				else
					rtn = other.getNegtiveItem(4);
		}
		
		return rtn;
	}

	@Override
	public char getPositiveItem(int index) {
		return this.source[index];
	}

	@Override
	public char getNegtiveItem(int index) {
		if(this.source[index] == 'A')
			return 'B';
		else
			return 'A';
	}
	
	@Override
	public char[] getSource(){
		return this.source;
	}
	
	@Override
	public List<Boolean> execute(IElement other) {
		
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
		
		if(!rtn.get(0) && this.source.length==5){//若刚才的结果为true,则跳过第二个运算
			val = expects[1] == (this.source[4]==other.getSource()[4]?Compare.same:Compare.difference);
			rtn.add(val);
		}
		
		return rtn;
	}
	@Override
	public void setSkipFlag() {
		this.skipFlag = true;
	}
	@Override
	public void append(char value) {
		
		char[] source = this.source;
		this.source = new char[source.length+1];
		this.source[source.length] = value;
		System.arraycopy(source, 0, this.source, 0, source.length);
		
	}
}
