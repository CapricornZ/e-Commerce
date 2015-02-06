package elements;

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
	
	@Override
	public char nextItem(IElement other) {
		
		char rtn = 0;
		if(this.source.length<3)
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
				rtn = 1;
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
}
