package fourier;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.math3.complex.Complex;

public class Tools {
	
	private static final String format = "bmp";
	
	public static void savePhase(int[] data, int width, int height, String fileName) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		try {
			File f = new File(fileName + "_phase." + format);
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					bi.setRGB(j, i, data[i*width + j]);
				}
			}
			ImageIO.write(bi, format, f);
			System.out.println("Phase saved");
		} catch (IOException e) {
			System.err.println("errooooooor");
			e.printStackTrace();
		}
	}

	public static void saveMagnitude(int[] data, int width, int height, String fileName) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		try {
			File f = new File(fileName + "_magnitude." + format);
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					bi.setRGB(j, i, data[i*width + j]);
				}
			}
			ImageIO.write(bi, format, f);
			System.out.println("Magnitude saved");
		} catch (IOException e) {
			System.err.println("errooooooor");
			e.printStackTrace();
		}
	}

	public static int[] centralize(int[] arr, int width, int height) {
		int cosa[][] = new int[height][width]; 
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				cosa[i][j] = arr[i*width+j];
			}
		}
		int tmp;
		for(int i=0; i<height/2; i++){//horizontal swap		//XOR SWAP!!!
			for(int j=0; j<width; j++){
				tmp = cosa[i][j];
				cosa[i][j] = cosa[i+height/2][j];
				cosa[i+height/2][j] = tmp;
			}
		}
		for(int i=0; i<height; i++){//horizontal swap		//XOR SWAP!!!
			for(int j=0; j<width/2; j++){
				tmp = cosa[i][j];
				cosa[i][j] = cosa[i][j+width/2];
				cosa[i][j+width/2] = tmp;
			}
		}
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				arr[i*width + j] = cosa[i][j];
			}
		}
		return arr;
	}
	
	public static Complex[][] centralize(Complex[][] data, int width, int height) {
		Complex cosa[][] = new Complex[height][width]; 
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				cosa[i][j] = data[i][j];
			}
		}
		Complex tmp;
		for(int i=0; i<height/2; i++){//horizontal swap		//XOR SWAP!!!
			for(int j=0; j<width; j++){
				tmp = cosa[i][j];
				cosa[i][j] = cosa[i+height/2][j];
				cosa[i+height/2][j] = tmp;
			}
		}
		for(int i=0; i<height; i++){//horizontal swap		//XOR SWAP!!!
			for(int j=0; j<width/2; j++){
				tmp = cosa[i][j];
				cosa[i][j] = cosa[i][j+width/2];
				cosa[i][j+width/2] = tmp;
			}
		}
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				data[i][j] = cosa[i][j];
			}
		}
		return data;
	}

	public static int[] toIntegerArr(Complex[] row, int n) {
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = (int) Math.round(row[i].getReal());
		}
		return result;
	}
	
	public static int[] toIntegerArr(Complex[][] data, int width, int height) {
		int[] result = new int[width*height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
			//	System.out.println((int) Math.round(data[i][j].getReal()));
				int vvv = ((int) Math.floor(data[i][j].getReal()	))/(256*256);
				vvv = vvv + vvv*256 + vvv*256*256;
				result[j + i*width] = vvv;//((int) Math.round(data[i][j].getReal()))/(256*256);
			}
		}
		return result;
	}

	public static void checkArrs(int[] temp, int[] temp2, int size) {
		if(temp.length != temp2.length){
			System.err.println("Different sizes");
		}
		else{
			for(int i=0; i<size; i++){
				if(temp[i] != temp2[i]){
					System.err.println(i + ":   " + temp[i] + " " + temp2[i]);
				}
				else{
					System.out.println(i + ":   " + temp[i] + " " + temp2[i]);
				}
			}
		}
	}

	public static void checkArrs(Complex[] temp, Complex[] temp2, int size) {
		if(temp.length != temp2.length){
			System.err.println("Different sizes");
		}
		else{
			for(int i=0; i<size; i++){
				if(!Complex.equals(temp[i], temp2[i])){
					System.err.println(i + ":   " + temp[i] + " " + temp2[i]);
				}
				else{
					System.out.println(i + ":   " + temp[i] + " " + temp2[i]);
				}
			}
		}
	}

	public static Complex[][] toComplex2DArr(int[] input, int width, int height) {
		Complex[][] arr = new Complex[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				arr[i][j] = new Complex(input[i*width + j]);
			}
		}
		return arr;
	}
	
}
