package ecommerce.algorithm1.domain;

import java.util.Date;

public class Record {
	
	private int id;
	private String source;
	private String trueANDfalse;
	private String result;
	private Date createTime;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTrueANDfalse() {
		return trueANDfalse;
	}
	public void setTrueANDfalse(String trueANDfalse) {
		this.trueANDfalse = trueANDfalse;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
