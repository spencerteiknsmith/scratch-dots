package driver;

public class Button {
    private final String mName;
    private final Driver mOwner;

    @Override
    public String toString() {
	return mName;
    }
    
    Button(String name, Driver owner) {
	mName = name;
	mOwner = owner;
    }

    public void press() {
	mOwner.buttonPressed(this);
    }
}
