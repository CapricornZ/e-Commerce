package ecommerce.algorithm4.stastic;

import java.util.List;
import java.util.Map;

import ecommerce.algorithm4.TrueAndFalse;

/***
 * 连续o/x的统计
 * @author martin
 *
 */
public interface ISequentialStastic {
	
	Map<Integer, Integer> getCountOfSeqX();
	Map<Integer, Integer> getCountOfSeqO();
	
	void run(List<List<TrueAndFalse>> totalResult, int maxSection);
	int getMaxCountOfSeq();

}
