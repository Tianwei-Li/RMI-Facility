package src;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;

import testCases.Runnable;
import testCases.RunnableImpl;

public class TestServer {
	ServerSocket listenSock;
	ListenThread listenThread;
	public static TestServer inst;
	static Runnable runnable;
	public TestServer() throws IOException {
		runnable = new RunnableImpl();

		// register ror to registry
		String regHost = "127.0.0.1";
		int regPort = 54321;

		// these are data.
		String ServiceName = "Runnable";

		// make ROR.
		RemoteObjectRef ror = new RemoteObjectRef("127.0.0.1", 12345, 111, "testCases.Runnable");

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
			Method method = runnable.getClass().getMethod(message.getMethod(), message.getArgClass());
			return method.invoke(runnable, message.args);
		} catch (Exception e) {
			return e.getStackTrace();
		}
	}
}
