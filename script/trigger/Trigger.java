package script.trigger;

import event.Event;

public interface Trigger {
    boolean isTriggeredBy(Event e);
}
