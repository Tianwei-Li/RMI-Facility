package testCases;

import java.io.Serializable;

public class ZipCodeList implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4070105540610303648L;
	public String city;
    public String ZipCode;
    public ZipCodeList next;

    public ZipCodeList(String c, String z, ZipCodeList n)
    {
	city=c;
	ZipCode=z;
	next=n;
    }
}
