package src;

import java.io.Serializable;
import java.lang.reflect.Proxy;

public class RemoteObjectRef implements Serializable 
{
	String IP_adr;
	int Port;
	int Obj_Key;
	String Remote_Interface_Name;
	transient Object proxy;

	public RemoteObjectRef(String ip, int port, int obj_key, String riname) 
	{
		IP_adr=ip;
		Port=port;
		Obj_Key=obj_key;
		Remote_Interface_Name=riname;
	}

	// this method is important, since it is a stub creator.
	// 
	public Object localise() throws ClassNotFoundException
	{
		if (proxy == null) {
			final Class rmiClass = Class.forName(Remote_Interface_Name);
			proxy = Proxy.newProxyInstance(rmiClass.getClassLoader(), new Class<?>[] {rmiClass}, new StubHandler(this));
		}
		return proxy;
	}
}
