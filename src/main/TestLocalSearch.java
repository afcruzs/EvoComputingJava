package main;

import experiment.DataHandler;
import experiment.AlgorithmsTesting;
import optimization.StopCondition;
import optimization.localSearch.Annealing;
import optimization.localSearch.Operators;
import optimization.localSearch.HillClimbing;
import math.TestFunctions;
import optimization.OptimizationProblem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestLocalSearch {
	static int iterations = 1000;
	static int population = 50;
	static int n = 10;
	static int experiments = 100;

	static DataHandler writeOutputHandler = new DataHandler() {
		
		@Override
		public void manipulateData(double[][][] data, String dataName) {
                    try {
                        System.setOut(new PrintStream(new File(dataName)));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(TestLocalSearch.class.getName()).log(Level.SEVERE, null, ex);
                    }
			for(int i=0; i<iterations; i++){
				double bestAvg = 0.0;
				double worstAvg = 0.0;
				double avgAvg = 0.0;
                                double desvAvg = 0.0;
				for(int j=0; j<experiments; j++){
					
					double best = Double.MAX_VALUE;
					double worst = Double.MIN_NORMAL;
					double avg = 0.0;
					
					for(int k=0; k<population; k++){
						best = Math.min( data[j][i][k], best );
						worst = Math.max(worst, data[j][i][k]);
						avg += data[j][i][k];
					}
					
					avg = avg / ((double)population);
					
                                        double desv = 0.0;
                                        for(int k=0; k<population; k++){
                                            desv += (data[j][i][k]-avg)*(data[j][i][k]-avg);
                                        }
                                        
                                        desv = desv / ((double)population);
                                        desv = Math.sqrt(desv);		
                                        
                                        bestAvg += best;
					worstAvg += worst;
					avgAvg += avg;
                                        desvAvg += desv;
					
								
					
				}
				bestAvg = bestAvg / ( (double) experiments );
				worstAvg = worstAvg / ( (double) experiments );
				avgAvg = avgAvg / ( (double) experiments );
                                desvAvg = desvAvg / ( (double) experiments );
				
				System.out.println( String.valueOf(bestAvg).replace(".", ",") + "\t" + 
									String.valueOf(avgAvg).replace(".", ",") + "\t" + 
									String.valueOf(worstAvg).replace(".", ",") + "\t" + 
                                                                        ( i % 25 == 0 ? String.valueOf(desvAvg).replace(".", ",") : "" )
                                );
			}
		}
	};
	
	

	static StopCondition stopCondition = new StopCondition() {

		@Override
		public boolean shouldStop(Double best, int iteration) {
			return iteration >= iterations;
		}
	};

	public static void main(String[] args) throws IOException {
		
                experiments = 100;
		n = 2;
		OptimizationProblem rosenbrock = TestFunctions.rosenbrock();
		OptimizationProblem schwefel = TestFunctions.schwefel(n);
		OptimizationProblem rastrigin = TestFunctions.rastrigin(408.0, n);
		OptimizationProblem griewangk = TestFunctions.griewangk(n);
		
		AlgorithmsTesting tester = new AlgorithmsTesting(iterations, experiments, population, n, rosenbrock, writeOutputHandler);
		
		tester.addAlgorithm( new HillClimbing(1.2, 1.1, Operators.powerLogOperator, stopCondition, iterations) );
                tester.addAlgorithm( new HillClimbing(1.7, 1.1, Operators.gaussianOperator, stopCondition, iterations) );
                tester.addAlgorithm( new Annealing(1.7,1.3,Operators.gaussianOperator, stopCondition, 1.4, 1.7, iterations) );
                tester.addAlgorithm( new Annealing(1.3,1.4,Operators.powerLogOperator, stopCondition, 1.3, 1.2, iterations) );
		tester.runAll();
                
                n = 10;
                
                
                tester = new AlgorithmsTesting(iterations, experiments, population, n, schwefel, writeOutputHandler);
		
		tester.addAlgorithm( new HillClimbing(80.3, 10.1, Operators.powerLogOperator, stopCondition, iterations) );
                tester.addAlgorithm( new HillClimbing(40.3, 45.1, Operators.gaussianOperator, stopCondition, iterations) );
                tester.addAlgorithm( new Annealing(1.1,1.4,Operators.gaussianOperator, stopCondition, 1.1, 10.2, iterations) );
                tester.addAlgorithm( new Annealing(1.3,1.4,Operators.powerLogOperator, stopCondition, 1.1, 3.2, iterations) );
		tester.runAll();
                
                tester = new AlgorithmsTesting(iterations, experiments, population, n, rastrigin, writeOutputHandler);
		
		tester.addAlgorithm( new HillClimbing(1.1, 1.1, Operators.powerLogOperator, stopCondition, iterations) );
                tester.addAlgorithm( new HillClimbing(1.3, 1.1, Operators.gaussianOperator, stopCondition, iterations) );
                tester.addAlgorithm( new Annealing(2.3,1.4,Operators.gaussianOperator, stopCondition, 0.1, 13.2, iterations) );
                tester.addAlgorithm( new Annealing(2.3,1.4,Operators.powerLogOperator, stopCondition, 3.1, 13.2, iterations) );
		tester.runAll();
                
		tester = new AlgorithmsTesting(iterations, experiments, population, n, griewangk, writeOutputHandler);
		
		tester.addAlgorithm( new HillClimbing(2.1, 4.1, Operators.powerLogOperator, stopCondition, iterations) );
                tester.addAlgorithm( new HillClimbing(1.1, 2.1, Operators.gaussianOperator, stopCondition, iterations) );
                tester.addAlgorithm( new Annealing(1.3,1.4,Operators.gaussianOperator, stopCondition, 2.1, 33.2, iterations) );
                tester.addAlgorithm( new Annealing(1.3,1.4,Operators.powerLogOperator, stopCondition, 2.1, 33.2, iterations) );
		tester.runAll();
	}
}
