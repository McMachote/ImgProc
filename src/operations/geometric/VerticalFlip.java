package operations.geometric;

import operations.Operation;
import sip.p1.Constants;

public class VerticalFlip extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VerticalFlip(String img) throws InterruptedException {
		super(img);
	}

	@Override
	public void executeOp() {
		System.out.println(this);
		for(int i = 0; i < this.imgRows; i++){
			for(int j = 0; j < this.imgCols; j++){
				for(int k = 0; k < 4; k++){
					this.threeDPixMod[i][j][k] = this.threeDPix[this.imgRows - i - 1][j][k];
				}
			}
		}
		this.save();
	}

	@Override
	public String toString() {
		return Constants.executingVerticalFlip;
	}

}
