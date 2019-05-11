package script.actionblock;

public final class Translate extends DotCharacterMovement {
    public enum Direction {
	LEFT("Left"),
	RIGHT("Riht"),
	UP("  Up"),
	DOWN("Down");

	private final String fourLetterName;
	Direction(String string) {
	    this.fourLetterName = string;
	}
	@Override
	public String toString() {
	    return fourLetterName;
	}
    }

    private final Direction mDirection;
    private final int mNumStepsToTake;
    private int mNumStepsTaken;

    @Override
    public String toString() {
	return String.format("%4s%-2d",
			     mDirection.toString(),
			     mNumStepsToTake);
    }
    
    @Override
    public boolean step() {
	translateOneCell();
	return (++mNumStepsTaken >= mNumStepsToTake);
    }

    private void translateOneCell() {
	switch (mDirection) {
	case LEFT:
	    mGrid.moveHoriz(mDotCharacter, -1);
	    break;
	case RIGHT:
	    mGrid.moveHoriz(mDotCharacter, 1);
	    break;
	case UP:
	    mGrid.moveVert(mDotCharacter, -1);
	    break;
	case DOWN:
	    mGrid.moveVert(mDotCharacter, 1);
	    break;
	}
    }

    @Override
    public ActionBlock copy() {
	return new Translate(this);
    }

    public Translate(page.DotCharacter character, page.Grid grid,
		     Direction direction, int numSteps) {
	super(character, grid);
	this.mDirection = direction;
	this.mNumStepsToTake = numSteps;
	this.mNumStepsTaken = 0;
    }
    Translate(Translate rhs) {
	super(rhs);
	this.mDirection = rhs.mDirection;
	this.mNumStepsToTake = rhs.mNumStepsToTake;
	this.mNumStepsTaken = 0;
    }
}
