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
	public TestServer() {
		runnable = new RunnableImpl();
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
	public static void main(String... args) {
		inst = new TestServer();
	}
	
	public static void handleRMI(RMIMessage message) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = runnable.getClass().getMethod(message.getMethod(), message.getArgClass());
		method.invoke(runnable, message.args);
	}
}
