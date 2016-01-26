package operations.histogram;

import operations.Operation;

public class Sexdetii extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int [][] h = new int [3][3];

	public Sexdetii(String img_string, int [][] h) throws InterruptedException {
		super(img_string);
		this.h = h;
	}

	@Override
	protected void executeOp() {
		int aux; 
		for ( int i = 0; i < this.imgRows; i++){
			for ( int j = 0; j< this.imgRows; j++){
				for (int k = 0; k< 4; k++){
					if(i ==this.imgCols-1 || j ==this.imgRows-1 || k ==0 || i == 0 || j == 0){
						this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
					}
					else {
						aux =
						threeDPix[i-1][j-1][k]*h[2][2] + threeDPix[i-1][j][k]*h[2][1] + threeDPix[i-1][j+1][k]*h[2][0] + 
						threeDPix[i][j-1][k]*h[1][2]+ threeDPix[i][j][k]*h[1][1] + threeDPix[i][j+1][k]*h[1][0] +
                        threeDPix[i+1][j-1][k]*h[0][2] + threeDPix[i+1][j][k]*h[0][1] + threeDPix[i+1][j+1][k]*h[0][0];
						if (aux > 255 ){
							aux = 255;
						}else if ( aux < 0){
							aux = 0;
						}
						threeDPixMod[i][j][k] = aux;
					}
				}
			}
		}
		this.save();
	}

}
