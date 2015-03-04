package ecommerce.eAlgorithm12.element;

import java.util.ArrayList;
import java.util.List;

import ecommerce.eAlgorithm12.Compare;
import ecommerce.eAlgorithm12.IExpect;

public class Element4SkipA extends Element4 {
	
	public Element4SkipA(char[] source, IExpect pattern){
		super(source, pattern);
	}
	
	@Override
	public List<Boolean> execute(IElement other, boolean result2this) {
		
		List<Boolean> rtn = new ArrayList<Boolean>();
		if(this.source.length<=2)
			return rtn;
		
		Compare[] depends = new Compare[2];
		depends[0] = this.source[0]==other.getSource()[0]?Compare.same:Compare.difference;
		depends[1] = this.source[1]==other.getSource()[1]?Compare.same:Compare.difference;
		
		Compare[] expects = pattern.expects(depends);//r1&r2得到期待值
		boolean val = false;
		if (this.source.length>2 && ((this.source[2] == 'A' && expects[0]==Compare.difference) 
				|| (this.source[2]=='B' && expects[0]==Compare.same))){
			
			val = expects[0] == (this.source[2]==other.getSource()[2]?Compare.same:Compare.difference);
			rtn.add(val);
			if(result2this)
				this.result[2] = val?'o':'x';
			else{
				char[] result = other.getResult();
				result[2] = val?'o':'x';
				other.setResult(result);
			}
		}
		
		if(!val && this.source.length == 4){//若刚才的结果为true,则跳过第二个运算
			if ((this.source[3] == 'A' && expects[1]==Compare.difference) 
					|| (this.source[3]=='B' && expects[1]==Compare.same)){
				
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
		}
		
		return rtn;
	}

}
