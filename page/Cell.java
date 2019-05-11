package page;

import java.util.HashSet;

class Cell implements Iterable<DotCharacter> {
    private HashSet<DotCharacter> mOccupants;

    @Override
    public String toString() {
	if (mOccupants.isEmpty()) {
	    return "";
	}
	else {
	    return mOccupants.toString().replaceFirst("\\[", "").replace("]", "");
	}
    }
    
    @Override
    public java.util.Iterator<DotCharacter> iterator() {
	return mOccupants.iterator();
    }

    Cell() {
	mOccupants = new HashSet<DotCharacter>();
    }
    void add(DotCharacter c) {
	mOccupants.add(c);
    }
    void remove(DotCharacter c) {
	mOccupants.remove(c);
    }

    boolean hasCollision() {
	return mOccupants.size() > 1;
    }
}
