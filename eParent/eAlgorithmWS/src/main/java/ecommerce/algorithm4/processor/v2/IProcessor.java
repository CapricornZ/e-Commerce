package ecommerce.algorithm4.processor.v2;

import java.util.List;

public interface IProcessor {
	
	//boolean execute();
	/***
	 * 
	 * @return 期待的数值（如果返回0表示算法已经结束，反之表示算法还需要继续运算）
	 */
	int execute();
	int getMaxStep();
	List<Integer> getProcedure();
	String getClassCycle();
	boolean getExpect();
}
