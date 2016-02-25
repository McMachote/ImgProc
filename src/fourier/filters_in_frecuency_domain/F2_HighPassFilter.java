package fourier.filters_in_frecuency_domain;

import org.apache.commons.math3.complex.Complex;

import fourier.Fourier;
import operations.Operation;

public class F2_HighPassFilter extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int n;
	private Complex[][] data;

	public F2_HighPassFilter(String img_string) throws InterruptedException {
		super(img_string);
		this.n = this.imgCols*this.imgRows;
		Complex[][] sample = Fourier.loadSample(this.imgCols, this.imgRows, oneDPix);
		data = Fourier.dft2d(sample, this.imgCols, this.imgRows);
	}

	@Override
	protected void executeOp() {
		// TODO Auto-generated method stub
		
	}

}
