/**
 * 
 */
package com.johnsproject.jpge.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * The FileIO class provides useful file loading methods.
 * 
 * @author John´s Project - John Konrad Ferraz Salomon
 *
 */
public class FileIO {

	/**
	 * Reads the content of the file at the given path and returns it.
	 * 
	 * @param fileName file path.
	 * @return content of given file.
	 * @throws IOException
	 */
	public static String readFile(String fileName) throws IOException {
		String content = null;
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(fileName);
			content = readStream(fileInputStream);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		return content;
	}

	/**
	 * Reads the content of the given {@link InputStream} and returns it.
	 * 
	 * @param stream {@link InputStream} to read from.
	 * @return content of the given {@link InputStream}.
	 * @throws IOException
	 */
	public static String readStream(InputStream stream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));

		StringBuilder stringBuilder = new StringBuilder();

		for (String line = in.readLine(); line != null; line = in.readLine()) {
			stringBuilder.append(line);
			stringBuilder.append("\n");
		}

		in.close();
		return stringBuilder.toString();
	}

	/**
	 * Loads the image at the given path and returns it as a {@link BufferedImage}.
	 * 
	 * @param path image path.
	 * @return loaded image as a {@link BufferedImage}.
	 * @throws IOException
	 */
	public static BufferedImage loadImage(String path) throws IOException {
		BufferedImage image = new BufferedImage(1, 1, 1);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(path);
			image = ImageIO.read(fileInputStream);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		image.flush();
		return image;
	}
	
	/**
	 * Loads an image from the given {@link InputStream} and returns it as a {@link BufferedImage}.
	 * 
	 * @param stream {@link InputStream} to read from.
	 * @return loaded image as a {@link BufferedImage}.
	 * @throws IOException
	 */
	public static BufferedImage loadImage(InputStream stream) throws IOException {
		BufferedImage image = new BufferedImage(1, 1, 1);
		try {
			image = ImageIO.read(stream);
		} finally {}
		image.flush();
		return image;
	}
	
	/**
	 * Loads the image at the given path and returns it as a {@link BufferedImage} 
	 * with the given size.
	 * 
	 * @param path image path.
	 * @param width destination width.
	 * @param height destination height.
	 * @return loaded image as a {@link BufferedImage}.
	 * @throws IOException
	 */
	public static BufferedImage loadImage(String path, int width, int height) throws IOException {
		BufferedImage image = new BufferedImage(1, 1, 1);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(path);
			image = ImageIO.read(fileInputStream);
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
		Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		resized.createGraphics().drawImage(tmp, 0, 0, null);
		resized.createGraphics().dispose();
		return resized;
	}
	
	/**
	 * Loads an image from the given {@link InputStream} and returns it as a {@link BufferedImage} 
	 * with the given size.
	 * 
	 * @param stream {@link InputStream} to read from.
	 * @param width destination width.
	 * @param height destination height.
	 * @return loaded image as a {@link BufferedImage}.
	 * @throws IOException
	 */
	public static BufferedImage loadImage(InputStream stream, int width, int height) throws IOException {
		BufferedImage image = new BufferedImage(1, 1, 1);
		try {
			image = ImageIO.read(stream);
		} finally { }
		Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		resized.createGraphics().drawImage(tmp, 0, 0, null);
		resized.createGraphics().dispose();
		return resized;
	}
	
//	public static File writeLineToFile(String fileName, String message) throws IOException {
//    	return writeLineToFile(new File(fileName), message);  
//    }
//
//	public static File writeLineToFile(File file, String message) throws IOException {
//        FileWriter out = new FileWriter(file, true);
//        out.write(message + "\n");
//        out.flush();
//        out.close();
//        return file;
//    }	
}
