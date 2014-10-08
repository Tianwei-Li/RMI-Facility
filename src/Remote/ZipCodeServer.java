package Remote;

import testCases.ZipCodeList;

public interface ZipCodeServer extends Remote // extends YourRemote or whatever
{
    public void initialise(ZipCodeList newlist);
    public String find(String city);
    public ZipCodeList findAll();
    public void printAll();
}

