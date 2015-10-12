package optimization.localSearch;

import math.StdRandom;
import experiment.Experiment;
import optimization.OptimizationProblem;
import optimization.OptimizationStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import optimization.StopCondition;

public class Annealing implements OptimizationStrategy {
	private double delta, sigma;
	private NeighborhoodOperator operator;
	private List<Double[]> initialPoints;
	private StopCondition stopCondtion;
	private double TMax, tau;
	private int iterations;
	private Experiment experiment;

	public Annealing(double delta, double sigma, NeighborhoodOperator operator,
			List<Double[]> initialPoints, StopCondition stopCondtion,
			double TMax, double tau, int iterations) {
		this.delta = delta;
		this.sigma = sigma;
		this.operator = operator;
		this.initialPoints = initialPoints;
		this.stopCondtion = stopCondtion;
		this.TMax = TMax;
		this.tau = tau;
		this.iterations = iterations;
	}

	public Annealing(double delta, double sigma, NeighborhoodOperator operator,
			StopCondition stopCondtion, double TMax, double tau, int iterations) {
		this.delta = delta;
		this.sigma = sigma;
		this.operator = operator;
		this.stopCondtion = stopCondtion;
		this.TMax = TMax;
		this.tau = tau;
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
			Double worstValue = null;
			Double average = 0.0;

			for (int i = 0; i < initialPoints.size(); i++) {
				Double guess[] = initialPoints.get(i);

				if (veryBest == null) {
					veryBestPoint = initialPoints.get(i);
					veryBest = problem.evaluate(veryBestPoint);
				}

				Double tmp[] = operator.getNeighbor(guess, delta);
				// or random.uniform(0,1) <= exp(-tau*it/T_max)
				if (problem.satisfy(tmp)
						&& (problem.evaluate(tmp) <= problem.evaluate(guess) || StdRandom.uniform(0.0, 1.0) <= Math.exp(-tau * ((double) it)
								/ TMax))) {

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

				Double currentEvaluation = problem.evaluate(initialPoints
						.get(i));
				if( experiment != null )
					experiment.setData(it, i, currentEvaluation);

				if (theBest == null || theBest > currentEvaluation)
					theBest = currentEvaluation;

				if (worstValue == null || worstValue < currentEvaluation)
					worstValue = currentEvaluation;

				average += currentEvaluation;

				if (veryBest >= currentEvaluation) {
					veryBestPoint = initialPoints.get(i);
					veryBest = problem.evaluate(veryBestPoint);
				}
			}

			it++;
		}
		
		delta = xd;

		
//		System.out.println( Arrays.toString(veryBestPoint) );
//		System.out.println( veryBest );
//		System.out.println(); 
		return veryBest;
	}




	@Override
	public void setInitialPopulation(List<Double[]> population) {
		if (initialPoints != null)
			initialPoints.clear();
		initialPoints = new ArrayList<Double[]>(population.size());

		for (int i = 0; i < population.size(); i++) {
			Double tmp[] = new Double[population.get(i).length];
			System.arraycopy(population.get(i), 0, tmp, 0, tmp.length);
			initialPoints.add(tmp);
		}
	}

	@Override
	public String toString() {
		return "Annealing [delta=" + delta + ", sigma=" + sigma + ", operator="
				+ operator + ", TMax=" + TMax + ", tau=" + tau
				+ ", iterations=" + iterations + "]";
	}

	@Override
	public void setExperimentHandler(Experiment experiment) {
		this.experiment = experiment;
	}
	

}
