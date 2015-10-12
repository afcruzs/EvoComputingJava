package optimization;


public interface StopCondition {
	boolean shouldStop( Double best, int iteration );
}
