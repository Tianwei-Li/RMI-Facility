package testCases;

import java.util.Arrays;

public class RunnableImpl implements Runnable {
	static public String serviceName = "testCases.Runnable";
	static public int a = 1;
	@Override
	public void run(String arg) {
		// TODO Auto-generated method stub
		System.out.println("get String" + arg);
	}

	@Override
	public Runnable run() {
		// TODO Auto-generated method stub
		System.out.println("get empty rum");
		return this;
	}

	@Override
	public String run(String[] args) {
		// TODO Auto-generated method stub
		return ("get args" + Arrays.toString(args));
	}

	@Override
	public void run(int[] args) {
		// TODO Auto-generated method stub
		System.out.println("get int args" + Arrays.toString(args));
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}


}
