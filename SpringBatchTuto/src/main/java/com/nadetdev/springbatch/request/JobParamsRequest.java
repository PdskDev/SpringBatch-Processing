package com.nadetdev.springbatch.request;

public class JobParamsRequest {
	
	private String paramKey;
	private String paramValue;
	
	public JobParamsRequest() {
		super();
	}
	
	public JobParamsRequest(String paramKey, String paramValue) {
		super();
		this.paramKey = paramKey;
		this.paramValue = paramValue;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobParamsRequest [paramKey=");
		builder.append(paramKey);
		builder.append(", paramValue=");
		builder.append(paramValue);
		builder.append("]");
		return builder.toString();
	}
	
}
