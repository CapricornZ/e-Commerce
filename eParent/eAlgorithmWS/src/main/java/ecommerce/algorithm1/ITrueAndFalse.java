package ecommerce.algorithm1;

public interface ITrueAndFalse {

	boolean[] getSource();
	String getFormated();
	String getType();
	Result execute(int cycleStep, String type);
}
