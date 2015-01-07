package ecommerce.eAlgorithm10;

import java.util.ArrayList;
import java.util.List;

public class ElementB extends ElementA{

	protected IExpect expect;
	public ElementB(char[] data, IExpect e12, IExpect e5, IExpect e) {
		super(data, e12, e5);
		this.expect = e;
	}
	
	@Override
	public List<Boolean> execute(ElementA element) {
		
		List<Boolean> rtn = new ArrayList<Boolean>();
		if(this.data.length < 6)
			return rtn;
		
		List<Boolean> result = super.execute(element);
		StringBuilder sb = new StringBuilder();
		sb.append(result.get(0)?"o":"x");//rtn.add(result.get(0));
		sb.append(result.get(1)?"o":"x");//rtn.add(result.get(1));
		sb.append(this.data[4]==element.data[4]?"+":"-");
		char[] expects = this.expect.expects(sb.toString());
		int i=5;
		for(char expect : expects){
			rtn.add(expect==(this.data[i]==element.data[i]?'+':'-'));
			i++;
			if(i>=this.data.length)
				break;
		}
		return rtn;
	}
}
