package filters;

import operations.Operation;

public class Geometric extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Geometric(String img_string) throws InterruptedException {
		super(img_string);
	}

	@Override
	protected void executeOp() {
		double EXP = 1.0/9.0;
		for (int i = 0; i < this.imgRows; i++) {
			for (int j = 0; j < this.imgCols; j++) {
				for (int k = 0; k < 4; k++) {
					if (k == 0) {
						this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
					} else {
						if (i == this.imgCols - 1 || j == this.imgRows - 1 || i == 0 || j == 0) {
							this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
						} else {
							double aux, aux1, aux2, aux3;
							aux1 = threeDPix[i - 1][j - 1][k] * threeDPix[i - 1][j][k] * threeDPix[i - 1][j + 1][k];
							aux2 = threeDPix[i][j - 1][k] * threeDPix[i][j][k] * threeDPix[i][j + 1][k];
							aux3 = threeDPix[i + 1][j - 1][k] * threeDPix[i + 1][j][k] * threeDPix[i + 1][j + 1][k];
							aux = Math.pow(aux1*aux2*aux3, EXP);
							if (aux > 255) {
								aux = 255;
							} else if (aux < 0) { //errorisimo...
								aux = 0;
							}
							this.threeDPixMod[i][j][k] = (int)aux;
						}
					}
				}
			}
		}
		this.save();
	}

}
