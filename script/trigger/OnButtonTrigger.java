package script.trigger;

import driver.Button;
import event.Event;
import event.ButtonPressed;

public final class OnButtonTrigger implements Trigger {
    private final Button mButton;

    @Override
    public String toString() {
	return String.format("onbutton(%s)", mButton);
    }
    
    public OnButtonTrigger(Button button) {
	mButton = button;
    }

    @Override
    public boolean isTriggeredBy(Event e) {
	return e instanceof ButtonPressed
	    && ((ButtonPressed)e).getButton() == mButton;
    }
}
