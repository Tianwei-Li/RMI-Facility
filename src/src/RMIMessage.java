package src;

import java.io.Serializable;
import java.util.Arrays;
@SuppressWarnings("rawtypes")
public class RMIMessage extends Message implements Serializable{
	final Object[] args;
	final Class[] argClass;
	final String method;
	final RemoteObjectRef ror;
	
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
