package script.actionblock;

public final class StopScripts extends DotCharacterAffectingAction {
    @Override
    public String toString() {
	return " Stop ";
    }

    @Override
    public boolean step() {
	mDotCharacter.stopScripts();
	return true;
    }

    @Override
    public ActionBlock copy() {
	return new StopScripts(this);
    }

    public StopScripts(page.DotCharacter character) {
	super(character);
    }
    StopScripts(StopScripts rhs) {
	super(rhs);
    }
}
