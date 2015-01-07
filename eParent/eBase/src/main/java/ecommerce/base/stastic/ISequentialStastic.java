package ecommerce.base.stastic;

import java.util.List;
import java.util.Map;

import ecommerce.base.ITrueAndFalse;

/***
 * 连续o/x的统计
 * @author martin
 *
 */
public interface ISequentialStastic {
	
	Map<Integer, Integer> getCountOfSeqX();
	Map<Integer, Integer> getCountOfSeqO();
	
	void run(List<List<ITrueAndFalse>> totalResult, int maxSection);
	int getMaxCountOfSeq();

}
