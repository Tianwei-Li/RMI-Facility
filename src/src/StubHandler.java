package src;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
		RMIMessage message = new RMIMessage(arg2, argClass, arg1.getName(), ror);
		System.out.println(message);
		return null;
	}
	
}
