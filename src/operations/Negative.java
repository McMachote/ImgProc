package operations;

import sip.p1.Constants;

public class Negative extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Negative(String s) throws InterruptedException{
		super(s);
	}
	
	@Override
	public void executeOp() {
		System.out.println(this);
		for(int i = 0; i < this.imgRows; i++){
			for(int j = 0; j < this.imgCols; j++){
				for(int k = 0; k < 4; k++){
					if(k != 0){
						this.threeDPixMod[i][j][k] = 255 - this.threeDPix[i][j][k];
					}
					else{
						this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
					}
				}
			}
		}
		this.save();
	}

	@Override
	public String toString() {
		return Constants.executingNegativeOp;
	}

}
