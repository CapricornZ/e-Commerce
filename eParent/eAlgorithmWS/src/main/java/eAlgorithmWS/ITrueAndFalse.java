package eAlgorithmWS;

import java.util.List;

public interface ITrueAndFalse {
	void run(int offset);
	
	/***
	 * 当前正要运算的o/x位置
	 * @return
	 */
	int getSourcePos();
	List<eAlgorithmWS.Item> getSource();
	/***
	 * 当前正要运算的MetaData位置
	 * @return
	 */
	int getMetaPos();
}
