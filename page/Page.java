package page;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;

import event.EventManager;
import driver.Button;

public final class Page {
    public static class InvalidActionException extends Exception {
	public InvalidActionException(String msg) {
	    super(msg);
	}
    }

    private final Collection<DotCharacter> mDotCharacters;
    private final Grid mGrid;
    private final EventManager mEventManager;
    private boolean mRunning;

    @Override
    public String toString() {
	StringBuilder res = new StringBuilder();
	res
	    .append("Is running? ").append(mRunning)
	    .append("\nDotCharacters:\n");
	for (DotCharacter dc : mDotCharacters) {
	    if (dc.hasActiveScript()) {
		res.append(dc.fullToString()).append("\n");
	    }
	}
	res
	    .append("\nCurrent events: ").append(mEventManager)
	    .append("\nThe grid:\n").append(mGrid);
	return res.toString();
    }

    public boolean hasActivity() {
	if (mEventManager.hasEvents()) {
	    return true;
	}
	for (DotCharacter dc : mDotCharacters) {
	    if (dc.hasActiveScript()) {
		return true;
	    }
	}
	return false;
    }
    
    public Page(int rows, int cols) {
	mDotCharacters = new ArrayList<DotCharacter>();
	mGrid = new Grid(rows, cols);
	mEventManager = new EventManager();
	mRunning = false;
    }
    public Grid getGrid() {
	return mGrid;
    }
    public EventManager getEventManager() {
	return mEventManager;
    }
    
    public DotCharacter newDotCharacter(int y, int x) throws InvalidActionException {
	return newDotCharacter(y, x, "");
    }
    public DotCharacter newDotCharacter(int y, int x, String color) throws InvalidActionException {
	if (mRunning) {
	    throw new InvalidActionException("Cannot add character while running");
	}
	Grid.Coord coord = mGrid.new Coord(y, x);
	DotCharacter character = new DotCharacter(coord, color);
	mDotCharacters.add(character);
	mGrid.place(coord, character);
	return character;
    }
	
    public void start() {
	mRunning = true;
    } 
    public void step() throws InvalidActionException {
	if (!mRunning) {
	    throw new InvalidActionException("Cannot step when not running");
	}

	for (DotCharacter c : mDotCharacters) {
	    c.step();
	}
	mGrid.addCollisionEvents(mEventManager);
	mGrid.sendOutLocalEvents(mEventManager);
	sendOutNonLocalEvents();
	mEventManager.clear();
    }
    void sendOutNonLocalEvents() {
	for (event.Event e : mEventManager) {
	    if (!(e instanceof event.LocalEvent)) {
		for (DotCharacter c : mDotCharacters) {
		    c.receiveEvent(e);
		}
	    }
	}
    }

    public void touched(int y, int x) {
	mEventManager.addEvent(new event.UserTap(y, x));
    }
    public void buttonPressed(Button button) {
	mEventManager.addEvent(new event.ButtonPressed(button));
    }
}
