/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import experiment.Experiment;
import java.util.ArrayList;
import java.util.List;
import optimization.OptimizationProblem;
import optimization.evolutionaryStrategies.SketchEvoAlgorithm;

/**
 *
 * @author PC
 */
public class EvolutionaryAlgorithmAdapter implements  optimization.OptimizationStrategy{
    
    SketchEvoAlgorithm.SelectionOperator selection;
    SketchEvoAlgorithm.CrossoverOperator crossover;
    SketchEvoAlgorithm.MutationOperator mutation;
    int iterations;
    List<Double[]> population;
    Experiment handler;
    
    public EvolutionaryAlgorithmAdapter(SketchEvoAlgorithm.SelectionOperator selection,
            SketchEvoAlgorithm.CrossoverOperator crossover,
            SketchEvoAlgorithm.MutationOperator mutation,
            int iterations){
        
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.iterations = iterations;
    }

    @Override
    public Double optimize(OptimizationProblem problem) {
        return SketchEvoAlgorithm.optimize(problem, population, iterations, selection, crossover, mutation, handler);
    }

    @Override
    public void setInitialPopulation(List<Double[]> population) {
        this.population = new ArrayList<>(population);
    }

    @Override
    public void setExperimentHandler(Experiment experiment) {
        this.handler = experiment;
    }
    
    @Override
    public String toString(){
        return "Evolutionary Algorithm " + selection + " " + crossover + " " + mutation;
    }
}
