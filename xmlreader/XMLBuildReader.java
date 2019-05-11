package xmlreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import driver.Builder.BadBuildAction;

public class XMLBuildReader {
    private driver.Builder builder;

    private XMLEventReader xmlEventReader;

    
    public driver.Driver readFile(String filename) {
	XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
	try {
	    xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filename));

	    while (xmlEventReader.hasNext()) {
		XMLEvent xmlEvent = xmlEventReader.nextEvent();
	    
		if (xmlEvent.isStartElement()) {
		    StartElement startElement = xmlEvent.asStartElement();
		    String name = startElement.getName().getLocalPart();
		    if ("Page".equalsIgnoreCase(name)) {
			return readPage(startElement);
		    }
		}
	    }
	    return builder.finish();
	}
	catch (BadBuildAction e) {
	    System.err.println(e.getMessage());
	    return null;
	}
	catch (FileNotFoundException | XMLStreamException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    private driver.Driver readPage(StartElement pageStartElement) throws XMLStreamException, BadBuildAction {
	Integer numRows = Integer.parseInt(pageStartElement.getAttributeByName(new QName("rows")).getValue());
	Integer numCols = Integer.parseInt(pageStartElement.getAttributeByName(new QName("cols")).getValue());
	builder = new driver.Builder(numRows, numCols);
	
	while (xmlEventReader.hasNext()) {
	    XMLEvent xmlEvent = xmlEventReader.nextEvent();

	    if (xmlEvent.isStartElement()) {
		StartElement startElement = xmlEvent.asStartElement();
		String name = startElement.getName().getLocalPart();		
		if ("Messages".equalsIgnoreCase(name)) {
		    readMessages();
		}
		if ("Buttons".equalsIgnoreCase(name)) {
		    readButtons();
		}
		if ("Character".equalsIgnoreCase(name)) {
		    readCharacter(startElement);
		}
	    }
	    if (xmlEvent.isEndElement()) {
		String name = xmlEvent.asEndElement().getName().getLocalPart();
		if ("Page".equalsIgnoreCase(name)) {
		    return builder.finish();
		}
	    }
	}
	return null;
    }

    private driver.Builder.CharacterBuilder characterBuilder;
    private void readCharacter(StartElement characterStartElement) throws XMLStreamException, BadBuildAction {
	Integer homeRow = Integer.parseInt(characterStartElement.getAttributeByName(new QName("row")).getValue());
	Integer homeCol = Integer.parseInt(characterStartElement.getAttributeByName(new QName("col")).getValue());
	javax.xml.stream.events.Attribute colorAttribute = characterStartElement.getAttributeByName(new QName("color"));
	if (colorAttribute == null) {
	    characterBuilder = builder.startCharacter(homeRow, homeCol);
	}
	else {
	    characterBuilder = builder.startCharacter(homeRow, homeCol, colorAttribute.getValue());
	}
	while (xmlEventReader.hasNext()) {
	    XMLEvent xmlEvent = xmlEventReader.nextEvent();

	    if (xmlEvent.isStartElement()) {
		String name = xmlEvent.asStartElement().getName().getLocalPart();
		if ("Script".equalsIgnoreCase(name)) {
		    readScript();
		}
	    }
	    if (xmlEvent.isEndElement()) {
		String name = xmlEvent.asEndElement().getName().getLocalPart();
		if ("Character".equalsIgnoreCase(name)) {
		    characterBuilder.finishCharacter();
		    return;
		}
	    }
	}
    }
    private driver.Builder.CharacterBuilder.ScriptBuilder scriptBuilder;
    private void readScript() throws XMLStreamException, BadBuildAction {
	scriptBuilder = characterBuilder.startScript();
	while (xmlEventReader.hasNext()) {
	    XMLEvent xmlEvent = xmlEventReader.nextEvent();

	    if (xmlEvent.isStartElement()) {
		String name = xmlEvent.asStartElement().getName().getLocalPart();
		if ("Trigger".equalsIgnoreCase(name)) {
		    xmlEvent = xmlEventReader.nextEvent();
		    scriptBuilder.setTrigger(xmlEvent.asCharacters().getData());
		}
		else if ("DoesRepeat".equalsIgnoreCase(name)) {
		    xmlEvent = xmlEventReader.nextEvent();
		    scriptBuilder.setDoesRepeat(xmlEvent.asCharacters().getData());
		}
		else if ("Sequence".equalsIgnoreCase(name)) {
		    readSequence();
		}
	    }
	    if (xmlEvent.isEndElement()) {
		String name = xmlEvent.asEndElement().getName().getLocalPart();
		if ("Script".equalsIgnoreCase(name)) {
		    scriptBuilder.finishScript();
		    return;
		}
	    }
	}
    }
    private void readSequence() throws XMLStreamException {
	while (xmlEventReader.hasNext()) {
	    XMLEvent xmlEvent = xmlEventReader.nextEvent();

	    if (xmlEvent.isStartElement()) {
		String name = xmlEvent.asStartElement().getName().getLocalPart();
		if ("Block".equalsIgnoreCase(name)) {
		    xmlEvent = xmlEventReader.nextEvent();
		    scriptBuilder.addBlock(xmlEvent.asCharacters().getData());
		}
		else if ("Repeat".equalsIgnoreCase(name)) {	
		    Integer repCount = Integer.parseInt(xmlEvent.asStartElement().getAttributeByName(new QName("count")).getValue());
		    scriptBuilder.startRepeatBlock(repCount);
		}
	    }
	    if (xmlEvent.isEndElement()) {
		String name = xmlEvent.asEndElement().getName().getLocalPart();
		if ("Sequence".equalsIgnoreCase(name)) {
		    return;
		}
		else if ("Repeat".equalsIgnoreCase(name)) {
		    scriptBuilder.endRepeatBlock();
		}
	    }
	}
    }
    private void readMessages() throws XMLStreamException {
	while (xmlEventReader.hasNext()) {
	    XMLEvent xmlEvent = xmlEventReader.nextEvent();

	    if (xmlEvent.isStartElement()) {
		String name = xmlEvent.asStartElement().getName().getLocalPart();
		if ("Message".equalsIgnoreCase(name)) {
		    xmlEvent = xmlEventReader.nextEvent();
		    builder.newMessage(xmlEvent.asCharacters().getData());
		}
	    }
	    if (xmlEvent.isEndElement()) {
		String name = xmlEvent.asEndElement().getName().getLocalPart();
		if ("Messages".equalsIgnoreCase(name)) {
		    return;
		}
	    }
	}
    }
    private void readButtons() throws XMLStreamException {
	while (xmlEventReader.hasNext()) {
	    XMLEvent xmlEvent = xmlEventReader.nextEvent();

	    if (xmlEvent.isStartElement()) {
		String name = xmlEvent.asStartElement().getName().getLocalPart();
		if ("Button".equalsIgnoreCase(name)) {
		    xmlEvent = xmlEventReader.nextEvent();
		    builder.newButton(xmlEvent.asCharacters().getData());
		}
	    }
	    if (xmlEvent.isEndElement()) {
		String name = xmlEvent.asEndElement().getName().getLocalPart();
		if ("Buttons".equalsIgnoreCase(name)) {
		    return;
		}
	    }
	}
    }
}
