package Client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

import Message.Message;
import Message.ObjectMessage;
import Message.RMIMessage;
import Remote.RemoteObjectRef;

public class StubHandler implements InvocationHandler{
	final RemoteObjectRef ror;
	public StubHandler(RemoteObjectRef ror) {
		this.ror = ror;
	}

	/**
	 * Override the invoke method to control the behavior when a Stub method get called
	 * arg0 is the caller object.
	 * arg1 is the method that get called.
	 * arg2 is the argument for the method.
	 */
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		//Create an array of Class object, recording type of the arguments
		@SuppressWarnings("rawtypes")
		final Class[] argClass;
		if (arg2 == null) {
			argClass = null;
		} else {
			argClass = new Class[arg2.length];
			for (int i = 0; i < argClass.length; ++i) {
				argClass[i] = arg2[i].getClass();
			}
		}
		//Constructing RMI message send to server.
		final Message message = new RMIMessage(arg2, argClass, arg1.getName(), ror);
		final Socket socket = send(message, ror.IP_adr, ror.Port);
		if (socket == null) {
			System.out.println("Connection error.");
		} else {
			//Get returned Object.
			Object receiveObject = receive(socket);
			socket.close();
			//If an ROR is returned, call localise to return the stub
			//(a proxy instance in our design) to the client.
			if (receiveObject instanceof RemoteObjectRef) {
				return ((RemoteObjectRef) receiveObject).localise();
			}
			return receiveObject;
		}
		//System.out.println(message);
		return null;
	}
	/**
	 * Receive an object from the given socket and return.
	 * @param socket
	 * @return
	 */
	public Object receive(Socket socket) {
		try {
			ObjectInputStream ois =  new ObjectInputStream(socket.getInputStream());
			ObjectMessage message = (ObjectMessage) ois.readObject();
			return message.getObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Send the message to the given ip and port number.
	 * @param message
	 * @param ip
	 * @param port
	 * @return
	 * @throws IOException
	 */
	public Socket send(Message message, String ip, int port) throws IOException {
		// setup connection if there is not already existed
		Socket sendSock;
		try {
            sendSock = new Socket(ip, port);
        } catch (UnknownHostException e) {
            System.out.println("Server is offline!");
            sendSock = null;
             
        } catch (IOException e) {
            System.out.println("Server is offline!");
            sendSock = null;
        }
         
        if (sendSock == null) {
            // createSendSock failed
            return null;
        }
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
