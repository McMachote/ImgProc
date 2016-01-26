package operations.histogram;

import operations.Operation;

public class SexdetiiConcret extends Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SexdetiiConcret(String img_string) throws InterruptedException {
		super(img_string);
	}

	@Override
	protected void executeOp() {
		// ( 1, 1,1)
		// (-1,-2,1)  Matrix choosen
		// (-1,-1,1)
		int aux;
		for ( int i = 0; i < this.imgRows; i++){
			for ( int j = 0; j< this.imgRows; j++){
				for (int k = 0; k< 4; k++){
					if(i ==this.imgCols-1 || j ==this.imgRows-1 || k ==0 || i == 0 || j == 0){
						this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
					}
					else {
						aux =
						threeDPix[i-1][j-1][k] - threeDPix[i-1][j][k] - threeDPix[i-1][j+1][k] + 
						threeDPix[i][j-1][k]+ threeDPix[i][j][k]*(-2) - threeDPix[i][j+1][k] +
                        threeDPix[i+1][j-1][k] + threeDPix[i+1][j][k] + threeDPix[i+1][j+1][k];
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
