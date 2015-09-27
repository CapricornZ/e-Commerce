package ecommerce.algorithm4.processor.v2;

/***
 * 不用找到3X或3O，立即启动
 * @author martin
 *
 */
public class StartImmidiate {

	static public IProcessor findProcessor(boolean[] source, int cycleStep, char[] expectPattern, String cycleType) {
		
		boolean[] expect = new boolean[source.length+1];
		if("oxox...".equals(cycleType)){
			
			for(int i=0; i<(source.length+1)/2; i++){
				expect[i*2] = true;
				expect[i*2+1] = false;
			}
			if(source.length % 2 == 0)
				expect[source.length] = true;
			
		} else if ("xoxo...".equals(cycleType)){
			
			for(int i=0; i<(source.length+1)/2; i++){
				expect[i*2] = false;
				expect[i*2+1] = true;
			}
			if(source.length % 2 == 0)
				expect[source.length] = false;
			
		} else if ("xxxx...".equals(cycleType)){
			
			for(int i=0; i<expect.length; i++)
				expect[i] = false;
			
		} else if ("oooo...".equals(cycleType)){
			
			for(int i=0; i<expect.length; i++)
				expect[i] = true;
		}
		return new ProcessorImmidiate(source, 0, cycleStep, expectPattern, expect);
	}
}
