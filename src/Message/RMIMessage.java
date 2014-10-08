package Message;

import java.io.Serializable;
import java.util.Arrays;

import Remote.RemoteObjectRef;

/**
 * This message is the key message that send the calling information of an ROR
 * @author wendiz
 *
 */
@SuppressWarnings("rawtypes")
public class RMIMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2947511566888739994L;
	public final Object[] args;	//Arguments of the method.
	public final Class[] argClass;	//Type of the arguments.
	public final String method;	//Name of the method.
	public final RemoteObjectRef ror;	//ROR is used to get real object on the server.
	
	public RMIMessage(Object[] args, Class[] argClass, String method, RemoteObjectRef ror) {
		super(MessageType.RMI);
		this.args = args;
		this.argClass = argClass;
		this.method = method;
		this.ror = ror;
	}
	@Override
	public String toString() {
		return "RMIMessage [args=" + Arrays.toString(args) + ", argClass="
				+ Arrays.toString(argClass) + ", method=" + method + ", ror="
				+ ror + "]";
	}
	public Object[] getArgs() {
		return args;
	}
	public Class[] getArgClass() {
		return argClass;
	}
	public String getMethod() {
		return method;
	}
	public RemoteObjectRef getRor() {
		return ror;
	}
}
