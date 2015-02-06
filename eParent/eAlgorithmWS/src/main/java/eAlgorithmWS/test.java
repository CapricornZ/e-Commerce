package eAlgorithmWS;

import java.util.ArrayList;
import java.util.List;

import elements.Element4;
import elements.IElement;
import elements.builder.Element4Builder;

public class test {

	public static void main(String[] args) {
		
		//String source = "BAAAAAAABAABBBABBBAABBBAABAABBAABABBAAAABAABAABBAABBBABBBBAAAAA";
		String source = "BAAAAAABAB";
		Element4Builder builder4 = new Element4Builder();
		builder4.setExpect(new Element4.PatternNegtive());

		boolean stop = false;
		int startOff = 0;
		List<IElement> elements = new ArrayList<IElement>();
		while(!stop){
			IElement element = builder4.createElement(source, startOff);
			elements.add(element);
			startOff += element.getLength();
			stop = startOff>=source.length();
		}
		char rtn = elements.get(elements.size()-1).nextItem(elements.get(elements.size()-2));
		
		//Element4 element1 = new Element4(new Element4.PatternNegtive(), new char[]{'A','B','A','B'});
		//Element4 element2 = new Element4(new Element4.PatternNegtive(), new char[]{'A','B','B'});
		//char rtn = element2.nextItem(element1);
		System.out.println(rtn);
	}
}
