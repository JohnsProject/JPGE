package com.johnsproject.jpge.utils;


/**
 * The VectorMathUtils class provides useful functionalities
 *  for handling vector math problems. <br>
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 * 
 */
public class VectorMathUtils extends MathUtils{
	private static final byte SHIFT = 10;
	private static int vx = VectorUtils.X, vy = VectorUtils.Y, vz = VectorUtils.Z;
	 
    public static void movePointByAngleZ(int[] position, int angle){
    	int sin = sin(angle), cos = cos(angle);
    	int x = position[vx], y = position[vy];
    	position[vx] = (((x * cos - y * sin) >> SHIFT)+2);
    	position[vy] = (((y * cos + x * sin) >> SHIFT)+2);
    }
    
    public static void movePointByAngleY(int[] position, int angle){
    	int sin = sin(angle), cos = cos(angle);
    	int x = position[vx], z = position[vz];
    	position[vx] = (((x * cos - z * sin) >> SHIFT)+2);
    	position[vz] = (((z * cos + x * sin) >> SHIFT)+2);
    }
    
    public static void movePointByAngleX(int[] position, int angle){
    	int sin = sin(angle), cos = cos(angle);
    	int y = position[vy], z = position[vz];
    	position[vy] = (((y * cos - z * sin) >> SHIFT)+2);
    	position[vz] = (((z * cos + y * sin) >> SHIFT)+2);
    }
    
    public static void movePointByAnglesXYZ(int[] position, int[] angles){
    	movePointByAngleX(position, angles[vx]);
    	movePointByAngleY(position, angles[vy]);
    	movePointByAngleZ(position, angles[vz]);
    }
    
    public static void movePointByScale(int[] position, int[] scale){
    	int x = position[vx] * scale[vx];
    	int y = position[vy] * scale[vy];
    	int z = position[vz] * scale[vz];
    	position[vx] = x;
    	position[vy] = y;
    	position[vz] = z;
    }
    
    public static int[] getDistance(int[] position1, int[] position2) {
    	int x = position1[vx] - position2[vx];
    	int y = position1[vy] - position2[vy];
    	int z = position1[vz] - position2[vz];
    	return new int[]{x, y, z};
    }
    
    public static int[] getCenter(int[][] positions) {
    	int x = 0, y = 0, z = 0, s = positions.length;
    	for (int i = 0; i < positions.length; i++) {
			x += positions[i][vx];
			y += positions[i][vy];
			z += positions[i][vz];
		}
    	return new int[]{x/s, y/s, z/s};
    }
    
    public static int getRadius(int[][] positions) {
    	int r = 0;
    	for (int i = 0; i < positions.length; i++) {
			if(Math.abs(positions[i][vx]) > Math.abs(r)) r = positions[i][vx];
			if(Math.abs(positions[i][vy]) > Math.abs(r)) r = positions[i][vy];
			if(Math.abs(positions[i][vz]) > Math.abs(r)) r = positions[i][vz];
		}
    	return r;
    }
    
    public static int[] crossProduct(int[] a, int[] b) {
    	int x = a[vy] * b[vz] - a[vz] * b[vy];
    	int y = a[vx] * b[vz] - a[vz] * b[vx];
    	int z = a[vx] * b[vy] - a[vy] * b[vx];
    	return new int[]{x, y, z};
    }
    
    public static int dotProduct(int[] a, int[] b) {
    	return a[vx] * a[vy] * a[vz] + b[vx] * b[vy] * b[vz];
    }

    public static int[] normal(int[] v1, int[] v2, int[] v3) {
    	int[] a = new int[]{v2[vx] - v1[vx], v2[vy] - v1[vy], v2[vz] - v1[vz]};
    	int[] b = new int[]{v3[vx] - v1[vx], v3[vy] - v1[vy], v3[vz] - v1[vz]};
    	int x = a[vy] * b[vz] - a[vz] * b[vy];
    	int y = a[vz] * b[vx] - a[vx] * b[vz];
    	int z = a[vx] * b[vy] - a[vy] * b[vx];
    	return new int[]{x, y, z};
    }
}
