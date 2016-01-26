package operations.histogram;

import operations.Operation;

public class Variance extends Histogram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float variance;

	public Variance(String img_string, char channel) throws InterruptedException {
		super(img_string, channel);
		Mean m = new Mean(img_string, channel);
	}

	@Override
	protected void executeOp() {
		super.executeOp();
		this.getVariance();
		System.out.println(this);
	}

	private void getVariance() {
		int n = this.imgCols * this.imgRows;
		float sum = 0;
		float mean;
		int x;
		for (int i = 0; i<256; i++)
		{
			//x = (i - mea)*(i - mea);
			//sum += x * this.histogram[i];
		}
		this.variance = sum / n;
	}

	
}
