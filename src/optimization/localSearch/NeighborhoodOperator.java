package optimization.localSearch;



public interface NeighborhoodOperator {
	Double [] getNeighbor( Double x[], Double delta );
}
