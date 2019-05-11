package event;

import driver.Button;

public final class ButtonPressed extends GlobalEvent {
    private final Button mButton;

    @Override
    public String toString() {
	return new StringBuilder()
	    .append("Pressed button: ")
	    .append(mButton)
	    .toString();
    }

    public ButtonPressed(Button button) {
	mButton = button;
    }
    public Button getButton() {
	return mButton;
    }
}
    
