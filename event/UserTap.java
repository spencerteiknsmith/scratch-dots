package event;

public final class UserTap extends LocalEvent {
    public UserTap(int y, int x) {
	super(y, x);
    }

    @Override
    public String toString() {
	return new StringBuilder()
	    .append("User tap at ")
	    .append(super.toString())
	    .toString();
    }
}
