/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import experiment.AlgorithmsTesting;
import experiment.DataHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import math.TestFunctions;
import optimization.OptimizationProblem;
import optimization.evolutionaryStrategies.SketchEvoAlgorithm;


/**
 *
 * @author PC
 */
public class TestEvolutionaryStrategies {
    
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
	
    
    
    public static void main(String[] args) throws IOException {
	
                
        
                experiments = 100;
		n = 2;
		OptimizationProblem rosenbrock = TestFunctions.rosenbrock();
		OptimizationProblem schwefel = TestFunctions.schwefel(n);
		OptimizationProblem rastrigin = TestFunctions.rastrigin(418.0, n);
		OptimizationProblem griewangk = TestFunctions.griewangk(n);
		
                
		AlgorithmsTesting tester = new AlgorithmsTesting(iterations, experiments, population, n, rosenbrock, writeOutputHandler);
		tester.addAlgorithm( new EvolutionaryAlgorithmAdapter(
                        SketchEvoAlgorithm.roulette, 
                        SketchEvoAlgorithm.singlePoint, 
                        SketchEvoAlgorithm.singleBitMutation,
                        iterations) );
		tester.runAll();
                
                n = 10;
                
                
                tester = new AlgorithmsTesting(iterations, experiments, population, n, schwefel, writeOutputHandler);
                tester.addAlgorithm( new EvolutionaryAlgorithmAdapter(
                        SketchEvoAlgorithm.roulette, 
                        SketchEvoAlgorithm.singlePoint, 
                        SketchEvoAlgorithm.singleBitMutation,
                        iterations) );
		tester.runAll();
                
                
                tester = new AlgorithmsTesting(iterations, experiments, population, n, rastrigin, writeOutputHandler);
                tester.addAlgorithm( new EvolutionaryAlgorithmAdapter(
                        SketchEvoAlgorithm.roulette, 
                        SketchEvoAlgorithm.singlePoint, 
                        SketchEvoAlgorithm.singleBitMutation,
                        iterations) );
		tester.runAll();
                
                tester = new AlgorithmsTesting(iterations, experiments, population, n, griewangk, writeOutputHandler);
                tester.addAlgorithm( new EvolutionaryAlgorithmAdapter(
                        SketchEvoAlgorithm.roulette, 
                        SketchEvoAlgorithm.singlePoint, 
                        SketchEvoAlgorithm.singleBitMutation,
                        iterations) );
		tester.runAll();
//                
//                tester = new AlgorithmsTesting(iterations, experiments, population, n, rastrigin, writeOutputHandler);
//                tester.addAlgorithm( new EvolutionaryAlgorithmAdapter(evoAlgorihtm) );
//		tester.runAll();
//                
//		tester = new AlgorithmsTesting(iterations, experiments, population, n, griewangk, writeOutputHandler);
//                tester.addAlgorithm( new EvolutionaryAlgorithmAdapter(evoAlgorihtm) );
//		tester.runAll();
	}
}
