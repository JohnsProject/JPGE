package com.johnsproject.jpge.utils;

public class UVUtils {

	private static final int USHIFT = 8;
	private static final int HEX = 0xFF;
	
	public static int convert(int u, int v) {
		return ((u & HEX)<<USHIFT)|(v & HEX);
	}
	
	public static int getU(int uv) {
		return (uv >> USHIFT) & HEX;
	}
	
	public static int getV(int uv) {
		return uv & HEX;
	}
	
}
