package ecommerce.algorithm1.processor;

import java.util.List;

public interface IProcessor {
	
	//boolean execute();
	int execute();
	int getMaxStep();
	List<Integer> getProcedure();
}
