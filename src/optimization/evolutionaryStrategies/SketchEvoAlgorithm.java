/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimization.evolutionaryStrategies;

import experiment.Experiment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import math.StdRandom;
import math.TestFunctions;
import optimization.OptimizationProblem;

/**
 *
 * @author PC
 */
public class SketchEvoAlgorithm {
    
    
    
       
   static double ev(OptimizationProblem problem, Double x[]){
       return ( problem.satisfy(x) ? problem.evaluate(x) : 1e9 );
   }
    
   public static Double[] best( OptimizationProblem problem, List<Double[]> population ){
       double best = ev(problem,population.get(0));
       Double ind[] = population.get(0);
       for(int i=1; i<population.size(); i++){
           double aux = ev(problem,population.get(i));
           if( aux < best ){
               best = aux;
               ind = population.get(i);
           }               
       }
       return ind;
   }
   
   static double min( double d[] ){
       double minm = d[0];
       for(int i=1; i<d.length; i++)
           if( d[i] < minm )
               minm = d[i];
       
       return minm;
   }
  
   public static interface SelectionOperator{
       void selection( OptimizationProblem problem, List<Double[]> population );
   }
   
   public static interface CrossoverOperator{
       void crossover( OptimizationProblem problem,  List<Double[]> population );
   }
   
   public static interface  MutationOperator{
       void mutation( OptimizationProblem problem, List<Double[]> population );
   }
   
   public static SelectionOperator roulette = new SelectionOperator() {
       
       @Override
       public String toString(){
           return "roulette";
       }

       @Override
       public void selection(OptimizationProblem problem, List<Double[]> population) {
            double a []= new double[population.size()];
            for(int i=0; i<a.length; i++)
                a[i] = -1*ev(problem,population.get(i));

            double extra = Math.abs(min(a));
            for(int i=0; i<a.length; i++)
                a[i] += extra;

            for(int i=1; i<a.length; i++)
                a[i] = a[i-1] + a[i];

            double maxm = a[a.length-1];
            for(int i=0; i<a.length; i++) a[i] /= maxm;
            ArrayList<Double[]> buffer = new ArrayList<>();
            for(int k=0; k<a.length; k++){
                int low = 0, high = population.size()-1;
                     int mid;

                     double p = StdRandom.uniform();
                     while( low < high && high-low > 1 ){
                         mid = (low+high) / 2;
                         if( p <= a[mid] )
                             high = mid;
                         else
                             low = mid;
                     }

                     if( a[low] > p )
                         buffer.add( population.get(low) );
                     else
                         buffer.add( population.get(high) );
            }

            population.clear();
            population.addAll(buffer);
            buffer.clear();
            buffer = null;
            a = null;
       }
   };
   
   
   static void deepCopyArray(Double origin[], Double dest[]){
       
       assert( origin.length == dest.length );
       
       for(int i=0; i<dest.length; i++)
           dest[i] = new Double(origin[i]);
   }
   
   public static CrossoverOperator singlePoint = new CrossoverOperator() {
       
       @Override
       public String toString(){
           return "singlePoint";
       }

       @Override
       public void crossover(OptimizationProblem problem, List<Double[]> population) {
            Collections.shuffle(population);
            Random rand = new Random();
            ArrayList<Double[]> buffer = new ArrayList<>();

            for(int k=0; k<population.size(); k+=2){
                int piv = rand.nextInt( population.get(k).length );
                Double p1[] = population.get(k);
                Double p2[] = population.get(k+1);

                Double o1[] = new Double[p1.length];
                Double o2[] = new Double[p1.length];

                for(int i=0; i<piv; i++){
                    o1[i] = p1[i];
                    o2[i] = p2[i];
                }

                for(int i=piv; i<p1.length; i++){
                    o1[i] = p2[i];
                    o2[i] = p1[i];
                }
                   
                
                if( !problem.satisfy(o1) )
                    deepCopyArray(p1,o1);

                if( !problem.satisfy(o2) )
                    deepCopyArray(p2,o2);


                buffer.add(o1);
                buffer.add(o2);
            }

            population.clear();
            population.addAll(buffer);
            buffer.clear();
            buffer = null;
       }
   };
           
   public static MutationOperator singleBitMutation = new MutationOperator() {
       
       
       @Override
       public String toString(){
           return "mutation";
       }

       @Override
       public void mutation(OptimizationProblem problem, List<Double[]> population) {
            Random rand = new Random();
            for(int i=0; i<population.size(); i++){
                if( rand.nextBoolean() ){
                    Double xd[] = new Double[population.get(i).length];
                    deepCopyArray(population.get(i), xd);
                    for(int k=0; k<population.get(i).length; k++){
                        if( rand.nextBoolean()  )
                            population.get(i)[k] += StdRandom.gaussian(0.0, 10.1);
                    }
                    
                    if( !problem.satisfy(population.get(i)) )
                        population.set(i, xd);

                }
            }
       }
   };

   static void printPopulation( OptimizationProblem problem, List<Double[]> population ){
       for(int i=0; i<population.size(); i++)
           System.out.print( ev(problem,population.get(i)) +  " " );
       System.out.println();
   }
   
     
    public static double optimize( OptimizationProblem problem, List<Double[]> population, int iterations,
           SelectionOperator selection, CrossoverOperator crossover, 
           MutationOperator mutation  ){
        
        return optimize(problem, population, iterations, selection, crossover, mutation, null);
    }
    
    public static Double optimize(OptimizationProblem problem, 
            List<Double[]> population, int iterations, SelectionOperator selection, 
            CrossoverOperator crossover, MutationOperator mutation, Experiment handler) {
        
        for(int it=0; it<iterations; it++){
            selection.selection(problem,population);
            crossover.crossover(problem,population);
            mutation.mutation(problem,population);
            if( handler != null ){
                for(int i=0; i<population.size(); i++){
                    assert( problem.satisfy(population.get(i)) );
                    handler.setData(it, i,  ev(problem,population.get(i)) );
                }
            }
        }
        
        Double best[] = best( problem,  population );
//        for(int i=0; i<population.size(); i++){
//            System.out.println( ev(problem,population.get(i)) );
//        }
        return ev( problem, best );
    }
   
    
    public static void main(String args[]){
        for(int T=0; T<1; T++){
            int iterations = 1000, popSize = 50,n = 10;
            OptimizationProblem function = TestFunctions.schwefel(n);
            List<Double[]> population = function.randomPoints(popSize, n);
            double ans = optimize(function, population, iterations, roulette, singlePoint, singleBitMutation);
            System.out.println( String.valueOf(ans).replace(".",",") );
            population.clear();
        }        
    }
}
