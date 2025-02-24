package com.qb.assessment.grocery.booking.dto;

public class ResponseObjectDto {

	private String responseMsg;
	private String responseStatus;

	private Object object;

	public String getResponseMsg() {
	return responseMsg;
	}
	
	public void setResponseMsg(String responseMsg) {
	this.responseMsg = responseMsg;
	}
	
	public String getResponseStatus () {
	return responseStatus;
	}
	
	public void setResponseStatus(String responseStatus) {
	this.responseStatus = responseStatus;
	}
	
	public Object getObject() {
	return object;
	}
	
	public void setobject(Object object) {
	this.object = object;
	}
}
