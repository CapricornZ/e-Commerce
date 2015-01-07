package ecommerce.eAlgorithm10;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ElementA {
	
	private static Logger logger = LoggerFactory.getLogger(ElementA.class);
	
	protected char[] data;
	protected IExpect expect12, expect5;
	public ElementA(char[] data, IExpect e12, IExpect e5){
		this.data = data;
		this.expect12 = e12;
		this.expect5 = e5;
	}

	public void print(){
		logger.debug("{}\r\n", new String(data));
	}
	
	public List<Boolean> execute(ElementA element){

		List<Boolean> rtn = new ArrayList<Boolean>();
		StringBuilder sb = new StringBuilder();
		if(this.data.length < 3)
			return rtn;
		
		sb.append(this.data[0]==element.data[0]?'+':'-');
		sb.append(this.data[1]==element.data[1]?'+':'-');
		
		int i=2;
		char[] expects12 = this.expect12.expects(sb.toString());
		for(char expect : expects12){
			rtn.add(expect==(this.data[i]==element.data[i]?'+':'-'));
			i++;
			if(i>=this.data.length)
				break;
		}
		
		if(this.data.length<6)
			return rtn;
		
		i=5;
		char[] expects5 = this.expect5.expects(this.data[4]==element.data[4]?"+":"-");
		for(char expect : expects5){
			rtn.add(expect==(this.data[i]==element.data[i]?'+':'-'));
			i++;
			if(i>=this.data.length)
				break;
		}
	
		return rtn;
	}
}
