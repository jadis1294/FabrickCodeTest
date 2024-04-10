package com.exercise.fabrick.demo.exception;

public class RecordNotFoundException extends Exception{
    public static final String RETCODE_ERRORE_NOT_FOUND="04";
	private String retCode;
    private String msgRetCode;

	public RecordNotFoundException(String errorMessage, String msgRetCode) {
		super(errorMessage);
		this.msgRetCode = msgRetCode;
        this.retCode= RETCODE_ERRORE_NOT_FOUND;
	}
	
	public RecordNotFoundException(String msgRetCode, Throwable err) {
		super(err);
		this.msgRetCode = msgRetCode;
        this.retCode= RETCODE_ERRORE_NOT_FOUND;
	}
    public RecordNotFoundException(String msgRetCode) {
		super(msgRetCode);
		this.msgRetCode = msgRetCode;
        this.retCode= RETCODE_ERRORE_NOT_FOUND;
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
