package script.trigger;

import event.Event;
import event.UserTap;

public final class OnTapTrigger implements Trigger {
    @Override
    public String toString() {
	return "ontap()";
    }

    @Override
    public boolean isTriggeredBy(Event e) {
	return e instanceof UserTap;
    }

    public OnTapTrigger() {
    }
}
