package fourier;

import org.apache.commons.math3.complex.Complex;

import operations.Operation;

public class FFT extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FFT(String img_string) throws InterruptedException {
		super(img_string);
	}

	@Override
	protected void executeOp() {
		//	FFT
		System.out.println("Performing FFT...");
		Complex[][] sample = Fourier.loadSample(this.imgCols, this.imgRows, this.oneDPix);
		Complex[][] fft_result = Fourier.fft2d(sample, this.imgCols, this.imgRows);
		System.out.println("FFT finished");
		
		//	OPERATIONS (over df_spectrum)
		
		//	IFFT
//		System.out.println("Performing IFFT...");
//		Complex[] i_fft_result = Fourier.ifft1d(fft_result, this.imgCols);
//		System.out.println("IFFT finished");
		
		
		////////////////Checking
//		Complex[][] dft_result = Fourier.dft2d(sample, this.imgCols, this.imgRows);
//		
		int[] temp = Tools.toIntegerArr(fft_result, this.imgCols, this.imgRows);
//		int[] temp2 = Tools.toIntegerArr(dft_result, this.imgCols, this.imgRows);
//		Tools.checkArrs(temp2, temp, this.imgCols);
		saveSpectrum(fft_result, this.imgCols, this.imgRows);
//		this.save();
	}

	private void saveSpectrum(Complex[][] data, int width, int height) {
		Tools.saveMagnitude( Fourier.getMagnitude(data, width, height), width, height, this.fileName );
		Tools.savePhase( Fourier.getPhase(data, width, height), width, height, this.fileName );
	}

}

