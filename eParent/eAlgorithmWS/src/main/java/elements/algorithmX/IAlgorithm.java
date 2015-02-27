package elements.algorithmX;

import eAlgorithmWS.ISourceRow;
import eAlgorithmWS.ITrueAndFalse;

public interface IAlgorithm {
	/***
	 * 
	 * @param source 数据源
	 * @param continuity 连续X的个数
	 * @return
	 */
	ITrueAndFalse execute(ISourceRow source, int continuity);
}
