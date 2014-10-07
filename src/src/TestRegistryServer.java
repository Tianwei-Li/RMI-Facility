package src;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;


public class TestRegistryServer {
	ServerSocket listenSock;
	RegistryServerListenThread listenThread;
	Hashtable<String, RemoteObjectRef> regTable = null;
	public static TestRegistryServer inst;
	
	public TestRegistryServer() {
		try {
			listenSock = new ServerSocket(54321);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		listenThread = new RegistryServerListenThread();
		listenThread.start();
		regTable = new Hashtable<String, RemoteObjectRef>();
	}
	
	public void rebind(String serviceName, RemoteObjectRef ror) {
		regTable.put(serviceName, ror);
	}
	
	public RemoteObjectRef lookup(String serviceName) {
		if (regTable.contains(serviceName) == true) {
			return regTable.get(serviceName);
		}
		return null;
	} 
	
	
	
	public static TestRegistryServer getInstance() {
		return inst;
	}
	public static void main(String... args) {
		inst = new TestRegistryServer();
	}
}
