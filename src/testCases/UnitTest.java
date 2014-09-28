package testCases;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import src.RemoteObjectRef;


public class UnitTest {
	public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RemoteObjectRef ror = new RemoteObjectRef("127.0.0.1", 12345, 111, "testCases.Runnable");
		Runnable runnable = (Runnable) ror.localise();
		runnable.run(new String[] {"terry", "jiii"});
		//Method method = runnable.getClass().getMethod("run", null);
		//method.invoke(runnable, null);
	}
}
