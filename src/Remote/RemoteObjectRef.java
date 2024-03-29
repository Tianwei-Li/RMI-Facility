package Remote;

import java.io.Serializable;
import java.lang.reflect.Proxy;

import Client.StubHandler;
/**
 * 
 * @author wendiz
 * This is the class for remote object reference
 *
 */

public class RemoteObjectRef implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4213346249184427866L;
	public String IP_adr;
	public int Port;
	public long Obj_Key;
	public String Remote_Interface_Name;
	transient Object proxy;

	public RemoteObjectRef(String ip, int port, long obj_key, String riname) 
	{
		IP_adr=ip;
		Port=port;
		Obj_Key=obj_key;
		Remote_Interface_Name=riname;
	}

	// this method is important, since it is a stub creator.
	// 
	@SuppressWarnings("rawtypes")
	public Object localise() throws ClassNotFoundException
	{
		if (proxy == null) {
			final Class rmiClass = Class.forName(Remote_Interface_Name);
			proxy = Proxy.newProxyInstance(rmiClass.getClassLoader(), new Class<?>[] {rmiClass}, new StubHandler(this));
		}
		return proxy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + Obj_Key);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteObjectRef other = (RemoteObjectRef) obj;
		if (Obj_Key != other.Obj_Key)
			return false;
		return true;
	}
}
