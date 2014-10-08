package Message;

import java.io.Serializable;

/**
 * Object message is a message used to pass an object.
 * @author wendiz
 *
 */
public class ObjectMessage extends Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6969697688126637410L;
	final Object object;
	public ObjectMessage(Object o) {
		super(MessageType.OBJECT);
		this.object = o;
	}
	public Object getObject() {
		return object;
	}
}
