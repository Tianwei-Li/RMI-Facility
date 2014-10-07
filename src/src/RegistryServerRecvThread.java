package src;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;

/**
 * The RecvThread is used for each node to keep read message from another node
 *
 */
public class RegistryServerRecvThread extends Thread {
	Socket recvSocket = null;   // save the socket and input stream object to re-use
	ObjectInputStream ois = null;

	public RegistryServerRecvThread(Socket recvSocket) {
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
					MessageType msgType = message.getType();

					if (msgType == MessageType.LOCATE_MSG) {
						LocateRegMessage reMsg = new LocateRegMessage("I am a simple registry.");
						send(reMsg, recvSocket);

					} else if (msgType == MessageType.LOOK_UP) {
						LookupMessage msg = (LookupMessage)message;
						RemoteObjectRef ror = TestRegistryServer.getInstance().lookup(msg.getServiceName());
						ObjectMessage objMsg = new ObjectMessage(ror);
						send(objMsg, recvSocket);

					} else if (msgType == MessageType.REBIND) {
						// rebind message
						RebindMessage msg = (RebindMessage)message;
						TestRegistryServer.getInstance().rebind(msg.getServiceName(), msg.getRor());
						BasicMessage basicMsg = new BasicMessage("rebind succeed!");
						send(basicMsg, recvSocket);
					} else if (msgType == MessageType.LIST) {
						// TODO: list all elements in the registry
					}
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			recvSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Socket send(Message message, Socket sendSock) throws IOException {
		// setup connection if there is not already existed

		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(sendSock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (output != null) {
			try {
				output.writeObject(message);
				output.flush();
				output.reset();
			}
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error: failed to send the message! Client Stub aborted!");
				sendSock.close();
				return null;
			}
		} else {
			System.out.println("Error: failed to send the message! Client Stub aborted!");
			sendSock.close();
			return null;
		}
		return sendSock;
	}

}
