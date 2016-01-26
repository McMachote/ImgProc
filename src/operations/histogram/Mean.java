package operations.histogram;

public class Mean extends Histogram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float mean_val;

	public Mean(String img_string, char channel) throws InterruptedException {
		super(img_string, channel);
	}

	@Override
	protected void executeOp() {
		super.executeOp();
		this.getMean();
		System.out.println(this);
	}

	private void getMean() {
		float aux = 0;
		for(int i=0; i<this.histogram.length; i++){
			aux += this.histogram[i] * i;
		}
		int n = this.imgCols * this.imgRows;
		this.mean_val = aux / n;
	}
	
	public String toString(){
		return "Mean: " + this.mean_val;
	}

}
