package printing;

class PrintingChar {
    private final String contents;

    @Override
    public String toString() {
	return contents;
    }
    
    private PrintingChar(String in) {
	contents = in;
    }
    
    static java.util.List<PrintingChar> printingCharsOf(String text) {
	java.util.List<PrintingChar> res = new java.util.ArrayList<PrintingChar>();

	boolean escaping = false;
	boolean inCSISequence = false;
	String currPiece = "";
	for (char c : text.toCharArray()) {
	    currPiece += c;
	    if (escaping) {
		escaping = false;
		if (c == '[') {
		    inCSISequence = true;
		}
	    }
	    else if (inCSISequence) {
		if (c >= '@' && c <= '~') {
		    inCSISequence = false;
		}
	    }
	    else if (c == '\u001b') {
		escaping = true;
	    }
	    else {
		res.add(new PrintingChar(currPiece));
		currPiece = "";
	    }
	}

	return res;
    }
}
	
	
