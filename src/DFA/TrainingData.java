/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DFA;

/**
 *
 * @author PC
 */
public class TrainingData {
    public int input[];
    public boolean classification;
    
    public TrainingData(int data[], boolean classification){
        this.input = data;
        this.classification = classification;
    }
    
}
