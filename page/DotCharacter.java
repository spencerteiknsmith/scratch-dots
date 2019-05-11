package page;

import script.Script;

public class DotCharacter {
    static int nextID = 0;

    private final int id;
    private final Grid.Coord mHomeCoord;
    private final java.util.Collection<Script> mScripts;
    private final String mColorString;
    
    @Override
    public String toString() {
	return new StringBuilder()
	    .append(mColorString)
	    .append("#").append(id)
	    .append("\u001b[0m")
	    .toString();
    }

    public String fullToString() {
	StringBuilder res = new StringBuilder();
	res.append(this.toString())
	    .append(":\n");
	for (Script script : mScripts) {
	    res.append(script).append("\n");
	}
	return res.toString();
    }
    
    boolean hasActiveScript() {
	for (Script s : mScripts) {
	    if (s.hasActiveInstance()) {
		return true;
	    }
	}
	return false;
    }

    DotCharacter(Grid.Coord homeCoord) {
	this(homeCoord, "");
    }
    DotCharacter(Grid.Coord homeCoord, String color) {
	mColorString = color;
	mHomeCoord = homeCoord.copy();
	mScripts = new java.util.ArrayList<Script>();
	id = ++nextID;
    }
    Grid.Coord getHomeCoord() {
	return mHomeCoord;
    }

    public void addScript(Script script) {
	mScripts.add(script);
    }
    
    private boolean mStopAtEndOfStepping;
    void step() {
	mStopAtEndOfStepping = false;
	for (Script script : mScripts) {
	    script.step();
	}
	if (mStopAtEndOfStepping) {
	    actuallyStopScripts();
	}
    }

    public void stopScripts() {
	mStopAtEndOfStepping = true;
    }
    private void actuallyStopScripts() {
	for (Script script : mScripts) {
	    script.stop();
	}
    }

    void receiveEvent(event.Event e) {
	for (Script s : mScripts) {
	    s.receiveEvent(e);
	}
    }
}
