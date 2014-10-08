package Message;

import java.io.Serializable;
import java.util.Arrays;
@SuppressWarnings("rawtypes")
public class LocateRegMessage extends Message implements Serializable{
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
