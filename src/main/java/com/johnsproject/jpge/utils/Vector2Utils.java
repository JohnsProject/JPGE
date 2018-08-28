package com.johnsproject.jpge.utils;

public class Vector2Utils {

	private static final int USHIFT = 16;
	private static final int HEX = 0xFFFF;
	
	public static int convert(int x, int y) {
		return ((x & HEX)<<USHIFT)|(y & HEX);
	}
	
	public static int getX(int vector) {
		return (short)((vector >> USHIFT) & HEX);
	}
	
	public static int getY(int vector) {
		return (short)(vector & HEX);
	}
	
}
