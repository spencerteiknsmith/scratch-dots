package printing;

import java.io.PrintStream;

public class GridPrinter extends java.io.PrintStream {
    private int numRows;
    private int numCols;
    private int cellHeight;
    private int cellWidth;

    private char vertDividerChar;
    private char horizDividerChar;

    private String dividerStyleStr;

    private static final String RESET_STYLE = "\u001b[0m";
    private static final String RIGHT_1 = "\u001b[1C";
    private static final String CLEAR_SCREEN = "\u001b[2J";
    private static final String NEXT_LINE = "\u001b[1E";
    
    private void moveCursorToScreenPos(int y, int x) {
	this.print("\u001b[" + y + ";" + x + "H");
    }

    private int cellRowToScreenY(int r) {
	return (cellHeight + 1) * r + 2;
    }
    private int cellColToScreenX(int c) {
	return (cellWidth + 1) * c + 2;
    }
    
    private void moveCursorToCell(int r, int c) {
	moveCursorToScreenPos(cellRowToScreenY(r), cellColToScreenX(c));
    }

    private void moveCursorBelowGrid() {
	moveCursorToCell(numRows, 0);
	this.print("\u001b[1G");
    }
    private void rememberCursorPos() {
	this.print("\u001b[s");
    }
    private void restoreCursorPos() {
	this.print("\u001b[u");
    }
    
    public void printCell(int r, int c, String contents) {
	rememberCursorPos();
	java.util.List<PrintingChar> chars = PrintingChar.printingCharsOf(contents);
	moveCursorToCell(r, c);

	int rowOfCell = 0;
	int colOfCell = 0;
	int charsIndex = 0;
	for (int y = 0; y < cellHeight; y++) {
	    for (int x = 0; x < cellWidth; x++) {
		if (charsIndex < chars.size()) {
		    this.print(chars.get(charsIndex++));
		}
		else {
		    this.print(' ');
		}
	    }
	    this.format("\u001b[1B\u001b[%dD", cellWidth);
	}
	if (charsIndex != chars.size()) {
	    this.format("\u001b[1A\u001b[%dC*", cellWidth - 1);
	}
	restoreCursorPos();
    }

    public void clearBelow() {
	moveCursorBelowGrid();
	this.print("\u001b[0J");
    }

    public void prepareScreen() {
	this.print(CLEAR_SCREEN);
    }	
    public void printDividers() {
	int numCharsVert = (cellHeight + 1) * numRows + 1;
	int numCharsHoriz = (cellWidth + 1) * numCols + 1;

	moveCursorToScreenPos(1, 1);
	
	
	String right1;
	for (int y = 0; y < numCharsVert; y++) {
	    if ((y % (cellHeight + 1)) == 0) {
		right1 = new StringBuilder()
		    .append(dividerStyleStr)
		    .append(horizDividerChar)
		    .toString();
	    }
	    else {
		right1 = RIGHT_1;
	    }
	    for (int x = 0; x < numCharsHoriz; x++) {
		if ((x % (cellWidth + 1)) == 0) {
		    this.print(dividerStyleStr);
		    this.print(vertDividerChar);
		    this.print(RESET_STYLE);
		}
		else {
		    this.print(right1);
		}
	    }
	    this.print(RESET_STYLE);
	    this.print(NEXT_LINE);
	}
    }
    
    public GridPrinter(int numRowsIn, int numColsIn, int cellHeightIn, int cellWidthIn) {
	this (numRowsIn, numColsIn, cellHeightIn, cellWidthIn, System.out);
    }
    public GridPrinter(int numRowsIn, int numColsIn, int cellHeightIn, int cellWidthIn, java.io.OutputStream outIn) {
	super(outIn);
	numRows = numRowsIn;
	numCols = numColsIn;
	cellHeight = cellHeightIn;
	cellWidth = cellWidthIn;

	vertDividerChar = '|';
	horizDividerChar = '-';
	dividerStyleStr = "\u001b[48;5;239m";
    }

    public void setVertDividerChar(char in) {
	vertDividerChar = in;
    }
    public void setHorizDividerChar(char in) {
	horizDividerChar = in;
    }
    public void setDividerStyleStr(String in) {
	dividerStyleStr = in;
    }
}
    
