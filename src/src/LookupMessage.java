package src;

import java.io.Serializable;

public class LookupMessage extends Message implements Serializable{
	final String serviceName;
	public LookupMessage(String serviceName) {
		super(MessageType.LOOK_UP);
		this.serviceName = serviceName;
	}
	public String getServiceName() {
		return serviceName;
	}
}
