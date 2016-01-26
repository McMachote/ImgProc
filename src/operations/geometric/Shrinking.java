package operations.geometric;

import operations.Operation;
import sip.p1.Constants;

public class Shrinking extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Shrinking(String img_string) throws InterruptedException {
		super(img_string);
	}

	@Override
	public void executeOp() {
		System.out.println(this);
		//this.threeDPixMod = new int[this.imgRows/2][this.imgCols/2][4];
		for(int i = 0; i < this.imgRows; i++, i++){
			for(int j = 0; j < this.imgCols; j++, j++){
				for(int k = 0; k < 4; k++){
					this.threeDPixMod[i/2][j/2][k] = this.threeDPix[i][j][k];
				}
			}
		}
		this.save();
	}

	@Override
	public String toString() {
		return Constants.executingShrinking;
	}

}
