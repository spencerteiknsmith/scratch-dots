package script.trigger;

import driver.Message;
import event.Event;
import event.MessageSent;

public final class OnMessageTrigger implements Trigger {
    private final Message mMsg;

    @Override
    public String toString() {
	return String.format("onmessage(%s)", mMsg);
    }

    public OnMessageTrigger(Message msg) {
	mMsg = msg;
    }

    @Override
    public boolean isTriggeredBy(Event e) {
	return e instanceof MessageSent
	    && ((MessageSent)e).getMessage() == mMsg;
    }
}
