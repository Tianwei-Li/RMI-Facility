package src;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;

/**
 * The RecvThread is used for each node to keep read message from another node
 *
 */
public class RecvThread extends Thread {
	Socket recvSocket = null;   // save the socket and input stream object to re-use
	ObjectInputStream ois = null;
	
	public RecvThread(Socket recvSocket) {
		super();
		this.recvSocket = recvSocket;
		try {
			ois = new ObjectInputStream(recvSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			// the inst.isActive() is false when the node is shut down
			while (true) {
				Message message = (Message) ois.readObject();
				if (message != null) {
					System.out.println("received new process!");
					if (message.getType() == MessageType.RMI) {
						TestServer.handleRMI((RMIMessage)message);
					}
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			recvSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
