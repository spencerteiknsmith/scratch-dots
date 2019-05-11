package script.actionblock;

import script.ActionSequence;

public final class Repeat implements ActionBlock {
    private final int mNumTimesToRepeat;
    private int mNumRepeatsDone;
    private final ActionSequence mSequence;
    private ActionSequence.ActiveID mRunningID;
    private boolean mIsActive;
    
    @Override
    public String toString() {
	return new StringBuilder()
	    .append(String.format("Rep%d(", mNumTimesToRepeat))
	    .append(mSequence)
	    .append("EndRep)")
	    .toString();
    }

    @Override
    public String instanceToString() {
	StringBuilder res = new StringBuilder();
	res.append(String.format("Rep%d(", mNumTimesToRepeat));
	res.append("\u001b[0m");
	if (!mIsActive) {
	    res.append(mSequence);
	}
	else {
	    res.append(mSequence.instanceToString(mRunningID));
	}
	res.append("   EndRep)");
	return res.toString();
    }

    @Override
    public boolean step() {
	mIsActive = true;
	if (mRunningID == null) {
	    mRunningID = mSequence.startInstance();
	}
	if (mSequence.step(mRunningID)) {
	    ++mNumRepeatsDone;
	    mSequence.restart(mRunningID);
	}
	if (mNumRepeatsDone >= mNumTimesToRepeat) {
	    mNumRepeatsDone = 0;
	    //mRunningID = null;
	    mIsActive = false;
	    return true;
	}
	else {
	    return false;
	}
    }

    @Override
    public ActionBlock copy() {
	return new Repeat(this);
    }

    public Repeat(int timesToRepeat, ActionSequence sequence) {
	this.mNumTimesToRepeat = timesToRepeat;
	this.mNumRepeatsDone = 0;
	this.mSequence = new ActionSequence(sequence);
	this.mRunningID = mSequence.startInstance();
	this.mIsActive = false;
    }
    
    Repeat(Repeat rhs) {
	this(rhs.mNumTimesToRepeat, rhs.mSequence);
    }
}
    
