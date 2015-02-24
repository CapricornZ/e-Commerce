package ecommerce.patterns.resultrow.stop;

import java.util.List;

public interface IStop {
	boolean match(List<List<Boolean>> result);
}
