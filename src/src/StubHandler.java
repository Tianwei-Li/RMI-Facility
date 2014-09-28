package src;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

public class StubHandler implements InvocationHandler{
	final RemoteObjectRef ror;
	public StubHandler(RemoteObjectRef ror) {
		this.ror = ror;
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		@SuppressWarnings("rawtypes")
		Class[] argClass;
		if (arg2 == null) {
			argClass = null;
		} else {
			argClass = new Class[arg2.length];
			for (int i = 0; i < argClass.length; ++i) {
				argClass[i] = arg2[i].getClass();
			}
		}
		Message message = new RMIMessage(arg2, argClass, arg1.getName(), ror);
		send(message, ror.IP_adr, ror.Port);
		System.out.println(message);
		return null;
	}
	
	public boolean send(Message message, String ip, int port) throws IOException {
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
            return false;
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
                return false;
            }
        } else {
            System.out.println("Error: failed to send the message! Client Stub aborted!");
            sendSock.close();
            return false;
        }
        sendSock.close();
        return true;
	}
	
}
