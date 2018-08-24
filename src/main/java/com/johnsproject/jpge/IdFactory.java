package com.johnsproject.jpge;

public class IdFactory {
	
	private static IdFactory instance;
	public static IdFactory getInstance() {
		if (instance == null) {
			instance = new IdFactory();
		}
		return instance;
	}
	
	private int currentID = 0;
	
	public int getId() {
		currentID++;
		return currentID;
	}
	
	public int[] getIds(int count) {
		int[] ids = new int[count];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = getId();
		}
		return ids;
	}
}
