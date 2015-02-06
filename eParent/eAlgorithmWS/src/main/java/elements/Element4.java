package elements;

public class Element4 implements IElement {
	
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
	
	public Element4(IExpect expectMode, char[] source){
		this.pattern = expectMode;
		this.source = source;
	}
	@Override public int getLength(){return this.source.length;}
	private IExpect pattern;
	private char[] source;
	
	@Override
	public char nextItem(IElement other) {
		
		char rtn = 0;
		if(this.source.length<2)
			return rtn;
		
		Compare[] depends = new Compare[2];
		depends[0] = this.source[0]==other.getPositiveItem(0)?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getPositiveItem(1)?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2得到期待值
		if(this.source.length==2){
			if(expects[0]==Compare.same)
				rtn = other.getPositiveItem(2);
			else
				rtn = other.getNegtiveItem(2);
		} else if(this.source.length==3){
			if((expects[0]==Compare.same && other.getPositiveItem(2)==this.source[2])
					||(expects[0]==Compare.difference && other.getNegtiveItem(2)==this.source[2]))
				rtn = 1;
			else
				if(expects[1]==Compare.same)
					rtn = other.getPositiveItem(3);
				else
					rtn = other.getNegtiveItem(3);
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
