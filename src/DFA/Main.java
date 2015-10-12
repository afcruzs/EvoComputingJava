/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DFA;

import java.util.ArrayList;
import java.util.HashMap;
import math.StdRandom;
import optimization.evolutionaryStrategies.EvolutionaryAlgorithm;
import optimization.evolutionaryStrategies.Individual;
import optimization.evolutionaryStrategies.Test;

/**
 *
 * @author PC
 */
public class Main {
    
    static EvolutionaryAlgorithm.SelectionOperator<DFA,Integer> selection = new EvolutionaryAlgorithm.SelectionOperator<DFA, Integer>() {

        private double cumulatedProbability[];
        private final HashMap<Integer,Integer> idxs = new HashMap<>();
        
        
        
        @Override
        public void select(ArrayList<Individual<DFA, Integer>> population, ArrayList<Individual<DFA, Integer>> buffer) {
            if( cumulatedProbability == null || cumulatedProbability.length < population.size() )
                cumulatedProbability = new double[population.size()];
                
            int i = 0;
            for(Individual<DFA, Integer> individual : population){
                cumulatedProbability[i] = -1.0 * individual.getFitness();
                i++;
            }
            
            
            
            double extra = Math.abs(cumulatedProbability[ cumulatedProbability.length - 1 ]) + Math.abs(cumulatedProbability[0]);
            for(i=0; i<cumulatedProbability.length; i++) cumulatedProbability[i] += extra;
            
           
            //System.out.println("HEU " + cumulatedProbability[ cumulatedProbability.length - 1 ] + "   " + cumulatedProbability[0] );
           // System.out.println( Arrays.toString(cumulatedProbability) );
            for(i=1; i<cumulatedProbability.length; i++){
                cumulatedProbability[i] = cumulatedProbability[i-1] + cumulatedProbability[i];
            }
            
            
            
            idxs.clear();
            
            for(int k=0; k<population.size(); k++){
                int low = 0, high = population.size()-1;
                int mid;
                double p = StdRandom.uniform(0.0, cumulatedProbability[population.size()-1]);
                while( low < high && high-low > 1 ){
                    mid = (low+high) / 2;
                    if( p <= cumulatedProbability[mid] )
                        high = mid;
                    else
                        low = mid;
                }
                
                if( cumulatedProbability[low] > p )
                    idxs.put( low, idxs.containsKey(low) ? idxs.get(low) + 1 : 1 );
                else
                    idxs.put( high, idxs.containsKey(high) ? idxs.get(high) + 1 : 1 );
//                
//                if( k == 0 ){
//                    if( cumulatedProbability[low] > p )
//                        System.out.println( p + " " +  cumulatedProbability[low] );
//                    else
//                        System.out.println( p + " " +  cumulatedProbability[high] );
//                }
                
                
            }
            
            
            i = 0;
            for( Individual<DFA,Integer> individual : population ){
                if( idxs.containsKey(i) ){
                    
                    int k = idxs.get(i);
                    while(k > 0){
                        buffer.add(individual);
                        k--;
                    }
                }
                i++;
            }
            
//            System.out.println( "Seleciont " + buffer.size() + "  "+ population.size() );  
            assert( buffer.size() == population.size() );
        }
    };
    
    static EvolutionaryAlgorithm.CrossoverOperator<DFA,Integer> crossover = new EvolutionaryAlgorithm.CrossoverOperator<DFA, Integer>() {

        @Override
        public void cross(ArrayList<Individual<DFA, Integer>> population, ArrayList<Individual<DFA, Integer>> buffer) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    static EvolutionaryAlgorithm.FinishCondition<DFA,Integer> finish = new EvolutionaryAlgorithm.FinishCondition<DFA, Integer>() {

        @Override
        public boolean shouldStop(Individual<DFA, Integer> individual, int iteration) {
            return iteration >= 1000;
        }
    };
    
    public static void main(String args[]){
        EvolutionaryAlgorithm<DFA,Integer> algorithm = new EvolutionaryAlgorithm<>(selection,null,null);
        Individual<DFA,Integer> result = algorithm.evolve();
        
        System.out.println(result.getIndividual());
        System.out.println(result.getFitness());
    }
}
