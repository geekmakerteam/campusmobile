package com.geeklub.vass.mc4android.app.beans;

public class ApiEntity {

	private int id;
	private String apiName;
	
	public ApiEntity(int id, String apiName) {
		super();
		this.id = id;
		this.apiName = apiName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getApiName() {
		return apiName;
	}
	
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
}
