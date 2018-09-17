package com.johnsproject.jpge;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import com.johnsproject.jpge.utils.MathUtils;
import com.johnsproject.jpge.utils.Vector3Utils;

public class Benchmarks {
	
	public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void integerBenchmark() {
	    int value = 1;
	    value += 100;
	    value -= 100;
	    value *= 100;
	    value /= 100;
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void floatBenchmark() {
		 float value = 1;
		 value += 100;
		 value -= 100;
		 value *= 100;
		 value /= 100;
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void vectorIntArrayBenchmark() {
	    int[] vector = new int[] {50,50,50};
	    int x = vector[0];
	    int y = vector[1];
	    int z = vector[2];
	    vector[0] = x;
	    vector[1] = y;
	    vector[2] = z;
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void vectorPackedBenchmark() {
		 long vector = Vector3Utils.convert(50, 50, 50);
		 int x = Vector3Utils.getX(vector);
		 int y = Vector3Utils.getY(vector);
		 int z = Vector3Utils.getZ(vector);
		 Vector3Utils.setX(vector, x);
		 Vector3Utils.setY(vector, y);
		 Vector3Utils.setZ(vector, z);
	}	
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void mathSinCosBenchmark() {
		Math.sin(Math.toRadians(50));
		Math.cos(Math.toRadians(50));
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void mathUtilsSinCosBenchmark() {
		MathUtils.sin(50);
		MathUtils.cos(50);
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void mathSqrtBenchmark() {
		Math.sqrt(125);
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void mathUtilsSqrtBenchmark() {
		MathUtils.sqrt(125);
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void mathPowBenchmark() {
		Math.pow(5, 5);
	}
	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void mathUtilsPowBenchmark() {
		MathUtils.pow(5, 5);
	}
}

/* ------------------------------- BENCHMARK RESULTS ----------------------------------------
 * Benchmark                    			Mode   Cnt      Score      		Error  	    Units
 * 
 * Benchmarks.floatBenchmark            	thrpt   20  2943085848,876 ± 123854828,411  ops/s
 * Benchmarks.integerBenchmark          	thrpt   20  2970269670,470 ±  98233724,042  ops/s
 * 
 * Benchmarks.mathPowBenchmark          	thrpt   20  3057605220,592 ±  42483688,596  ops/s
 * Benchmarks.mathUtilsPowBenchmark     	thrpt   20   517305246,373 ±   1841468,156  ops/s
 * 
 * Benchmarks.mathSinCosBenchmark       	thrpt   20    10612065,678 ±    259946,905  ops/s
 * Benchmarks.mathUtilsSinCosBenchmark  	thrpt   20  1313963994,723 ±  55156688,179  ops/s
 * 
 * Benchmarks.mathSqrtBenchmark         	thrpt   20  2971027729,621 ±  88309944,454  ops/s
 * Benchmarks.mathUtilsSqrtBenchmark    	thrpt   20   239768505,712 ±   8972302,570  ops/s
 * 
 * Benchmarks.vectorIntArrayBenchmark   	thrpt   20  3282109759,848 ±  13874974,082  ops/s
 * Benchmarks.vectorPackedBenchmark     	thrpt   20  3282280731,327 ±   8531409,716  ops/s
 * 
 * Benchmarks.drawPolygonIntegerBenchmark  	thrpt   20     1871815,084 ±      5869,040  ops/s
 * Benchmarks.drawPolygonFloatBenchmark  	thrpt   20     1550902,575 ± 	  7407,074  ops/s
 */
