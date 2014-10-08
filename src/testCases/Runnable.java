package testCases;

import src.Remote;

public interface Runnable extends Remote {
	public void run(String arg);
	public String run(String[] args);
	public void run(int[] args);
	public Runnable run();
}
