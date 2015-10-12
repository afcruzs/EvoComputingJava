/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 *
 * @author PC
 */
public class Utils {
    
    public static double avg(double [] a, int n){
        double ans = 0.0;
        for(int i=0; i<n; i++)
            ans += a[i];
        return ans / n;
    }
    
    public static double StdDev(double []a, int n){
        double avg = avg(a,n);
        double ans = 0.0;
        for(int i=0; i<n; i++){
            ans += (avg-a[i])*(avg-a[i]);
        }
        return Math.sqrt( ans / (n-1) );
    }
}
