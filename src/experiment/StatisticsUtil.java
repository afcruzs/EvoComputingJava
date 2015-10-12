package experiment;

import java.util.Arrays;


public class StatisticsUtil {
	public static Double minimum( Double[] d ){
		Double min = d[0];
		for(int i=1; i<d.length; i++)
			min = Math.min( min, d[i] );
		return min;
	}
	
	public static Double maximum( Double[] d ){
		Double max = d[0];
		for(int i=1; i<d.length; i++)
			max = Math.max( max, d[i] );
		return max;
	}
	
	public static Double average( Double d[] ){
		Double avg = 0.0;
		for(int i=0; i<d.length; i++)
			avg += d[i];
		
		return avg / ( (double) d.length);
	}
	
	public static Double median( Double d[] ){
		Arrays.sort(d);
		if( d.length % 2 == 0 )
			return d[ d.length/2 - 1 ];
		else
			return d[ d.length / 2 ];			
	}
	
	public static Double stdDeviation( Double d[], Double avg ){
		Double dev = 0.0;
		for(int i=0; i<d.length; i++)
			dev += ( d[i] - avg )*( d[i] - avg );
		
		return dev / ( (double) d.length );
	}
	
	public static Double stdDeviation( Double d[] ){
		return stdDeviation(d,  average(d) );
	}
}
