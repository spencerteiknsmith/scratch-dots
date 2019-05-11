package event;

import java.util.HashSet;

public final class EventManager implements Iterable<Event> {
    private final HashSet<Event> mEvents;

    @Override
    public String toString() {
	return mEvents.toString();
    }
    
    @Override
    public java.util.Iterator<Event> iterator() {
	return mEvents.iterator();
    }

    public EventManager() {
	mEvents = new HashSet<Event>();
    }
    public void addEvent(Event e) {
	mEvents.add(e);
    }
    public void clear() {
	mEvents.clear();
    }
    public boolean hasEvents() {
	return !mEvents.isEmpty();
    }
}
