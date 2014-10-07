package testCases;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import src.LocateSimpleRegistry;
import src.RemoteObjectRef;
import src.SimpleRegistry;


public class UnitTest {
	public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		// lookup registry
		String regHost = "127.0.0.1";
		int regPort = 54321;
		String ServiceName = "Runnable";

		System.out.println("We lookup "+ ServiceName);

		// locate.
		SimpleRegistry sr = LocateSimpleRegistry.getRegistry(regHost, regPort);
		RemoteObjectRef ror = null;
		if (sr != null) {
			System.out.println("located registry server at "+ sr.toString());
			// lookup.
			ror = sr.lookup(ServiceName);
			if (ror == null) {
				System.out.println("The service is bound to no remote object.");
				return;
			} else {
				System.out.println("Found remote object from the registry.");
			}
		}
		else {
			System.out.println("no registry found.");
			return;
		}

		//RemoteObjectRef ror = new RemoteObjectRef("127.0.0.1", 12345, 111, "testCases.Runnable");
		Runnable runnable = (Runnable) ror.localise();
		System.out.println("get result "+runnable.run(new String[]{"123","456"}));
		//Method method = runnable.getClass().getMethod("run", null);
		//method.invoke(runnable, null);
	}
}
