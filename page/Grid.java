package page;

import java.util.HashMap;

public final class Grid {
    public class Coord {
	private int mY;
	private int mX;
	private void setY(int y) {
	    mY = Math.floorMod(y, mNumRows);
	}
	private void setX(int x) {
	    mX = Math.floorMod(x, mNumCols);
	}
	public int getY() {
	    return mY;
	}
	public int getX() {
	    return mX;
	}
	Coord (int y, int x) {
	    setY(y);
	    setX(x);
	}
	Coord copy() {
	    return new Coord(this.mY, this.mX);
	}
	@Override
	public int hashCode() {
	    return Grid.this.hashCode() * 31 + mY * mNumCols + mX;
	}
	@Override
	public boolean equals(Object o) {
	    if (!(o instanceof Coord)) {
		return false;
	    }
	    Coord ocoord = (Coord)o;
	    return Grid.this == ocoord.getGrid()
		&& this.mY == ocoord.mY
		&& this.mX == ocoord.mX;
	}
	private Grid getGrid() {
	    return Grid.this;
	}
	Coord addVert(int mag) {
	    Coord res = this.copy();
	    res.setY(res.mY + mag);
	    return res;
	}
	Coord addHoriz(int mag) {
	    Coord res = this.copy();
	    res.setX(res.mX + mag);
	    return res;
	}
    }

    private final int mNumCols;
    private final int mNumRows;
    private final Cell[][] mGrid;
    private final HashMap<DotCharacter, Coord> mLocs;
    private HashMap<Coord, String> mChanges;
    
    @Override
    public String toString() {
	String[][] strings = new String[mNumRows][mNumCols];
	int maxWidth = 8; //This accomodates three characters nicely
	//TODO? Calc a real width

	String goFarLeft = "\u001b[" + (maxWidth * (mNumCols + 1)) + "D";
	final String goOneRight = "\u001b[" + maxWidth + "C";
	String currGoRight = "";
	StringBuilder res = new StringBuilder();
	for (int y = 0; y < mNumRows; y++) {
	    currGoRight = "";
	    for (int x = 0; x < mNumCols; x++) {
		res.append(goFarLeft).append(currGoRight);
		currGoRight += goOneRight;
		res.append(String.valueOf(mGrid[y][x]));
	    }
	    res.append("\n\n");
	}
	return res.toString();
    }
    
    Grid(int r, int c) {
	mGrid = new Cell[r][c];
	mNumRows = r;
	mNumCols = c;
	mLocs = new HashMap<DotCharacter, Coord>();
	mChanges = new HashMap<>();
    }

    private void init(Coord coord) {
	mGrid[coord.mY][coord.mX] = new Cell();
    }
    private void logCellChange(Coord coord) {
	mChanges.put(coord.copy(), this.at(coord).toString());
    }
    void place(Coord coord, DotCharacter character) {
	if (this.at(coord) == null) {
	    init(coord);
	}
	this.at(coord).add(character);
	mLocs.put(character, coord.copy());
	logCellChange(coord);
    }
    Cell at(Coord coord) {
	return mGrid[coord.mY][coord.mX];
    }

    public void moveHome(DotCharacter character) {
	move(character, character.getHomeCoord());
    }
    public void moveVert(DotCharacter character, int mag) {
	move(character, mLocs.get(character).addVert(mag));
    }
    public void moveHoriz(DotCharacter character, int mag) {
	move(character, mLocs.get(character).addHoriz(mag));
    }
	
    private void move(DotCharacter character, Coord coord) {
	Coord orig = mLocs.get(character);
	this.at(orig).remove(character);
	logCellChange(orig);
	place(coord, character);
    }

    public HashMap<Coord, String> flushChanges() {
	HashMap<Coord, String> res = mChanges;
	mChanges = new HashMap<>();
	return res;
    }

    void addCollisionEvents(event.EventManager eManager) {
	for (int y = 0; y < mNumRows; y++) {
	    for (int x = 0; x < mNumCols; x++) {
		Cell cell = this.at(new Coord(y, x));
		if (cell != null && cell.hasCollision()) {
		    eManager.addEvent(new event.Collision(y, x));
		}
	    }
	}
    }
    void sendOutLocalEvents(event.EventManager eManager) {
	for (event.Event e : eManager) {
	    if (e instanceof event.LocalEvent) {
		event.LocalEvent le = (event.LocalEvent)e;
		Cell cell = this.at(new Coord(le.getY(), le.getX()));
		if (cell != null) {
		    for (DotCharacter c : mGrid[le.getY()][le.getX()]) {
			c.receiveEvent(e);
		    }
		}
	    }
	}
    }
}
