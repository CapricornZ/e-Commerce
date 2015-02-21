package elements;

import java.util.ArrayList;
import java.util.List;

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
	
	public Element4(IExpect expectMode, char[] source, int startOff){
		this.pattern = expectMode;
		this.source = source;
		this.startOff = startOff;
	}
	@Override public int getLength(){return this.source.length;}
	private IExpect pattern;
	private char[] source;
	private boolean skipFlag = false;
	private int startOff = 0;
	
	@Override
	public char nextItem(IElement other) {
		
		char rtn = 0;
		if(this.skipFlag || this.source.length<2)
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
				rtn = 0;
			else
				if(expects[1]==Compare.same)
					rtn = other.getPositiveItem(3);
				else
					rtn = other.getNegtiveItem(3);
		} else if(this.source.length == 4)
			rtn = 0;
		
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
	public List<eAlgorithmWS.Item> execute(IElement other){
		
		List<eAlgorithmWS.Item> rtn = new ArrayList<eAlgorithmWS.Item>();
		if(this.source.length<=2)
			return rtn;
		
		Compare[] depends = new Compare[2];
		depends[0] = this.source[0]==other.getSource()[0]?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getSource()[1]?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2得到期待值
		boolean val = expects[0] == (this.source[2]==other.getSource()[2]?Compare.same:Compare.difference);
		rtn.add(new eAlgorithmWS.Item(this.startOff+2, this.source[2], val));
		
		if(!val && this.source.length==4){//若刚才的结果为true,则跳过第二个运算
			val = expects[1] == (this.source[3]==other.getSource()[3]?Compare.same:Compare.difference);
			rtn.add(new eAlgorithmWS.Item(this.startOff+3, this.source[3], val));
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
	
	@Override
	public String toString() {
		return String.format("%s", this.source.toString());
	}
}
