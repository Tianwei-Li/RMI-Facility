package Message;

import java.io.Serializable;

/**
 * This is a message that request a list of all the records in Registry table.
 * @author wendiz
 *
 */
public class ListMessage extends Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8405650407342635551L;

	public ListMessage() {
		super(MessageType.LIST);
	}
}
