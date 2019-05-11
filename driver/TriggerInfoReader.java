package driver;

class TriggerInfoReader {
    static void read(String info, Builder.CharacterBuilder.ScriptBuilder scriptBuilder) {
	NameAndParams nAndP = new NameAndParams(info);
	if ("ontap".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.setTrigger(new script.trigger.OnTapTrigger());
	    return;
	}
	else if ("oncollision".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.setTrigger(new script.trigger.OnCollisionTrigger());
	}
	else if ("onmessage".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.setTrigger(new script.trigger.OnMessageTrigger(scriptBuilder.outerBuilder().getMessage(nAndP.paramAt(0))));
	}
	else if ("onbutton".equalsIgnoreCase(nAndP.name())) {
	    scriptBuilder.setTrigger(new script.trigger.OnButtonTrigger(scriptBuilder.outerBuilder().getButton(nAndP.paramAt(0))));
	}
    }
}

    
