package optimization.localSearch;

import math.TestFunctions;
import experiment.Experiment;
import optimization.OptimizationProblem;
import optimization.OptimizationStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import optimization.StopCondition;

public class HillClimbing implements OptimizationStrategy {

	private double delta, sigma;
	private final NeighborhoodOperator operator;
	private List<Double[]> initialPoints;
	private final StopCondition stopCondtion;
	private final int iterations;
	private Experiment experiment;

	public HillClimbing(double delta, double sigma,
			NeighborhoodOperator operator, List<Double[]> initialPoints,
			StopCondition stopCondtion, int iterations) {
		this.delta = delta;
		this.sigma = sigma;
		this.operator = operator;
		this.initialPoints = initialPoints;
		this.stopCondtion = stopCondtion;
		this.iterations = iterations;
	}

	public HillClimbing(double delta, double sigma,
			NeighborhoodOperator operator, StopCondition stopCondtion,
			int iterations) {
		this.delta = delta;
		this.sigma = sigma;
		this.operator = operator;
		this.stopCondtion = stopCondtion;
		this.iterations = iterations;
	}

	@Override
	public Double optimize(OptimizationProblem problem) {

		for (Double x[] : initialPoints) {
			if (problem.satisfy(x) == false) {
				throw new IllegalStateException(Arrays.toString(x)
						+ " does not satisfy one or more restrictions");
			}
		}

		int it = 0;
		double cont = 0.0;
		double gMax = 100.0;
		double g2 = 0.0;
		Double veryBest = null;
		Double veryBestPoint[] = null;

		double xd = delta;
		while (stopCondtion.shouldStop(veryBest, it) == false) {
			Double theBest = null;
			for (int i = 0; i < initialPoints.size(); i++) {
				Double guess[] = initialPoints.get(i);

				if (veryBest == null) {
					veryBestPoint = initialPoints.get(i);
					veryBest = problem.evaluate(veryBestPoint);
				}

				Double tmp[] = operator.getNeighbor(guess, delta);
				if (problem.satisfy(tmp)
						&& problem.evaluate(tmp) <= problem.evaluate(guess)) {
					initialPoints.set(i, tmp);
					g2 = g2 + 1;
				}

				cont++;
				if (cont == gMax) {
					if (g2 / gMax != 0.2) {
						if (g2 / gMax < 0.2)
							delta = delta / sigma;
						else
							delta = delta * sigma;
						cont = 0;
						g2 = 0;
					}
				}
				
				Double currentEvaluation = problem.evaluate(initialPoints.get(i));
				if( experiment != null )
					experiment.setData(it, i, currentEvaluation);
//				System.out.print(currentEvaluation + " ");
				// System.out.println( currentEvaluation );
				if (veryBest >= currentEvaluation) {
					veryBestPoint = initialPoints.get(i);
					veryBest = problem.evaluate(veryBestPoint);
				}

				if (theBest == null || theBest > currentEvaluation)
					theBest = currentEvaluation;
				
				
			}
//			System.out.println();

			it++;
		}
		
//		System.out.println( Arrays.toString(veryBestPoint) );
//		System.out.println( veryBest );
		delta = xd;
		return veryBest;
	}

//	
//
//	public static void main(String[] args) {
//
//		int n = 10;
//		final int iterations = 10;
//		int population = 3;
//
//		OptimizationProblem easy = TestFunctions.rastrigin(10, n);
//
//		StopCondition stopCondition = new StopCondition() {
//
//			@Override
//			public boolean shouldStop(Double best, int iteration) {
//				return iteration >= iterations;
//			}
//		};
//
//		HillClimbing hill = new HillClimbing(1, 1, Operators.gaussianOperator,
//				easy.randomPoints(population, n), stopCondition, iterations);
//
//		System.out.println(hill.optimize(easy));
//	}

	@Override
	public void setInitialPopulation(List<Double[]> population) {
		if( initialPoints != null )
			initialPoints.clear();
		
		initialPoints = new ArrayList<Double[]>( population.size() );
		
		for( int i=0; i<population.size(); i++ ){
			Double tmp[] = new Double[ population.get(i).length ];
			System.arraycopy(population.get(i), 0, tmp, 0, tmp.length);
			initialPoints.add(tmp);
		}
	}

	@Override
	public String toString() {
		return "HillClimbing [delta=" + delta + ", sigma=" + sigma
				+ ", operator=" + operator + ", iterations=" + iterations + "]";
	}

	@Override
	public void setExperimentHandler(Experiment experiment) {
		this.experiment = experiment;
	}
	
	static int xd = 1000;
	static StopCondition stopCondition = new StopCondition() {

		@Override
		public boolean shouldStop(Double best, int iteration) {
			return iteration >= xd;
		}
	};
	
	public static void main(String[] args) {
		 HillClimbing hill = new HillClimbing(1, 1.1, Operators.gaussianOperator, stopCondition, xd);
		 OptimizationProblem rosenbrock = TestFunctions.griewangk(10);
		 hill.setInitialPopulation(rosenbrock.randomPoints(50, 10));
		 System.out.println( hill.optimize(rosenbrock) );
	}
}
