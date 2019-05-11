package event;

import driver.Message;

public final class MessageSent extends GlobalEvent {
    private final Message mMsg;

    @Override
    public String toString() {
	return new StringBuilder()
	    .append("Sent msg: ")
	    .append(mMsg)
	    .toString();
    }
    
    public MessageSent(Message msg) {
	mMsg = msg;
    }
    public Message getMessage() {
	return mMsg;
    }
}
    
