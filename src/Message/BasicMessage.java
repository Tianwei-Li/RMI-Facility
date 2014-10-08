package Message;

import java.io.Serializable;

public class BasicMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1360329399138105515L;
	final String msg;
	public BasicMessage(String msg) {
		super(MessageType.BASIC_MSG);
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
}
