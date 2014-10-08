package testCases;

import java.util.Arrays;

public class RunnableImpl implements Runnable {

	@Override
	public void run(String arg) {
		// TODO Auto-generated method stub
		System.out.println("get String" + arg);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("get empty rum");
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


}
