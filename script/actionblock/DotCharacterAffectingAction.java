package script.actionblock;

abstract class DotCharacterAffectingAction implements ActionBlock {
    protected final page.DotCharacter mDotCharacter;

    DotCharacterAffectingAction(DotCharacterAffectingAction rhs) {
	mDotCharacter = rhs.mDotCharacter;
    }
    DotCharacterAffectingAction(page.DotCharacter character) {
	mDotCharacter = character;
    }
}
