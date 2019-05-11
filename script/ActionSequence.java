package script;

import java.util.ArrayList;
import java.util.TreeMap;
import script.actionblock.ActionBlock;

public class ActionSequence {
    private class ActiveInstance {
	private final ArrayList<ActionBlock> mCopyList;
	private int mActiveIndex;

	ActiveInstance() {
	    mCopyList = new ArrayList<ActionBlock>();
	    for (ActionBlock master : mMasterList) {
		mCopyList.add(master.copy());
	    }
	    mActiveIndex = 0;
	}

	void restart() {
	    mActiveIndex = 0;
	}
	boolean step() {
	    if (mActiveIndex >= mCopyList.size()) {
		return true;
	    }
	    
	    if (mCopyList.get(mActiveIndex).step()) {
		mActiveIndex++;
	    }
	    return mActiveIndex >= mCopyList.size();
	}

	@Override
	public String toString() {
	    StringBuilder res = new StringBuilder();
	    res.append("[");
	    for (int i = 0; i < mCopyList.size(); i++) {
		if (i == mActiveIndex) {
		    res.append("\u001b[7m");
		}
		res.append(" " + mCopyList.get(i).instanceToString() + " ");
		if (i == mActiveIndex) {
		    res.append("\u001b[0m");
		}
	    }
	    res.append("]");
	    return res.toString();
	}
    }

    public static class ActiveID implements Comparable<ActiveID> {
	static int nextID = 0;
	private final int id;
	ActiveID() {
	    id = nextID++;
	}
	@Override
	public int compareTo(ActiveID rhs) {
	    return Integer.compare(this.id, rhs.id);
	}
    }
    
    private final ArrayList<ActionBlock> mMasterList;
    private final TreeMap<ActiveID, ActiveInstance> mActives;

    @Override
    public String toString() {
	StringBuilder res = new StringBuilder();
	res.append("[");
	for (ActionBlock ab : mMasterList) {
	    res.append(" " + ab + " ");
	}
	res.append("]");
	return res.toString();
    }

    public String instanceToString(ActiveID id) {
	return mActives.get(id).toString();
    }

    public ActionSequence() {
	mMasterList = new ArrayList<ActionBlock>();
	mActives = new TreeMap<ActiveID, ActiveInstance>();
    }
    public ActionSequence(ActionSequence rhs) {
	this(rhs.mMasterList);
    }
    public ActionSequence(ArrayList<ActionBlock> masterList) {
	this();
	for (ActionBlock block : masterList) {
	    mMasterList.add(block.copy());
	}
    }

    public void addActionBlock(ActionBlock block) {
	mMasterList.add(block.copy());
    }
    
    public boolean step(ActiveID id) {
	return mActives.get(id).step();
    }
    public void restart(ActiveID id) {
	mActives.get(id).restart();
    }
    public void terminate(ActiveID id) {
	mActives.remove(id);
    }
    public void stop() {
	mActives.clear();
    }
    public ActiveID startInstance() {
	ActiveID id = new ActiveID();
	mActives.put(id, new ActiveInstance());
	return id;
    }
}
