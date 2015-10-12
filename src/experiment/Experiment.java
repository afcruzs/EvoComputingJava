package experiment;

import optimization.OptimizationProblem;
import optimization.OptimizationStrategy;
import java.util.ArrayList;
import java.util.List;
import optimization.OptimizationProblem;
import optimization.OptimizationStrategy;


public class Experiment {
	double data[][][];
	int currentExperiment;
	OptimizationStrategy algorithm;
	OptimizationProblem problem;
	int experiments,population,iterations;
	List< List<Double[]> > initialPoints;
	int dimension;
	
	public Experiment( int iterations, int population, int experiments, int dimension ) {
		data = new double[experiments][iterations][population];
		this.experiments = experiments;
		this.iterations = iterations;
		this.population = population;
		this.dimension = dimension;
		initialPoints = new ArrayList<List<Double[]>>( experiments );
	}
	
	public void setData( int iteration, int individual, double value ){
		data[currentExperiment][iteration][individual] = value;
	}
	
	
	public void setAlgorithm(OptimizationStrategy algorithm){
		this.algorithm = algorithm;
	}
	
	public void setProblem(OptimizationProblem problem){
		this.problem = problem;
	}
	
	public void setInitialPoints( List< List<Double[]> > initialPoints ){
		this.initialPoints = initialPoints;
	}
	
	public void runExperiments(){
//		System.out.println("EXP");
                String name = problem + " " + algorithm;
		for(currentExperiment =0; currentExperiment<experiments; currentExperiment++){
//                    System.out.println("JEJE" + currentExperiment);
//			System.out.println(currentExperiment + " " + name );
			algorithm.setInitialPopulation( initialPoints.get(currentExperiment) );
			algorithm.setExperimentHandler(this);
			algorithm.optimize(problem);
		}
	}

	public double[][][] getData() {
		return data;
	}
}


