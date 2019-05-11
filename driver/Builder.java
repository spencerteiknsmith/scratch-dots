package driver;

import java.util.HashMap;
import java.util.ArrayList;

public class Builder {
    private driver.Driver mDriver;
    private page.Page mPage;

    private HashMap<String, driver.Message> mMessages;
    private HashMap<String, driver.Button> mButtons;

    page.Page getPage() {
	return mPage;
    }

    public class CharacterBuilder {
	private page.DotCharacter mCharacter;

	CharacterBuilder(int y, int x, String color) throws page.Page.InvalidActionException {
	    mCharacter = mPage.newDotCharacter(y, x, color);
	}

	public class ScriptBuilder {
	    private script.trigger.Trigger mTrigger;
	    private boolean mWillRepeat;
	    private java.util.Stack<script.ActionSequence> mSequenceStack;
	    private java.util.Stack<Integer> mRepeatCounts;

	    public ScriptBuilder setTrigger(String info) {
		TriggerInfoReader.read(info, this);
		return this;
	    }
	    public ScriptBuilder addBlock(String info) {
		BlockInfoReader.read(info, this);
		return this;
	    }
	    public ScriptBuilder setDoesRepeat(String info) {
		if ("true".equalsIgnoreCase(info)) {
		    mWillRepeat = true;
		}
		return this;
	    }

	    public ScriptBuilder startRepeatBlock(int numReps) {
		mSequenceStack.push(new script.ActionSequence());
		mRepeatCounts.push(numReps);
		return this;
	    }
	    public ScriptBuilder endRepeatBlock() {
		script.ActionSequence repSequence = mSequenceStack.pop();
		int numReps = mRepeatCounts.pop();
		addBlock(new script.actionblock.Repeat(numReps, repSequence));
		return this;
	    }
	    
	    public CharacterBuilder finishScript() throws BadBuildAction {
		if (mTrigger == null) {
		    throw new BadBuildAction("Unable to finish script with null trigger");
		}
		if (mSequenceStack.size() != 1) {
		    throw new BadBuildAction("Unable to finish script with unterminated repeats");
		}
		mCharacter.addScript(
		    new script.Script(mTrigger, mSequenceStack.pop(), mWillRepeat));
		return CharacterBuilder.this;
	    }

	    ScriptBuilder() {
		mTrigger = null;
		mWillRepeat = false;
		mSequenceStack = new java.util.Stack<script.ActionSequence>();
		mSequenceStack.push(new script.ActionSequence());
		mRepeatCounts = new java.util.Stack<Integer>();
	    }
	    Builder outerBuilder() {
		return Builder.this;
	    }

	    void setTrigger(script.trigger.Trigger trigger) {
		mTrigger = trigger;
	    }

	    void addBlock(script.actionblock.ActionBlock block) {
		mSequenceStack.peek().addActionBlock(block);
	    }
	    ScriptBuilder addStopCharacterScriptsBlock() {
		mSequenceStack.peek().addActionBlock(new script.actionblock.StopScripts(mCharacter));
		return this;
	    }
	    ScriptBuilder addTranslateBlock(script.actionblock.Translate.Direction d,
						   int magnitude) {
		mSequenceStack.peek().addActionBlock(new script.actionblock.Translate(mCharacter, mPage.getGrid(),
									  d, magnitude));
		return this;
	    }
	    ScriptBuilder addGoHomeBlock() {
		mSequenceStack.peek().addActionBlock(new script.actionblock.GoHome(mCharacter, mPage.getGrid()));
		return this;
	    }
	}

	public ScriptBuilder startScript() {
	    return new ScriptBuilder();
	}

	public Builder finishCharacter() {
	    return Builder.this;
	}
    }
	      
    public Builder(int numRows, int numCols) {
	mDriver = new driver.Driver(numRows, numCols);
	mPage = mDriver.getPage();
	mMessages = new HashMap<>();
	mButtons = new HashMap<>();
    }

    public Builder newMessage(String text) {
	driver.Message res = mDriver.newMessage(text);
	mMessages.put(text, res);
	return this;
    }
    public Builder newButton(String name) {
	driver.Button res = mDriver.newButton(name);
	mButtons.put(name, res);
	return this;
    }
    public driver.Message getMessage(String text) {
	return mMessages.get(text);
    }
    public driver.Button getButton(String name) {
	return mButtons.get(name);
    }

    
    public CharacterBuilder startCharacter(int y, int x) throws BadBuildAction {
	return startCharacter(y, x, "NONE");
    }
    public CharacterBuilder startCharacter(int y, int x, String color) throws BadBuildAction {
	try {
	    return new CharacterBuilder(y, x, ColorNameToANSI.ansiOf(color));
	}
	catch (page.Page.InvalidActionException e) {
	    throw new BadBuildAction("Can't create new character after finishing");
	}
    }

    public driver.Driver finish() {
	mPage.start();
	return mDriver;
    }

    public static class BadBuildAction extends Exception {
	public BadBuildAction(String msg) {
	    super(msg);
	}
    }
}
