package event;

public final class Collision extends LocalEvent {
    public Collision(int y, int x) {
	super(y, x);
    }

    @Override
    public String toString() {
	return new StringBuilder()
	    .append("Collision at ")
	    .append(super.toString())
	    .toString();
    }
}
