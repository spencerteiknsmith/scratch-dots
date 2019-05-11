package script.actionblock;

public interface ActionBlock {
    boolean step();
    ActionBlock copy();
    default String instanceToString() {
	return this.toString();
    }
}
