package event;

public abstract class LocalEvent implements Event {
    private final int mY;
    private final int mX;

    @Override
    public String toString() {
	return new StringBuilder()
	    .append("y: ").append(mY)
	    .append(", x: ").append(mX)
	    .toString();
    }
    
    protected LocalEvent(int y, int x) {
	mY = y;
	mX = x;
    }
    public int getY() {
	return mY;
    }
    public int getX() {
	return mX;
    }
}
