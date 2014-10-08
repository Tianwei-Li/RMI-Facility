package Registry;
import java.net.*;
import java.io.*;
/**
 * 
 * @author tianweil
 * The listening thread for ProcessManager, listen at configured port, 
 * create socket when getting a communication establish request.
 */
public class RegistryServerListenThread extends Thread {
	
	public RegistryServerListenThread() {
		super();
	}
	
	@Override
	public void run() {
		final TestRegistryServer inst = TestRegistryServer.getInstance();
		try {
			while (true) {
				final Socket skt = inst.listenSock.accept();
				(new RegistryServerRecvThread(skt)).start();
			}
		} catch (IOException e) {
			System.out.println("listening thread terminated successfully.");
			//e.printStackTrace();
		}
	}

}
