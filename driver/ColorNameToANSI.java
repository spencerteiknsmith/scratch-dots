package driver;

import java.util.Map;
import java.util.HashMap;

class ColorNameToANSI {
    static final Map<String, String> theMap;
    static {
	Map<String, String> aMap = new HashMap<>();
	aMap.put("black", "\u001b[30m");
	aMap.put("red", "\u001b[31m");
	aMap.put("green", "\u001b[32m");
	aMap.put("yellow", "\u001b[33m");
	aMap.put("blue", "\u001b[34m");
	aMap.put("magenta", "\u001b[35m");
	aMap.put("cyan", "\u001b[36m");
	aMap.put("white", "\u001b[37m");

	aMap.put("backblack", "\u001b[40m");
	aMap.put("backred", "\u001b[41m");
	aMap.put("backgreen", "\u001b[42m");
	aMap.put("backyellow", "\u001b[44m");
	aMap.put("backblue", "\u001b[44m");
	aMap.put("backmagenta", "\u001b[45m");
	aMap.put("backcyan", "\u001b[46m");
	aMap.put("backwhite", "\u001b[47m");

	theMap = java.util.Collections.unmodifiableMap(aMap);
    };

    static String ansiOf(String fullName) {
	if (fullName.charAt(0) == '#') {
	    String rest = fullName.substring(1);
	    String[] parts = rest.split(",");
	    String res = "";
	    for (String part : parts) {
		res += "\u001b[" + fullName.substring(1) + "m";
	    }
	    return res;
	}
	String lowerFullName = fullName.toLowerCase();
	String[] names = lowerFullName.split("[^\\p{Alpha}]+");

	String ansiRes = "";
	for (String name : names) {
	    String ansi = theMap.get(name);
	    if (ansi != null) {
		ansiRes += ansi;
	    }
	}
	return ansiRes;
    }
}
