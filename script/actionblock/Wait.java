package script.actionblock;

public final class Wait implements ActionBlock {
    private final int mNumStepsToWait;
    private int mNumStepsTaken;

    @Override
    public String toString() {
	return String.format("Wait%-2d", mNumStepsToWait);
    }
    
    @Override
    public boolean step() {
	mNumStepsTaken++;
	if (mNumStepsTaken >= mNumStepsToWait) {
	    mNumStepsTaken = 0;
	    return true;
	}
	return false;
    }

    @Override
    public ActionBlock copy() {
	return new Wait(this);
    }

    public Wait(int in) {
	mNumStepsToWait = in;
	mNumStepsTaken = 0;
    }
    
    Wait(Wait rhs) {
	this.mNumStepsToWait = rhs.mNumStepsToWait;
	this.mNumStepsTaken = 0;
    }
}
