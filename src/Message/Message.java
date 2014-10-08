package Message;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4126699753269048094L;
	final MessageType type;
	public Message(MessageType type) {
		this.type = type;
	}
	public MessageType getType() {
		return type;
	}
}
