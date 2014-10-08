package src;

import java.io.Serializable;

public class RebindMessage extends Message implements Serializable{
	final String serviceName;
	final RemoteObjectRef ror;
	public RebindMessage(String serviceName, RemoteObjectRef ror) {
		super(MessageType.REBIND);
		this.serviceName = serviceName;
		this.ror = ror;
	}
	public String getServiceName() {
		return serviceName;
	}
	
	public RemoteObjectRef getRor() {
		return ror;
	}
}
