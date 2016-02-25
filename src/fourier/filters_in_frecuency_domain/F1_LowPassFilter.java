package fourier.filters_in_frecuency_domain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.math3.complex.Complex;

import com.sun.glass.ui.CommonDialogs.Type;

import fourier.Fourier;
import fourier.Tools;
import operations.Operation;

public class F1_LowPassFilter extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Complex[][] data;
	private int n;
	private int[] magnitude;
	private int[] phase;
	private int radix;
	private Complex[][] sample;

	public F1_LowPassFilter(String img_string, int radix) throws InterruptedException {
		super(img_string);
		this.radix = radix;
		this.n = this.imgCols * this.imgRows;
		this.sample = Fourier.loadSample(this.imgCols, this.imgRows, this.oneDPix);
	}

	@Override
	protected void executeOp() {
		Complex[][] mask = this.generateMaskComplex(this.imgCols, this.imgRows, this.radix);
		this.data = Fourier.fft2d(sample, this.imgCols, this.imgRows);
	
	//	saveSpectrum(this.data, this.imgCols, this.imgRows);
		
		this.data = Tools.centralize(this.data ,this.imgCols, this.imgRows);
		
	//	saveSpectrum(this.data, this.imgCols, this.imgRows);
		
		for(int i=0; i<this.imgRows; i++){
			for(int j=0; j<this.imgCols; j++){
				//if(mask[i][j].getReal() == -1.6777216E7) { //	black -16777216	// white -1
					if (Math.sqrt(Math.pow(i-(this.imgRows/2), 2) + Math.pow(j-(this.imgCols/2), 2)) < 40 && !(i == this.imgRows/2 && j == this.imgCols/2) ) {
						
					this.data[i][j] = this.data[i][j].divide(1000000);//new Complex(-16777216E7);
				}
			}
		}
		this.data = Tools.centralize(this.data ,this.imgCols, this.imgRows);
		saveSpectrum(this.data, this.imgCols, this.imgRows);
		this.data = Fourier.idft2d(this.data, this.imgCols, this.imgRows);
		
		this.oneDPix = Tools.toIntegerArr(this.data, this.imgCols, this.imgRows);
		this.save();
	}
	
	/**
	 * Sets and saves the magnitude and the phase
	 * @param data - Fourier transformed data
	 * @param width
	 * @param height
	 */
	private void saveSpectrum(Complex[][] data, int width, int height) {
		this.magnitude = Fourier.getMagnitude(data, width, height);
		Tools.saveMagnitude( this.magnitude, width, height, this.fileName );
		this.phase = Fourier.getPhase(data, width, height);
		Tools.savePhase( this.phase, width, height, this.fileName );
	}

	private int[][] generateMask(int width, int height, int radix2) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setColor(Color.WHITE);
		g.fillOval(width / 2 - radix, height / 2 - radix, radix * 2, radix * 2);
		try {
			File f = new File("F1mask." + this.format);
			ImageIO.write(bi, this.format, f);
			System.out.println("-- mask saved");
		} catch (IOException e) {
			System.err.println("errooooooor");
			e.printStackTrace();
		}
		int[][] mask = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				mask[i][j] = bi.getRGB(j, i);
			}
		}
		return mask;
	}
	
	private Complex[][] generateMaskComplex(int width, int height, int radix2) {
		int[][] mask = generateMask(width, height, radix2);
		Complex[][] arr = new Complex[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				arr[i][j] = new Complex(mask[i][j]);
			}
		}
		return arr;
	}

	protected void save() {
		BufferedImage bi = new BufferedImage(this.imgCols, this.imgRows, BufferedImage.TYPE_INT_RGB);
		try {
			File f = new File(this.fileName + "_F1." + this.format);
			for (int i = 0; i < this.imgRows; i++) {
				for (int j = 0; j < this.imgCols; j++) {
					bi.setRGB(j, i, this.oneDPix[i * this.imgCols + j]);
				}
			}
			ImageIO.write(bi, this.format, f);
			System.out.println("-- saved");
		} catch (IOException e) {
			System.err.println("errooooooor");
			e.printStackTrace();
		}
	}

}
