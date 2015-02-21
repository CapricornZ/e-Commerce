package elements.controller;

import java.util.List;

public class Result {
	
	private String sourceRow;
	public String getSourceRow() {
		return sourceRow;
	}
	public void setSourceRow(String sourceRow) {
		this.sourceRow = sourceRow;
	}
	
	private String formatSourceRow;
	public String getFormatSourceRow() {
		return formatSourceRow;
	}
	public void setFormatSourceRow(String formatSourceRow) {
		this.formatSourceRow = formatSourceRow;
	}
	
	private List<Phase> phases;
	public List<Phase> getPhases() {
		return phases;
	}
	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}
	
}