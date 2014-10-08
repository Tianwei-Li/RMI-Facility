package src;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;

import testCases.NameServerImpl;
import testCases.ZipCodeRList;
import testCases.ZipCodeRListImpl;

public class TestServer {
	public ServerSocket listenSock;
	public ListenThread listenThread;
	public static TestServer inst;
	static ZipCodeRList zipcode;
	static final String regHost = "127.0.0.1";
	static final  int regPort = 54321;
	public TestServer() throws IOException {
		zipcode = new ZipCodeRListImpl("beijing","15213",null);
		zipcode = zipcode.add("shanghai", "15640");
		
		// register ror to registry
		

		// these are data.
		String ServiceName = "testCases.ZipCodeRList";

		// make ROR.
		RemoteObjectRef ror = new RemoteObjectRef("127.0.0.1", 12345, 111, "testCases.ZipCodeRList");

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
