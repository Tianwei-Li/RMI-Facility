package Message;

import java.io.Serializable;

/**
 * This message will request for locate a registry server.
 * @author tianweil
 *
 */
public class LocateRegMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5998352119159478226L;
	public final String msg;
	
	public LocateRegMessage(String msg) {
		super(MessageType.LOCATE_MSG);
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "LocateRegistryMessage [msg=" + msg + "]";
	}
	
	public String getMsg() {
		return msg;
	}
	
}
