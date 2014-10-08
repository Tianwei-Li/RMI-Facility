package Message;

import java.io.Serializable;

public class ListMessage extends Message implements Serializable {
	public ListMessage() {
		super(MessageType.LIST);
	}
}
