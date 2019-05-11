package driver;

public class Message {
    private final String mText;

    @Override
    public String toString() {
	return mText;
    }
    
    Message(String text) {
	mText = text;
    }
}
