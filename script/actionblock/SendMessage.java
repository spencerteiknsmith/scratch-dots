package script.actionblock;

import event.EventManager;
import event.MessageSent;
import driver.Message;

public final class SendMessage implements ActionBlock {
    private final EventManager mManager;
    private final Message mMsg;

    @Override
    public String toString() {
	return String.format("S:%4.4s", mMsg);
    }
    
    @Override
    public boolean step() {
	mManager.addEvent(new MessageSent(mMsg));
	return true;
    }

    @Override
    public ActionBlock copy() {
	return new SendMessage(this);
    }

    public SendMessage(EventManager eventManager, Message message) {
	mManager = eventManager;
	mMsg = message;
    }
    
    SendMessage(SendMessage rhs) {
	this.mManager = rhs.mManager;
	this.mMsg = rhs.mMsg;
    }
}
