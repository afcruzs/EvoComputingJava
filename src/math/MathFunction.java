package math;


public abstract class MathFunction {
	public abstract Double evaluate( Double x[] );
	
	protected Double[] simpleRandomPoint(double a, double b, int n){
		Double d[] = new Double[n];
		for(int i=0; i<n; i++) d[i] = StdRandom.uniform(a,b);
		return d;
	}
	
	public Double[] randomPoint( int n ){
		Double d[] = new Double[n];
		for(int i=0; i<n; i++) d[i] = StdRandom.uniform();
		return d;
	}
}
