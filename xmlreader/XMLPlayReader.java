package xmlreader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLPlayReader {
    private driver.Driver driver;
    private java.io.PrintStream out;

    
    public void play(driver.Driver driverIn, String filename) {
	play(driverIn, filename, System.out);
    }
    public void play(driver.Driver driverIn, String filename, java.io.PrintStream outIn) {
	driver = driverIn;
	out = outIn;
	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	try {
	    SAXParser parser = saxParserFactory.newSAXParser();
	    parser.parse(new File(filename), new DefaultHandler() {
		    @Override
		    public void startElement(String uri, String localName, String qName, Attributes attributes) {
			try {
			    if (qName.equalsIgnoreCase("step")) {
				int numSteps = 1;
				String countStr = attributes.getValue("count");
				if (countStr != null) {
				    numSteps = Integer.parseInt(countStr);
				}
				for (int i = 0; i < numSteps; i++) {
				    driver.step();
				}
			    }
			    else if (qName.equalsIgnoreCase("touch")) {
				int y = Integer.parseInt(attributes.getValue("y"));
				int x = Integer.parseInt(attributes.getValue("x"));
				driver.touch(y, x);
			    }
			    else if (qName.equalsIgnoreCase("finish")) {
				while (driver.hasActivity()) {
				    driver.step();
				}
			    }
			    else if (qName.equalsIgnoreCase("print")) {
				out.println(driver);
			    }
			    else if (qName.equalsIgnoreCase("stepbystep")) {
				try {
				    int key = 0;
				    while (key != 113) {
					out.println("=================================");
					out.println("=================================");
					out.println("=================================");
					out.println(driver);
					driver.step();
					key = System.in.read();
				    }
				}
				catch (IOException e) {
				    System.err.println("Error with input in stepbystep");
				    return;
				}
			    }
			    else if (qName.equalsIgnoreCase("visualfinish")) {
				try {
				    int millisPerStep = Integer.parseInt(attributes.getValue("millis"));
				    driver.prepareGridPrinter();
				    driver.updateGridPrint();
				    while (driver.hasActivity()) {
					Thread.sleep(millisPerStep);
					driver.step();
					driver.updateGridPrint();
				    }
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
			    }
				
			} catch (driver.Driver.BadDriverAction e) {
			    System.out.println(e.getMessage());
			}
		    }
		}
		);
	}
	catch (ParserConfigurationException | SAXException | IOException e) {
	    e.printStackTrace();
	}
    }
}
