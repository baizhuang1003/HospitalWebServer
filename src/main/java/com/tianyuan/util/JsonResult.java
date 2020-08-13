package com.tianyuan.util;

public class JsonResult<T> {
	private String message;
	private T content;
	private int code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}



	
	public enum Code {

		SUCCESS(200), FAILED(400) {
			@Override
			public boolean isRest() {
				return true;
			}
		};
		private int value;

		private Code(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public boolean isRest() {
			return false;
		}

	}
}
