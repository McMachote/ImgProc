package operations.geometric;

import operations.Operation;
import sip.p1.Constants;

public class Enlarge extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Enlarge(String img_string) throws InterruptedException {
		super(img_string);
	}

	@Override
	public void executeOp() {
		System.out.println(this);
		resize3DArray(this.imgRows*2, this.imgCols*2);
		for(int i = 0; i < this.imgRows; i++){
			for(int j = 0; j < this.imgCols; j++){
				for(int k = 0; k < 4; k++){
					this.threeDPixMod[i*2][j*2][k] = this.threeDPix[i][j][k];
					this.threeDPixMod[i*2+1][j*2][k] = this.threeDPix[i][j][k];
					this.threeDPixMod[i*2][j*2+1][k] = this.threeDPix[i][j][k];
					this.threeDPixMod[i*2+1][j*2+1][k] = this.threeDPix[i][j][k];
				}
			}
		}
		resize1DArray(this.imgCols*this.imgRows);
		this.imgCols *= 2;
		this.imgRows *= 2;
		this.save();
	}

	@Override
	public String toString() {
		return Constants.executingEnlarge;
	}

}
