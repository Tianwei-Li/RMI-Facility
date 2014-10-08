package Message;

import java.io.Serializable;

public class ListMessage extends Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8405650407342635551L;

	public ListMessage() {
		super(MessageType.LIST);
	}
}
