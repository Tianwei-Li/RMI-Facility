package Remote;
import java.util.*;

// This is simple. ROR needs a new object key for each remote object (or its skeleton). 
// This can be done easily, for example by using a counter.
// We also assume a remote object implements only one interface, which is a remote interface.

public class RORtbl
{
    // I omit all instance variables. you can use hash table, for example.
    // The table would have a key by ROR.
	HashMap<RemoteObjectRef, Object> RORtoOBJTable = new HashMap<>();
	HashMap<Object, RemoteObjectRef> OBJtoRORTable = new HashMap<>();
    
    // make a new table. 
    public RORtbl() {}

    // add a remote object to the table. 
    // Given an object, you can get its class, hence its remote interface.
    // Using it, you can construct a ROR. 
    // The host and port are not used unless it is exported outside.
    // In any way, it is better to have it for uniformity.
    public void add(RemoteObjectRef ror, Object o) {
    	RORtoOBJTable.put(ror, o);
    	OBJtoRORTable.put(o, ror);
	}
    
    public void remove(RemoteObjectRef ror) {
    	if (RORtoOBJTable.containsKey(ror) == false) {
    		return;
    	}
    	
    	Object obj = RORtoOBJTable.remove(ror);
    	OBJtoRORTable.remove(obj);
    }
    
    public void remove(Object obj) {
    	if (OBJtoRORTable.containsKey(obj) == false) {
    		return;
    	}
    	RemoteObjectRef ror = OBJtoRORTable.remove(obj);
    	RORtoOBJTable.remove(ror);
    }

    // given ror, find the corresponding object.
    public Object findOBJ(RemoteObjectRef ror) {
	    if (RORtoOBJTable.containsKey(ror) == true) {
	    	return RORtoOBJTable.get(ror);
	    }
	    return null;
	}
    
    // given object, find the corresponding ror.
    public RemoteObjectRef findROR(Object o) {
	    if (OBJtoRORTable.containsKey(o) == true) {
	    	return OBJtoRORTable.get(o);
	    }
	    return null;
	}
    
    public void clear() {
    	RORtoOBJTable.clear();
    	OBJtoRORTable.clear();
    }
}
