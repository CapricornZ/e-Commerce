package ecommerce.base;

import java.util.List;

import ecommerce.patterns.trueandfalse.gonext.IGoNext;
import ecommerce.patterns.trueandfalse.stop.IStop;

public interface ITrueAndFalse {
	
	void print();	
	void run(int offset);
	
	/***
	 * 当前和值
	 * @return
	 */
	int getSum();
	/***
	 * 当前最大值
	 * @return
	 */
	int getMax();
	/***
	 * 当前Delta值
	 * @return
	 */
	int getCurrent();
	/***
	 * 当前正要运算的o/x位置
	 * @return
	 */
	int getResultPos();
	/***
	 * 当前正要运算的MetaData位置
	 * @return
	 */
	int getMetaPos();
	int getCountTrue();
	int getCountFalse();
	List<Boolean> getResult();
	
	void setStop(IStop stop);
	void setGoNext(IGoNext next);
	/***
	 * 获取当前运算过程
	 * @return
	 */
	List<Integer> getProcess();
}
