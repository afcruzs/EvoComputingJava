package math;

import optimization.OptimizationProblem;
import java.util.ArrayList;


public class TestFunctions {
	
	private static ArrayList<MathFunction> easyRestriction(final double a, final double b, int n){
		ArrayList<MathFunction> g = new ArrayList<>();
		
		for(int i=0; i<n; i++){
			final int lel = i;
			
			g.add( new MathFunction() {
				
				@Override
				public Double evaluate(Double[] x) {
					return a- x[lel];
				}
			} );
			
			g.add( new MathFunction() {
				
				@Override
				public Double evaluate(Double[] x) {
					return x[lel] - b;
				}
			} );
		}
		
		return g;
		
	}
	
	static OptimizationProblem easy(){
		
		MathFunction function = new MathFunction() {
			
			@Override
			public Double[] randomPoint(int n) {
				Double d[] = new Double[n];
				for(int i=0; i<n; i++) d[i] = StdRandom.uniform(0, 1.0);
				return d;
			}
			
			@Override
			public Double evaluate(Double[] x) {
				return x[0]*x[0] - x[1]*x[1];
			}
		};
		
		ArrayList<MathFunction> g = new ArrayList<MathFunction>();
		
		g.add( new MathFunction() {

			@Override
			public Double evaluate(Double[] x) {
				return -x[0];
			}
		} );
		
		g.add( new MathFunction() {
			
			@Override
			public Double evaluate(Double[] x) {
				return -x[1];
			}
		} );
		
		g.add( new MathFunction() {
			@Override
			public Double evaluate(Double[] x) {
				return x[0] + x[1] - 4.0;
			}
		} );
		
		return new OptimizationProblem(g, 
				new ArrayList<MathFunction>(), function);
	}
	
	
	public static OptimizationProblem rosenbrock(){
		
//		ArrayList<MathFunction> g = easyRestriction(-2.048,2.048,2);
//		ArrayList<MathFunction> h = new ArrayList<MathFunction>();
		
		return new OptimizationProblem(null,null, new MathFunction() {

			@Override
			public Double[] randomPoint(int n) {
				return simpleRandomPoint(-2.048,2.048, n);
			}
			
			@Override
			public Double evaluate(Double[] x) {
				return 100.0 * ( x[0]*x[0] - x[1] ) * ( x[0]*x[0] - x[1] ) + ( 1.0 - x[1] )*( 1.0 - x[1] );
			}
			
			@Override
			public String toString(){
				return "Rosenbrock";
			}
		}){
                    @Override
                    public boolean satisfy( Double x[] ){
                        for(int i=0; i<x.length; i++)
                            if( !( x[i] >= -2.048 && x[i] <= 2.048 ) )
                                return false;
                        return true;
                    }
                };
	}
	
	public static OptimizationProblem schwefel( int n ){
//		
//		ArrayList<MathFunction> g = easyRestriction(-512.0, 512.0, n);
//		ArrayList<MathFunction> h = new ArrayList<MathFunction>();
		
		return new OptimizationProblem(null, null, new MathFunction() {
			
			public Double[] randomPoint( int n ){
				return simpleRandomPoint(-512.0,512.0, n);
			}
			
			@Override
			public Double evaluate(Double[] x) {
				int n = x.length;
				double ans  = 418.9829 * ( (double) n );
				for(int i=0; i<n; i++) 
					ans += -x[i] * Math.sin( Math.sqrt( Math.abs(x[i]) ) );
				
				return ans;
			}
			

			@Override
			public String toString(){
				return "Schwefel";
			}
		}){
                    @Override
                    public boolean satisfy( Double x[] ){
                        for(int i=0; i<x.length; i++)
                            if( !( x[i] >= -512.0 && x[i] <= 512.0 ) )
                                return false;
                        return true;
                    }
                };
	}
	
	public static OptimizationProblem rastrigin(final double A, final int n){
//		ArrayList<MathFunction> g = easyRestriction(-5.120, 5.120, n);
//		ArrayList<MathFunction> h = new ArrayList<MathFunction>();
		
		return new OptimizationProblem(null, null, new MathFunction() {
			
			public Double[] randomPoint( int n ){
				return simpleRandomPoint(-5.120,5.120, n);
			}
			
			@Override
			public Double evaluate(Double[] x) {
				int n = x.length;
				double ans = n*A;
				for(int i=0; i<n; i++){
					ans += x[i]*x[i] - A*Math.cos( 2.0 * Math.PI * x[i] );
				}
				return ans;
			}
			

			@Override
			public String toString(){
				return "Rastrigin";
			}
		}){
                    @Override
                    public boolean satisfy( Double x[] ){
                        for(int i=0; i<x.length; i++)
                            if( !( x[i] >= -5.120 && x[i] <= 5.120 ) )
                                return false;
                        return true;
                    }
                };
	}
	
	public static OptimizationProblem griewangk( int n ){
//		ArrayList<MathFunction> g = easyRestriction(-600.0, 600.0, n);
//		ArrayList<MathFunction> h = new ArrayList<MathFunction>();
		
		return new OptimizationProblem(null, null, new MathFunction() {
                        
                        
			
			public Double[] randomPoint( int n ){
				return simpleRandomPoint(-600.0,600.0, n);
			}
			
			
			@Override
			public Double evaluate(Double[] x) {
				double ans = 1.0;
				
				for(int i=0; i<x.length; i++)
					ans += x[i]*x[i]/4000.0;
				
				double mult = -1;
				for(int i=0; i<x.length; i++)
					mult *= Math.cos( x[i] / Math.sqrt( i+1 ) ); 
				
				return ans + mult;
			}
			

			@Override
			public String toString(){
				return "Griewangk";
			}
		}){
                    @Override
                    public boolean satisfy( Double x[] ){
                        for(int i=0; i<x.length; i++)
                            if( !(x[i] >= -600.0 && x[i] <= 600.0) )
                                return false;
                        
                        return true;
                    }
                };
	}
}
