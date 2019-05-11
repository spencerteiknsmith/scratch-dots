package driver;

class BlockInfoReader {
    static void read(String info, driver.Builder.CharacterBuilder.ScriptBuilder scriptBuilder) {
	NameAndParams nAndP = new NameAndParams(info);
	if ("gohome".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.addGoHomeBlock();
	    return;
	}
	else if ("stopscripts".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.addStopCharacterScriptsBlock();
	    return;
	}
	else if ("translate".equalsIgnoreCase(nAndP.name())) {
	    String directionStr = nAndP.paramAt(0).toUpperCase();
	    Integer magnitude = Integer.parseInt(nAndP.paramAt(1));
	    scriptBuilder.addTranslateBlock(script.actionblock.Translate.Direction.valueOf(directionStr), magnitude);
	    return;
	}
	else if ("sendmessage".equalsIgnoreCase(nAndP.name())) {
	    Builder builder = scriptBuilder.outerBuilder();
	    event.EventManager manager = builder.getPage().getEventManager();
	    Message message = builder.getMessage(nAndP.paramAt(0));
	    scriptBuilder.addBlock(new script.actionblock.SendMessage(manager, message));
	}
	else if ("wait".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.addBlock(new script.actionblock.Wait(Integer.parseInt(nAndP.paramAt(0))));
	}
	else if ("speak".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.addBlock(new script.actionblock.Speak(nAndP.fullParams()));
	}
    }
}

    
