package com.exercise.fabrick.demo.model.response;

public class ResponseResource {
    public static final int MAX_STACKTRACE =10;
    public static final String RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO="00";
    public static final String RETCODE_ERRORE_GENERICO="99";
	public static final String RETCODE_ERRORE_NOT_FOUND="04";
    private String retCode;

	private String msgRetCode;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
	public String getMsgRetCode() {
		return msgRetCode;
	}

	public void setMsgRetCode(String msgRetCode) {
		this.msgRetCode = msgRetCode;
	}


	public ResponseResource(String retCode, String msgRetCode) {
		super();
		this.retCode = retCode;
		this.msgRetCode = msgRetCode;
	}

	public ResponseResource(String retCode, Throwable throwable) {
		super();
		this.retCode = retCode;
		StringBuilder sb = new StringBuilder(throwable.toString());
		if(throwable.getMessage() != null)
			sb.append(": ").append(throwable.getMessage());
		if(throwable.getStackTrace().length > 0)
			for(int i=0; i < MAX_STACKTRACE && i < throwable.getStackTrace().length; i++)
				sb.append("\n").append(throwable.getStackTrace()[i]);
		this.msgRetCode = sb.toString();
	}

	public ResponseResource(String retCode) {
		super();
		this.retCode = retCode;
	}
    public ResponseResource() {
		super();
		this.retCode = RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO;
	}

}
