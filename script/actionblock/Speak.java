package script.actionblock;

public final class Speak implements ActionBlock {
    private final String mMessage;
    
    @Override
    public String toString() {
	return String.format("Spk:%-2.8s", mMessage);
    }

    @Override
    public boolean step() {
	System.out.print(mMessage);
	return true;
    }

    @Override
    public ActionBlock copy() {
	return new Speak(this);
    }

    public Speak(String message) {
	mMessage = message;
    }

    Speak(Speak rhs) {
	this(rhs.mMessage);
    }
}
