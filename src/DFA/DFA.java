/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DFA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import optimization.evolutionaryStrategies.Individual;

/**
 *
 * @author PC
 */
public class DFA implements Individual<DFA,Integer>{
   
   private int zeroEdges[], oneEdges[];
   private boolean accept[];
   private int correctEvaluations;
   private int initialState;
   
   public DFA(int expectedSize, int initialState){
       zeroEdges = new int[expectedSize];
       oneEdges = new int[expectedSize];
       accept = new boolean[expectedSize];
       for(int i=0; i<expectedSize; i++){
           zeroEdges[i] = oneEdges[i] = -1;
           accept[i] = false;
       }
       this.initialState = initialState;
       correctEvaluations = 0;
   }
   
   
   public void setAcceptation(int u){
       accept[u] = true;
   }
   
   
   
   public void addEdge( int u, int v, boolean label ){
       if( !label ){
           zeroEdges[u] = v;
       }else{
           oneEdges[v] = u;
       }
   }
   
   private boolean accept( TrainingData data ){
       Stack<Integer> q = new Stack<>();
       
       int n = zeroEdges.length;
       int currentNode = initialState;
       if( currentNode == -1 ) return false;
       for(int i=0; i<n; i++){
           if( data.input[i] == 0 ){
               currentNode = zeroEdges[i];
           }else if( data.input[i] == 1 ){
               currentNode = oneEdges[i];
           }
           
           if( currentNode == -1 ) return false;
       }
       if( currentNode == -1 ) return false;
       return accept[currentNode];
   }
   
   public void measureFitness(){
       correctEvaluations = 0;
       for( TrainingData data : Data.sampleData ){
           correctEvaluations += (data.classification == accept(data) ? 1 : 0 ) ;
       }
   }
 
    @Override
    public DFA getIndividual() {
        return this;
    }

    @Override
    public Integer getFitness() {
        measureFitness();
        return correctEvaluations;
    }

    @Override
    public int compareTo(Individual<DFA, Integer> o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
   class Node{
       int zeroEdge, oneEdge;
       
       public Node(int z, int o){
           zeroEdge = z;
           oneEdge = o;
       }
   }
}
