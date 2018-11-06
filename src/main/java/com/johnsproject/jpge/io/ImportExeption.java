package com.johnsproject.jpge.io;

public class ImportExeption extends Exception{
	private static final long serialVersionUID = 1L;
	public ImportExeption() { super(); }
	public ImportExeption(String message) { super(message); }
	public ImportExeption(String message, Throwable cause) { super(message, cause); }
	public ImportExeption(Throwable cause) { super(cause); }
}
