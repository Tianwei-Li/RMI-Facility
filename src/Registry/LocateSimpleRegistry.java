package Registry;
import java.net.*;
import java.io.*;

import Message.LocateRegMessage;
import Message.Message;

public class LocateSimpleRegistry { 
	// this is the SOLE static method.
	// you use it as: LocateSimpleRegistry.getRegistry(123.123.123.123, 2048)
	// and it returns null if there is none, else it returns the registry.
	// actually the registry is just a pair of host IP and port. 
	// inefficient? well you can change it as you like. 
	// for the rest, you can see SimpleRegistry.java.
	public static SimpleRegistry getRegistry(String host, int port) {
		// open socket.
		try {
			final Socket sendSock = new Socket(host, port);

			// ask.
			final Message message = new LocateRegMessage("who are you?");
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
			

			// gets answer.
	        try {
	        	final ObjectInputStream ois =  new ObjectInputStream(sendSock.getInputStream());
	        	final LocateRegMessage recvMessage = (LocateRegMessage) ois.readObject();
				if (recvMessage.msg.equals("I am a simple registry.")) {
					return new SimpleRegistry(host, port);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	        sendSock.close();
		}
		catch (Exception e) { 
			System.out.println("nobody is there!"+e);
			return null;
		}
		
		return null;
	}
}

