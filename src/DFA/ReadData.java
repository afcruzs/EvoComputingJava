/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DFA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author PC
 */
public class ReadData {
    public static void main(String [] args) throws IOException{
        BufferedReader in = new BufferedReader( new FileReader(new File("gecco-test-10.txt")) );
        String data[] = in.readLine().split("\\s+");
        int T = Integer.parseInt(data[0]);
        int classes = Integer.parseInt(data[1]);
        String line = "";
        Data.sampleData = new TrainingData[T];
        for(int k=0; k<T; k++){
            data = in.readLine().split("\\s+");
            boolean value = Boolean.parseBoolean(data[0]);
            int n = Integer.parseInt(data[1]);
            char xd[] = new char[n];
            for(int i=2; i<data.length; i++){
                xd[i] = data[i].charAt(0);
            }
          //  Data.sampleData[k] = new TrainingData(xd, value);
        }
    }
}
