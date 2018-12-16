/**
 * MIT License
 *
 * Copyright (c) 2018 John Salomon - John´s Project
 *  
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.johnsproject.jpge;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import com.johnsproject.jpge.utils.MathUtils;

public class Benchmarks {
	
	public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void integerBenchmark() {
//	    int value = 1;
//	    value += 100;
//	    value -= 100;
//	    value *= 100;
//	    value /= 100;
//	}
//	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void floatBenchmark() {
//		 float value = 1;
//		 value += 100;
//		 value -= 100;
//		 value *= 100;
//		 value /= 100;
//	}
	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void vectorIntArrayBenchmark() {
//	    int[] vector = new int[] {50,50,50};
//	    int x = vector[0];
//	    int y = vector[1];
//	    int z = vector[2];
//	    vector[0] = x;
//	    vector[1] = y;
//	    vector[2] = z;
//	}
	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void vectorPackedBenchmark() {
//		 long vector = Vector3Utils.convert(50, 50, 50);
//		 int x = Vector3Utils.getX(vector);
//		 int y = Vector3Utils.getY(vector);
//		 int z = Vector3Utils.getZ(vector);
//		 Vector3Utils.setX(vector, x);
//		 Vector3Utils.setY(vector, y);
//		 Vector3Utils.setZ(vector, z);
//	}	
	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void mathSinCosBenchmark() {
//		Math.sin(Math.toRadians(50));
//		Math.cos(Math.toRadians(50));
//	}
//	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void mathUtilsSinCosBenchmark() {
//		MathUtils.sin(50);
//		MathUtils.cos(50);
//	}
//	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void mathSqrtBenchmark() {
//		Math.sqrt(125);
//	}
//	
	@Benchmark
	@Fork(value = 1)
	@BenchmarkMode(Mode.Throughput)
	public void mathUtilsSqrtBenchmark() {
		MathUtils.sqrt(125);
	}
//	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void mathPowBenchmark() {
//		Math.pow(5, 5);
//	}
//	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void mathUtilsPowBenchmark() {
//		MathUtils.pow(5, 5);
//	}
	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void arrayBenchmark() {
//		int[] vector = new int[] {50,50,50};
//		Vertex vertex = new Vertex(new int[] {50, 50, 50}, new int[] {0,0,0}, 0, 0);
//	    int x = vector[0];
//	    int y = vector[1];
//	    int z = vector[2];
//	}
//	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void classBenchmark() {
//		int[] vector = new int[] {50,50,50};
//		Vertex vertex = new Vertex(new int[] {50, 50, 50}, new int[] {0,0,0}, 0, 0);
//	    int x = vertex.getLocation()[0];
//	    int y = vertex.getLocation()[1];
//	    int z = vertex.getLocation()[2];
//	}
	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void directlyBenchmark() {
//		int a = 10;
//		int b = 100;
//		int x = a * b;
//		int y = a / b;
//	}
//	
//	@Benchmark
//	@Fork(value = 1)
//	@BenchmarkMode(Mode.Throughput)
//	public void methodBenchmark() {
//		test();
//	}
//	
//	public void test() {
//		int a = 10;
//		int b = 100;
//		int x = a * b;
//		int y = a / b;
//	}
	
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
 * Benchmarks.mathUtilsSinCosBenchmark  	thrpt   20  1444695918,874 ±  52460196,818  ops/s
 * 
 * Benchmarks.mathSqrtBenchmark         	thrpt   20  2971027729,621 ±  88309944,454  ops/s
 * Benchmarks.mathUtilsSqrtBenchmark  		thrpt   20  3202462958,330 ±  33535129,759  ops/s
 * 
 * Benchmarks.vectorIntArrayBenchmark   	thrpt   20  3282109759,848 ±  13874974,082  ops/s
 * Benchmarks.vectorPackedBenchmark     	thrpt   20  3282280731,327 ±   8531409,716  ops/s
 * 
 * Benchmarks.drawPolygonIntegerBenchmark  	thrpt   20     1871815,084 ±      5869,040  ops/s
 * Benchmarks.drawPolygonFloatBenchmark  	thrpt   20     1550902,575 ± 	  7407,074  ops/s
 * 
 * Benchmarks.arrayBenchmark  				thrpt   20   155647600,193 ±   1518862,598  ops/s
 * Benchmarks.classBenchmark  				thrpt   20   155463978,465 ±   1599607,806  ops/s
 * 
 * Benchmarks.directlyBenchmark  			thrpt   20  3284081297,588 ±  10104135,657  ops/s
 * Benchmarks.methodBenchmark    			thrpt   20  3278210219,368 ±   9043291,269  ops/s
 */
