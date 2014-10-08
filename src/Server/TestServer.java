package Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.UUID;

import Message.RMIMessage;
import Registry.LocateSimpleRegistry;
import Registry.SimpleRegistry;
import Remote.RORtbl;
import Remote.Remote;
import Remote.RemoteObjectRef;
import Remote.ZipCodeRList;
import Remote.ZipCodeRListImpl;

import testCases.NameServerImpl;

public class TestServer {
	public ServerSocket listenSock;
	public ListenThread listenThread;
	public static TestServer inst;
	static final String regHost = "127.0.0.1";
	static final  int regPort = 54321;
	static public  RORtbl rortbl;
	public TestServer() throws IOException {
		ZipCodeRList zipcode = new ZipCodeRListImpl("beijing","15213",null);
		
		// register ror to registry
		

		// these are data.
		String ServiceName = "testCases.ZipCodeRList";

		// make ROR.
		RemoteObjectRef ror = new RemoteObjectRef("127.0.0.1", 12345, UUID.randomUUID().getLeastSignificantBits(), "testCases.ZipCodeRList");

		// locate.
		SimpleRegistry sr = LocateSimpleRegistry.getRegistry(regHost, regPort);

		if (sr != null) {
			System.out.println("located registry server at " + sr.toString());
			// bind.
			sr.rebind(ServiceName, ror);
		}
		else {
			System.out.println("no registry found.");
		}
		
		rortbl = new RORtbl();
		rortbl.add(ror, zipcode);


		try {
			listenSock = new ServerSocket(12345);

		} catch (IOException e) {
			e.printStackTrace();
		}
		listenThread = new ListenThread();
		listenThread.start();
	}
	
	
	public static TestServer getInstance() {
		return inst;
	}
	public static void main(String... args) throws IOException {
		inst = new TestServer();
	}

	public static Object handleRMI(RMIMessage message) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			Object remoteInstance = rortbl.findOBJ(message.ror);
			Method method = zipcode.getClass().getMethod(message.getMethod(), message.getArgClass());
			Object returnObject = method.invoke(zipcode, message.args);
			if (returnObject instanceof Remote) {
				//TODO: remove hard code
				zipcode = (ZipCodeRList)returnObject;
				SimpleRegistry sr = LocateSimpleRegistry.getRegistry(regHost, regPort);
				returnObject = sr.lookup(((Remote) returnObject).getServiceName());
			}
			return returnObject;
		} catch (Exception e) {
			return e.getStackTrace();
		}
	}
}
