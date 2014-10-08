package Registry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Message.BasicMessage;
import Message.ListMessage;
import Message.LookupMessage;
import Message.Message;
import Message.ObjectMessage;
import Message.RebindMessage;
import Remote.RemoteObjectRef;

public class SimpleRegistry 
{ 
	// registry holds its port and host, and connects to it each time. 
	String Host;
	int Port;

	// ultra simple constructor.
	public SimpleRegistry(String IPAdr, int PortNum)
	{
		Host = IPAdr;
		Port = PortNum;
	}

	@Override
	public String toString() {
		return Host + " " + Port;
	}
	
	public String list() throws UnknownHostException, IOException {
		Socket sendSock = new Socket(Host, Port);

		// ask.
		Message message = new ListMessage();
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
		System.out.println("command is sent.");

		// branch according to the answer.
		try {
			ObjectInputStream ois =  new ObjectInputStream(sendSock.getInputStream());
			BasicMessage recvMessage = (BasicMessage) ois.readObject();
			if (recvMessage != null) {
				return recvMessage.getMsg();
			}
			else {
				return null;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// close the socket.
		sendSock.close();
		return null;
	}

	// returns the ROR (if found) or null (if else)
	public RemoteObjectRef lookup(String serviceName) throws IOException {
		// open socket.
		// it assumes registry is already located by locate registry.
		// you should usually do try-catch here (and later).
		Socket sendSock = new Socket(Host, Port);

		// ask.
		Message message = new LookupMessage(serviceName);
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
		System.out.println("command and service name sent.");

		// branch according to the answer.
		RemoteObjectRef ror = null;
		try {
			ObjectInputStream ois =  new ObjectInputStream(sendSock.getInputStream());
			ObjectMessage recvMessage = (ObjectMessage) ois.readObject();
			if (recvMessage.getObject() != null) {
				System.out.println("it is found!.");
				ror = (RemoteObjectRef)(recvMessage.getObject());
			}
			else {
				System.out.println("it is not found!.");
				ror = null;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// close the socket.
		sendSock.close();

		// return ROR.
		return ror;
	}

	// rebind a ROR. ROR can be null. again no check, on this or whatever. 
	// I hate this but have no time.
	public void rebind(String serviceName, RemoteObjectRef ror) throws IOException
	{
		// open socket. same as before.
		Socket sendSock = new Socket(Host, Port);

		// ask.
		Message message = new RebindMessage(serviceName, ror);
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
				return;
			}
		} else {
			System.out.println("Error: failed to send the message! Client Stub aborted!");
			sendSock.close();
			return;
		}
		System.out.println("command and service name sent.");

		// branch according to the answer.
		try {
			ObjectInputStream ois =  new ObjectInputStream(sendSock.getInputStream());
			BasicMessage recvMessage = (BasicMessage) ois.readObject();
			if (recvMessage != null) {
				System.out.println(recvMessage.getMsg());
			}
			else {
				System.out.println("rebind failed!.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// close the socket.
		sendSock.close();
	}
} 

