package script;

import script.trigger.Trigger;

public class Script {
    private final Trigger mTrigger;
    private final ActionSequence mSequence;
    private final boolean mDoesRepeat;
    private final java.util.SortedSet<ActionSequence.ActiveID> mActiveIDs;

    @Override
    public String toString() {
	StringBuilder res = new StringBuilder();
	res
	    .append("Trigger: ").append(mTrigger)
	    .append(", Sequence: ")
	    .append(mSequence);
	if (mDoesRepeat) {
	    res.append(",  (Repeats)");
	}
	res.append("\n");
	for (ActionSequence.ActiveID aid : mActiveIDs) {
	    res.append(mSequence.instanceToString(aid)).append("\n");
	}
	return res.toString();
    }
    
    public Script(Trigger trigger, ActionSequence sequence) {
	this(trigger, sequence, false);
    }
    public Script(Trigger trigger, ActionSequence sequence, boolean doesRepeat) {
	mTrigger = trigger;
	mSequence = new ActionSequence(sequence);
	mDoesRepeat = doesRepeat;
	mActiveIDs = new java.util.TreeSet<ActionSequence.ActiveID>();
    }

    public boolean hasActiveInstance() {
	return !mActiveIDs.isEmpty();
    }

    public void step() {
	java.util.HashSet<ActionSequence.ActiveID> terminated = new java.util.HashSet<ActionSequence.ActiveID>();
	for (ActionSequence.ActiveID id : mActiveIDs) {
	    if (mSequence.step(id)) {
		if (mDoesRepeat) {
		    mSequence.restart(id);
		}
		else {
		    mSequence.terminate(id);
		    terminated.add(id);
		}
	    }
	}
	for (ActionSequence.ActiveID id : terminated) {
	    mActiveIDs.remove(id);
	}
    }

    public void stop() {
	mSequence.stop();
	mActiveIDs.clear();
    }

    public void receiveEvent(event.Event e) {
	if (mTrigger.isTriggeredBy(e)) {
	    mActiveIDs.add(mSequence.startInstance());
	}
    }
}
