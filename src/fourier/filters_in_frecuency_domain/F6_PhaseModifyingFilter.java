package fourier.filters_in_frecuency_domain;

import org.apache.commons.math3.complex.Complex;

import fourier.Fourier;
import operations.Operation;

public class F6_PhaseModifyingFilter extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Complex[][] data;

	public F6_PhaseModifyingFilter(String img_string) throws InterruptedException {
		super(img_string);
		Complex[][] sample = Fourier.loadSample(this.imgCols, this.imgRows, oneDPix);
		data = Fourier.dft2d(sample, this.imgCols, this.imgRows);
	}

	@Override
	protected void executeOp() {
		// TODO Auto-generated method stub
		
	}

}
