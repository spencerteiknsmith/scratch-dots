package main;

import script.trigger.OnTapTrigger;
import script.trigger.OnCollisionTrigger;
import script.actionblock.Translate;

import script.actionblock.Translate.Direction;

import driver.Driver.BadDriverAction;

public class Main {
    driver.Driver driver;

    String buildFile;
    String runFile;
    String binaryInputStr;
    String specialMode;
    
    Main() {
	buildFile = "dots.xml";
	runFile = ".defaultrun.xml";
	binaryInputStr = null;
	specialMode = null;
    }
    
    private void parseArgs(String[] args) {
	for (int i = 0; i < args.length; i++) {
	    if (args[i].charAt(0) == '-') {
		switch (args[i].charAt(1)) {
		case 'b':
		    buildFile = args[++i];
		    break;
		case 'r':
		    runFile = args[++i];
		    break;
		case 'n':
		    binaryInputStr = args[++i];
		    break;
		case 'm':
		    specialMode = args[++i];
		    break;
		}
	    }
	}
    }
    
    private void run(String[] args) {
	parseArgs(args);
	build();
	execute();
    }
    private void build() {
	driver = new xmlreader.XMLBuildReader().readFile(buildFile);
    }
    private void execute() {
	if ("bin2dec".equalsIgnoreCase(specialMode)) {
	    touchBinInput();
	}
	new xmlreader.XMLPlayReader().play(driver, runFile);
    }
    private void touchBinInput() {
	for (int i = 0; i < binaryInputStr.length(); i++) {
	    if (binaryInputStr.charAt(i) == '1') {
		driver.touch(2, 2 + (2 * i));
	    }
	}
    }



    
    public static void main(String[] args) {
	new Main().run(args);
    }
}
