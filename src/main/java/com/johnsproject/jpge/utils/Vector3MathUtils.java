package com.johnsproject.jpge.utils;


/**
 * The VectorMathUtils class provides useful functionalities
 *  for handling 3D vector math problems. <br>
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 * 
 */
public class Vector3MathUtils extends MathUtils{
	private static final byte SHIFT = 10;
	 
    public static long movePointByAngleZ(long position, int angle){
    	int sin = sin(angle), cos = cos(angle);
    	long x = Vector3Utils.getX(position),
    			y = Vector3Utils.getY(position),
    			z = Vector3Utils.getZ(position);
    	long vx = (((x * cos - y * sin) >> SHIFT)+2);
    	long vy = (((y * cos + x * sin) >> SHIFT)+2);
    	return Vector3Utils.convert(vx, vy, z);
    }
    
    public static long movePointByAngleY(long position, int angle){
    	int sin = sin(angle), cos = cos(angle);
    	long x = Vector3Utils.getX(position),
    			y = Vector3Utils.getY(position),
    			z = Vector3Utils.getZ(position);
    	long vx = (((x * cos - z * sin) >> SHIFT)+2);
    	long vz = (((z * cos + x * sin) >> SHIFT)+2);
    	return Vector3Utils.convert(vx, y, vz);
    }
    
    public static long movePointByAngleX(long position, int angle){
    	int sin = sin(angle), cos = cos(angle);
    	long x = Vector3Utils.getX(position),
    			y = Vector3Utils.getY(position),
    			z = Vector3Utils.getZ(position);
    	long vy = (((y * cos - z * sin) >> SHIFT)+2);
    	long vz = (((z * cos + y * sin) >> SHIFT)+2);
    	return Vector3Utils.convert(x, vy, vz);
    }
    
    public static long movePointByAnglesXYZ(long position, long angles){
    	position = movePointByAngleX(position, (int)Vector3Utils.getX(angles));
    	position = movePointByAngleY(position, (int)Vector3Utils.getY(angles));
    	position = movePointByAngleZ(position, (int)Vector3Utils.getZ(angles));
    	return position;
    }
    
    public static long movePointByScale(long position, long scale){
    	return Vector3Utils.multiply(position, scale);
    }
    
    public static long getDistance(long position1, long position2) {
    	return Vector3Utils.subtract(position1, position2);
    }
    
//    public static long getCenter(long[] positions) {
//    	int x = 0, y = 0, z = 0, s = positions.length;
//    	for (int i = 0; i < positions.length; i++) {
//			x += positions[i][vx];
//			y += positions[i][vy];
//			z += positions[i][vz];
//		}
//    	return new long{x/s, y/s, z/s};
//    }
    
//    public static int getRadius(long[] positions) {
//    	int r = 0;
//    	for (int i = 0; i < positions.length; i++) {
//			if(Math.abs(positions[i][vx]) > Math.abs(r)) r = positions[i][vx];
//			if(Math.abs(positions[i][vy]) > Math.abs(r)) r = positions[i][vy];
//			if(Math.abs(positions[i][vz]) > Math.abs(r)) r = positions[i][vz];
//		}
//    	return r;
//    }
    
//    public static long crossProduct(long a, long b) {
//    	int x = a[vy] * b[vz] - a[vz] * b[vy];
//    	int y = a[vx] * b[vz] - a[vz] * b[vx];
//    	int z = a[vx] * b[vy] - a[vy] * b[vx];
//    	return new long{x, y, z};
//    }
//    
//    public static int dotProduct(long a, long b) {
//    	return a[vx] * a[vy] * a[vz] + b[vx] * b[vy] * b[vz];
//    }
//
//    public static long normal(long v1, long v2, long v3) {
//    	long a = new long{v2[vx] - v1[vx], v2[vy] - v1[vy], v2[vz] - v1[vz]};
//    	long b = new long{v3[vx] - v1[vx], v3[vy] - v1[vy], v3[vz] - v1[vz]};
//    	int x = a[vy] * b[vz] - a[vz] * b[vy];
//    	int y = a[vz] * b[vx] - a[vx] * b[vz];
//    	int z = a[vx] * b[vy] - a[vy] * b[vx];
//    	return new long{x, y, z};
//    }
}
