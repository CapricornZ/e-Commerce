package eAlgorithmWS;

public class SingleSourceRow extends ISourceRow{
	
	private String source;
	private int startOff;
	public SingleSourceRow(String source, int startOff){
		this.source = source;
		this.startOff = startOff;
	}
	
	@Override 
	public String getRealSource() { 
		return this.source.substring(this.startOff, this.source.length()-1);
	}
	
	@Override
	public String getSource() {
		return this.source.substring(this.startOff, this.source.length());
	}

	@Override
	public int getStartOff() {
		return this.startOff;
	}
}
