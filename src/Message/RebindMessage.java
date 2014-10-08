package Message;

import java.io.Serializable;

import Remote.RemoteObjectRef;

/**
 * This message is used to rebind a service name and ROR.
 * @author Terryli
 *
 */
public class RebindMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4117438874555433925L;
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
