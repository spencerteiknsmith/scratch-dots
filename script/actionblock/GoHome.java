package script.actionblock;

public final class GoHome extends DotCharacterMovement {
    @Override
    public String toString() {
	return "GoHome";
    }

    @Override
    public boolean step() {
	mGrid.moveHome(mDotCharacter);
	return true;
    }

    @Override
    public ActionBlock copy() {
	return new GoHome(this);
    }

    public GoHome(page.DotCharacter character, page.Grid grid) {
	super(character, grid);
    }
    GoHome(GoHome rhs) {
	super(rhs);
    }
}
