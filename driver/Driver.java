package driver;

import page.Page;
import java.util.Set;
import java.util.HashSet;

public class Driver {
    private final Page mPage;
    private final Set<Button> mButtons;
    private final Set<Message> mMessages;

    private printing.GridPrinter mGridPrinter;

    private page.Grid grid() {
	return mPage.getGrid();
    }
    public void prepareGridPrinter() {
	mGridPrinter.prepareScreen();
	mGridPrinter.printDividers();
    }
    public void updateGridPrint() {
	java.util.HashMap<page.Grid.Coord, String> updates = mPage.getGrid().flushChanges();
	for (java.util.Map.Entry<page.Grid.Coord, String> update : updates.entrySet()) {
	    mGridPrinter.printCell(update.getKey().getY(), update.getKey().getX(), update.getValue());
	}
    }
    @Override
    public String toString() {
	return new StringBuilder()
	    .append("\nButtons: ").append(mButtons)
	    .append("\nMessages: ").append(mMessages)
	    .append("\n\nActive page:\n").append(mPage)
	    .toString();
    }
    
    Driver(int rows, int columns) {
	mPage = new Page(rows, columns);
	mButtons = new HashSet<Button>();
	mMessages = new HashSet<Message>();
	mGridPrinter = new printing.GridPrinter(rows, columns, 2, 4);
    }

    public Page getPage() {
	return mPage;
    }

    public boolean hasActivity() {
	return mPage.hasActivity();
    }
    
    public void touch(int y, int x) {
	mPage.touched(y, x);
    }
    public void step() throws BadDriverAction {
	try {
	    mPage.step();
	}
	catch (Page.InvalidActionException e) {
	    throw new BadDriverAction(e.getMessage());
	}
    }

    public Button newButton() {
	return newButton("No Name");
    }
    public Button newButton(String name) {
	Button button = new Button(name, this);
	mButtons.add(button);
	return button;
    }
    public Message newMessage(String text) {
	Message message = new Message(text);
	mMessages.add(message);
	return message;
    }

    void buttonPressed(Button button) {
	mPage.buttonPressed(button);
    }

    public static class BadDriverAction extends Exception {
	BadDriverAction(String msg) {
	    super(msg);
	}
    }	
}
	
