package script.actionblock;

import page.Grid;

abstract class DotCharacterMovement extends DotCharacterAffectingAction {
    protected final Grid mGrid;

    DotCharacterMovement(DotCharacterMovement rhs) {
	super(rhs);
	mGrid = rhs.mGrid;
    }
    DotCharacterMovement(page.DotCharacter character, Grid grid) {
	super(character);
	mGrid = grid;
    }
}
