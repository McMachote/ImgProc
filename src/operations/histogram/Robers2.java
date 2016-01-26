package operations.histogram;

import operations.Operation;

public class Robers2 extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Robers2(String img_string) throws InterruptedException {
		super(img_string);
	}

	@Override
	protected void executeOp() {
		for (int i = 0; i < this.imgCols; i++){
			for (int j=0; j < this.imgRows; j++){
				for(int k=0; k< 4;k++){
					if((i==this.imgCols-1) || (j==this.imgRows-1) || k==0){
						this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
					}
					else{
						this.threeDPixMod[i][j][k] = Math.abs(this.threeDPix[i][j][k]- this.threeDPix[i+1][j+1][k]) + Math.abs(this.threeDPix[i][j+1][k]- this.threeDPix[i+1][j][k]);
					}
				}
			}
		}
		this.save();
	}

}
