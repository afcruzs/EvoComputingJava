package optimization.localSearch;


import math.StdRandom;



public class Operators {
	public static NeighborhoodOperator gaussianOperator = new NeighborhoodOperator() {
		
		@Override
		public Double[] getNeighbor(Double[] x, Double delta) {
			Double ret[] = new Double[x.length];
			
			for(int i=0; i<x.length; i++){
				ret[i] = x[i] + StdRandom.gaussian(0.0, delta);
			}
			
			return ret;
		}
		
		@Override
		public String toString(){
			return "Gaussian Operator";
		}
	};
	
	public static NeighborhoodOperator powerLogOperator = new NeighborhoodOperator() {
		
		@Override
		public Double[] getNeighbor(Double[] x, Double delta) {
			int sign;
			if( StdRandom.uniform() <= 0.5 ) sign = 1;
			else sign = -1;
			
			Double d[] = new Double[ x.length ];
			for(int i=0; i<d.length; i++) 
				d[i] = x[i] + sign * StdRandom.pareto(10)*delta; 
			
			return d;
		}
		
		@Override
		public String toString(){
			return "Pareto (Power log) Operator";
		}
	};
}