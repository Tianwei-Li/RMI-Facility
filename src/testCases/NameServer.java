package testCases;
import src.Remote;
import src.RemoteObjectRef;

public interface NameServer extends Remote// extends YourRemote 
{
    public RemoteObjectRef match(String name);
    public NameServer add(String s, RemoteObjectRef r, NameServer n);
    public NameServer next();   
}

