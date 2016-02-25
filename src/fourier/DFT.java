package fourier;

import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.math3.complex.Complex;

import operations.Operation;

public class DFT extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Complex[][] magnitude;
	private Complex[][] phase;

	public DFT(String img_string) throws InterruptedException {
		super(img_string);
	}

	@Override
	protected void executeOp() {
		int[] temp = this.oneDPix;
		//	load as complex + DFT + spectrum
		System.out.println("Performing DFT...");
		Complex[][] data = Fourier.loadSample(this.imgCols,this.imgRows, this.oneDPix);
		Complex[][] dft_result = Fourier.dft2d(data, this.imgCols,this.imgRows);
		System.out.println("DFT finished");
		saveSpectrum(dft_result, this.imgCols, this.imgRows);
		
		//	OPERATIONS (over df_spectrum)
		
		//	IDFT + toInt
		System.out.println("Performing IDFT...");
		Complex[][] aux = Fourier.idft2d(dft_result, this.imgRows, this.imgCols);
		int[] temp2 = Tools.toIntegerArr(aux, this.imgCols, this.imgRows);
		System.out.println("IDFT finished");
		this.oneDPix = temp2;
		Tools.checkArrs(temp, temp2, this.imgCols*this.imgRows);
		this.save();
	}

	private void saveSpectrum(Complex[][] data, int width, int height) {
		int[] temp = Fourier.getMagnitude(data, width, height);
		this.magnitude = new Complex[width][height];
		for(int i=0;i<height; i++){
			for(int j=0; j<width;j++){
				this.magnitude[i][j] = new Complex(temp[i*width + j]);
			}
		}
		Tools.saveMagnitude( temp, width, height, this.fileName );
		
		int[] temp2 = Fourier.getPhase(data, width, height);
		this.phase = new Complex[width][height];
		for(int i=0;i<height; i++){
			for(int j=0; j<width;j++){
				this.phase[i][j] = new Complex(temp2[i*width + j]);
			}
		}
		Tools.savePhase( temp2, width, height, this.fileName );
	}
	
	protected void save() {
		this.modImg = createImage(new MemoryImageSource(imgCols, imgRows, oneDPix, 0, imgCols));
		BufferedImage bi = new BufferedImage(this.imgCols, this.imgRows, BufferedImage.TYPE_INT_RGB);
//		comprobacionEstado();
		try {
			File f = new File(this.fileName + "_mod." + this.format);
			for(int i = 0; i < this.imgRows; i++){
				for(int j = 0; j < this.imgCols; j++){
					bi.setRGB(j, i, this.oneDPix[i*this.imgCols + j]);
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
