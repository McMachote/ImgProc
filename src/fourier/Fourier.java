package fourier;

import org.apache.commons.math3.complex.Complex;

public class Fourier {

	private static final Complex E = new Complex(Math.E);

	public static Complex[] loadSample(int n, int[] oneDPix) { // first row
		Complex[] row = new Complex[n];
		for (int i = 0; i < n; i++) {
			row[i] = new Complex(oneDPix[i]);
		}
		return row;
	}

	public static Complex[][] loadSample(int width, int height, int[] oneDPix) {
		Complex[][] row = new Complex[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				row[i][j] = new Complex(oneDPix[(i * height) + j]);
			}
		}
		return row;
	}

	public static void showComplexArr(Complex[] result) {
		int i = 0;
		for (Complex c : result) {
			System.out.println("element: " + i + " [a=" + c.getReal() + ", b=i*" + c.getImaginary() + "]");
			i++;
		}
	}

	public static void showComplexArr(Complex[][] result) {
		for (Complex[] c_arr : result) {
			for (Complex c : c_arr) {
				System.out.print("[a=" + c.getReal() + ", b=i*" + c.getImaginary() + "]  ");
			}
			System.out.println();
		}
	}

	public static void showComplexArr(Complex[][][] result, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(result[i][j][1] + " "); // JUST RED BY NOW
			}
			System.out.println();
		}
	}

	public static Complex W(int k, int N) {
		return E.pow(Complex.I.multiply(new Complex((-2) * Math.PI * (k / N))));
	}

	public static int[] getPhase(Complex[][] data, int witdh, int height) {
		int arr[] = new int[witdh*height];
		double max = data[0][0].getReal();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < witdh; j++) {
				if (data[i][j].getReal() > max) {
					max = data[i][j].getReal();
				}
			}
		}
		double f = 255 / (Math.log(Math.PI + Math.abs(max)));
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < witdh; j++) {
				 arr[j+i*witdh] = (int) ((int)data[i][j].getArgument()*f); // Phase = Argument
			}
		}
		return arr;
	}

	public static int[] getMagnitude(Complex[][] data, int width, int height) {
		int n = width*height;
		int arr[] = new int[n];
		double max = data[0][0].getImaginary();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (data[i][j].getImaginary() > max) {
					max = data[i][j].getImaginary();
				}
			}
		}
		double f = 255 / (Math.log(1 + Math.abs(max)));
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				arr[i*width + j] = (int) (f * Math.log(1+Math.sqrt((Math.pow(data[i][j].getReal(), 2) + Math.pow(data[i][j].getImaginary(), 2)))));
			}
		}
		arr = Tools.centralize(arr, width, height);
		return arr;
	}

	public static Complex[] fft1d(Complex[] row, int n) {
		Complex X[] = new Complex[(int) n];
		if (n > 1) {
			// RECURSIVE CALLS /////////////////////////
			Complex firstHalf[] = new Complex[(int) (n / 2)];
			Complex secondHalf[] = new Complex[(int) (n / 2)];
			for (int i = 0; i < n / 2; i++) {
				firstHalf[i] = row[i];
			}
			for (int i = 0; i < n / 2; i++) {
				secondHalf[i] = row[(int) (i + n / 2)];
			}
			firstHalf = fft1d(firstHalf, n / 2);
			secondHalf = fft1d(secondHalf, n / 2);
			// COMBINATION /////////////////////////
			for (double k = 0; k < n / 2; k++) {
				Complex exp = E.pow(Complex.I.multiply(new Complex((-2) * Math.PI * (k / n))));
				Complex temp = row[(int) k];
				X[(int) k] = temp.add(exp.multiply(firstHalf[(int) k]));
				X[(int) (k + n / 2)] = temp.subtract(exp.multiply(secondHalf[(int) k]));
			}
		} else {
			X = row;
		}
		return X;
	}
	
	public static Complex[] ifft1d(Complex[] row, int n) {
//		int n2 = n / 2;
//		complex *tabe = new complex[n2];
//		complex *tabo = new complex[n2];
//		complex e, o;
//		if (n == 1) return;
//		for (int x = 0; x < n2; ++x){
//			e = row[x]+row[x+n2];
//			o = (row[x] - row[x + n2]);
//			o=o*W(n,x);
//			tabe[x] = e;
//			tabo[x] = o;
//		}
//		
//		fft1d(tabe, n2);
//		fft1d(tabo, n2);
//
//		for (int x = 0; x < n2; ++x){
//			row[2 * x] = tabe[x];
//			row[2 * x + 1] = tabo[x];
//		}
		
		Complex X[] = new Complex[(int) n];
		if (n > 1) {
			// RECURSIVE CALLS /////////////////////////
			Complex firstHalf[]  = new Complex[n / 2];
			Complex secondHalf[] = new Complex[n / 2];
			for (int i = 0; i < n / 2; i++) {
				firstHalf[i] = row[i];
			}
			for (int i = 0; i < n / 2; i++) {
				secondHalf[i] = row[(int) (i + n / 2)];
			}
			firstHalf = fft1d(firstHalf, n / 2);
			secondHalf = fft1d(secondHalf, n / 2);
			// COMBINATION /////////////////////////
			for (double k = 0; k < n / 2; k++) {
				Complex exp = E.pow(Complex.I.multiply(new Complex((-2) * Math.PI * (k / n))));
				Complex temp = row[(int) k];
				X[(int) k] = temp.add(exp.multiply(firstHalf[(int) k]));
				X[(int) (k + n / 2)] = temp.subtract(exp.multiply(secondHalf[(int) k]));
			}
		} else {
			X = row;
		}
		return X;
	}

	public static Complex[] dft1d(Complex[] row, int n) {
		Complex[] temp = new Complex[n];
		Complex[] ret = new Complex[n];
		for (int k = 0; k < n; k++) {// EVERY OUTPUT
			Complex acum = new Complex(0);
			Complex expAux = (Complex.I.multiply(-2 * Math.PI * k / n));
			Complex exp = new Complex(0);
			for (int l = 0; l < n; l++) {// EVERY INPUT
				exp = expAux.multiply(l);
				acum = acum.add(row[l].multiply(E.pow(exp)));
			}
			temp[k] = acum;
		}

		for (int i = 0; i < n; i++) {
			ret[i] = temp[i];
		}
		return ret;
	}

	public static Complex[] idft1d(Complex[] row, int n) {
		Complex[] temp = new Complex[n];
		for (int k = 0; k < n; k++) {// EVERY OUTPUT
			Complex acum = new Complex(0);
			Complex expAux = (Complex.I.multiply(-2 * Math.PI * k / n));
			Complex exp = new Complex(0);
			for (int l = 0; l < n; l++) {// EVERY INPUT
				exp = expAux.multiply(-l);
				acum = acum.add(row[l].multiply(E.pow(exp)));
			}
			temp[k] = acum.divide(n);
		}
		return temp;
	}

	/**
	 * FIRST VERSION
	 * 
	 * @param data
	 * @param width
	 * @param height
	 * @return
	 */
	public static Complex[][] dft2d(Complex[][] data, int width, int height) {
		Complex[] arr = new Complex[width];
		// EVERY ROW
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				arr[j] = data[i][j];
			}
			arr = Fourier.dft1d(arr, width);
			for (int j = 0; j < height; j++) {// replace
				data[i][j] = arr[j];
			}
		}
		// EVERY COLUMN
		arr = new Complex[height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				arr[j] = data[j][i];
			}
			arr = dft1d(arr, height);
			for (int j = 0; j < height; j++) {
				data[j][i] = arr[j];
			}
		}
		return data;
	}
	
	public static Complex[][] idft2d(Complex[][] data, int width, int height) {
		Complex[] aux = new Complex[width];
		// EVERY ROW
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				aux[j] = data[i][j];
			}
			aux = Fourier.idft1d(aux, width);
			for (int j = 0; j < height; j++) {// replace
				data[i][j] = aux[j];
			}
		}
		// EVERY COLUMN
		aux = new Complex[height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				aux[j] = data[j][i];
			}
			aux = Fourier.idft1d(aux, height);
			for (int j = 0; j < height; j++) {
				data[j][i] = aux[j];
			}
		}
		return data;
	}

}
