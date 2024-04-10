package com.exercise.fabrick.demo.exception;

public class FabrickException extends Exception{
	private String retCode;
    private String msgRetCode;

	public FabrickException(String errorMessage,String retcode, String msgRetCode) {
		super(errorMessage);
		this.msgRetCode = msgRetCode;
        this.retCode= retcode;
	}
	
	public FabrickException(String msgRetCode, String retcode, Throwable err) {
		super(err);
		this.msgRetCode = msgRetCode;
        this.retCode= retcode;
	}

    public FabrickException(String msgRetCode, String retcode) {
		super(msgRetCode);
		this.msgRetCode = msgRetCode;
        this.retCode= retcode;
	}


	public String getMsgRetCode() {
		return msgRetCode;
	}

	public void setMsgRetCode(String msgRetCode) {
		this.msgRetCode = msgRetCode;
	}

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
}
