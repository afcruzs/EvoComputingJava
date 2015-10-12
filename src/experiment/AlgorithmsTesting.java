package experiment;

import optimization.OptimizationProblem;
import optimization.OptimizationStrategy;
import java.util.ArrayList;
import java.util.List;


public class AlgorithmsTesting {
	private Experiment experiment;
	private ArrayList< OptimizationStrategy > algorithms;
	private OptimizationProblem problem;
	private DataHandler handler;
	
	public AlgorithmsTesting(int iterations, int experiments, int population, int dimension, OptimizationProblem problem,
			DataHandler handler){
		experiment = new Experiment(iterations, population, experiments, dimension);
		algorithms = new ArrayList<>();
		this.problem = problem;
		this.handler = handler;
		List< List<Double []> > commonInitialPoints = new ArrayList<>(iterations);
		for(int i=0; i<experiments; i++)
			commonInitialPoints.add( problem.randomPoints(population, dimension) );
		experiment.setInitialPoints(commonInitialPoints);
	}
	
	public void addAlgorithm( OptimizationStrategy algorithm ){
		algorithms.add(algorithm);
	}
	
	public void runAll(){
		for( OptimizationStrategy algorithm : algorithms ){
                        
			experiment.setAlgorithm(algorithm);
			experiment.setProblem(problem);
			experiment.runExperiments();
			handler.manipulateData( experiment.getData(), problem.toString() + " " + algorithm + ".txt" );
		}
	}
	
}
