package optimization;

import math.MathFunction;
import java.util.ArrayList;
import java.util.List;


public class OptimizationProblem {
	private ArrayList< MathFunction > gConstraints;
	private ArrayList< MathFunction > hConstraints;
	private MathFunction optimizationFunction;
	
	public OptimizationProblem(ArrayList<MathFunction> gConstraints,
			ArrayList<MathFunction> hConstraints,
			MathFunction optimizationFunction) {
		
		this.gConstraints = gConstraints;
		this.hConstraints = hConstraints;
		this.optimizationFunction = optimizationFunction;
	}
	
	public List<Double[]> randomPoints(int points, int n){
		List<Double[]> d = new ArrayList<>();
		
		for(int i=0; i<points; i++)
			d.add( optimizationFunction.randomPoint(n) );
		
		return d;
	}
	
	public double evaluate( Double x[] ){
		return optimizationFunction.evaluate(x);
	}
	
	public boolean satisfy( Double x[] ){
		for( MathFunction g : gConstraints )
			if( g.evaluate(x) > 0 ) return false;
		
		for( MathFunction h : hConstraints )
			if( h.evaluate(x) != 0 ) return false;
		
		return true;
	}
	
	public String toString(){
		return optimizationFunction.toString();	
	}
	
}


