package src;

import java.io.Serializable;

public enum MessageType implements Serializable{
	BASIC_MSG,
	LOCATE_MSG,
	LOOK_UP,
	REBIND,
	RMI,
	OBJECT,
	LIST;
}
