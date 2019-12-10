package com.example.getstarted.objects;

public class Convo {
	private long fromId;
	private String message;
	private String time;
	
	public static final String FROMID = "from1";
	public static final String MESSAGE = "message";
	public static final String TIME = "createdAt";
	private Convo(Builder builder) {
		this.fromId = builder.fromId;
		this.message = builder.message;
		this.time = builder.time;
	}
	public static class Builder{
		private long fromId;
		private String message;
		private String time;
		
		public Builder fromId(long fromId) {
			this.fromId = fromId;
			return this;
		}
		public Builder message(String message) {
			this.message = message;
			return this;
		}
		public Builder time(String time) {
			this.time = time;
			return this;
		}
		public Convo build() {
			return new Convo(this);
		}
	}
	public long getFromId() {
		return fromId;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
