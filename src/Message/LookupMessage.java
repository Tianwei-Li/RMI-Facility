package Message;

import java.io.Serializable;

public class LookupMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2429501105588413209L;
	final String serviceName;
	public LookupMessage(String serviceName) {
		super(MessageType.LOOK_UP);
		this.serviceName = serviceName;
	}
	public String getServiceName() {
		return serviceName;
	}
}
