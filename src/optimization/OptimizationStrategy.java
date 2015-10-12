package optimization;

import experiment.Experiment;
import java.util.List;



public interface OptimizationStrategy {
	Double optimize( OptimizationProblem  problem );
	void setInitialPopulation( List<Double[]> population );
	void setExperimentHandler( Experiment experiment );
}
