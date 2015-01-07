package ecommerce.eAlgorithm9;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Element {
	
	private static Logger logger = LoggerFactory.getLogger(Element.class);
	
	private char[] data;
	private int countOfA, countOfB;
	public Element(char[] data){
		this.data = data;
		for(char item : data){
			if(item=='A')
				this.countOfA++;
			else
				this.countOfB++;
		}
	}
	
	public void print(){
		logger.debug("{}\r\n", new String(data));
	}
	
	public Pair pair(Element element){
		if(this.countOfA == element.countOfA && this.countOfB == element.countOfB)
			return Pair.POSITIVE;
		if(this.countOfA == element.countOfB && this.countOfB == element.countOfA)
			return Pair.NEGTIVE;
		return Pair.INVALID;
	}

	public List<Boolean> execute(Element element, Pair pair){

		List<Boolean> rtn = new ArrayList<Boolean>();
		int min = this.data.length<element.data.length?this.data.length:element.data.length;
		for(int i=0; i<min; i++){
			if(pair == Pair.POSITIVE)
				rtn.add(this.data[i]==element.data[i]);
			
			if(pair == Pair.NEGTIVE)
				rtn.add(this.data[i]!=element.data[i]);
		}
		return rtn;
	}
}
