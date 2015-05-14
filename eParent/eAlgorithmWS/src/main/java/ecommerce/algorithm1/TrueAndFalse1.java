package ecommerce.algorithm1;

import ecommerce.algorithm1.processor.IProcessor;
import ecommerce.algorithm1.processor.Processor3O;
import ecommerce.algorithm1.processor.Start;

public class TrueAndFalse1 implements ITrueAndFalse{
	
	private boolean[] source;
	private int header;
	private String type;
	public TrueAndFalse1(boolean[] source, int header){
		this.source = source;
		this.header = header;
	}
	
	@Override
	public boolean[] getSource(){ return this.source; }
	
	@Override
	public String getType() { return this.type; }
	
	@Override
	public Result execute(int cycleStep){

		String class3O = "ecommerce.algorithm1.processor.CycleNegtive";
		String class3X = "ecommerce.algorithm1.processor.CyclePositive";
		boolean bStop = false;
		IProcessor processor = Start.findProcessor(source, cycleStep, class3X, class3O);
		if(null != processor){
			bStop = processor.execute();
			this.type = processor instanceof Processor3O ? "NEGTIVE" : "POSITIVE";
			return new Result(processor.getProcedure(), processor.getMaxStep(), bStop);
		} else
			return null;
		
		
	}
	
	@Override
	public String getFormated(){
		
		int countFalse = 0, countTrue = 0;
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<=this.header; i++)
			sb.append("-");
		
		for(boolean val : source){
			if(val){
				sb.append("o");
				countTrue ++ ;
			} else {
				sb.append("x");
				countFalse ++ ;
			}
		}
		sb.append(String.format(" [x:%d (%f%%), o:%d (%f%%)]", 
				countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), countTrue, ((float)countTrue*100/(float)(countFalse+countTrue))));
		return sb.toString();
	}
}
