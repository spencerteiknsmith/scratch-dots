package script.trigger;

import event.Event;
import event.Collision;

public final class OnCollisionTrigger implements Trigger {
    @Override
    public String toString() {
	return "oncollision()";
    }

    @Override
    public boolean isTriggeredBy(Event e) {
	return e instanceof Collision;
    }

    public OnCollisionTrigger() {
    }
}
