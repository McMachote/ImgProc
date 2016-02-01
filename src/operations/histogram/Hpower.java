package operations.histogram;

import operations.Operation;

public class Hpower extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] histogramR, histogramG, histogramB;
	private int min, max;

	public Hpower(String img_string, int minimun, int maximun) throws InterruptedException {
		super(img_string);
		this.min = minimun;
		this.max = maximun;
		this.histogramR = new int[256];
		this.histogramG = new int[256];
		this.histogramB = new int[256];
	}
	
	@Override
	protected void executeOp() {
		this.generateHistrogram();
		int contR=0, contG=0, contB=0;
		int N = this.imgCols*this.imgRows;
		
		int [] newR = new int[256];
		int [] newG = new int[256];
		int [] newB = new int[256];
		
		for(int l=0; l<this.histogramR.length; l++){
			contR += this.histogramR[l];
			contG += this.histogramG[l];
			contB += this.histogramB[l];
			newR[l] = (int)Math.pow(( Math.pow(this.min, 1.0/3.0) +  (contR * (Math.pow(this.max, 1.0/3.0) - Math.pow(this.min, 1.0/3.0))) /N), 3);
			newG[l] = (int)Math.pow(( Math.pow(this.min, 1.0/3.0) +  (contG * (Math.pow(this.max, 1.0/3.0) - Math.pow(this.min, 1.0/3.0))) /N), 3);
			newB[l] = (int)Math.pow(( Math.pow(this.min, 1.0/3.0) +  (contB * (Math.pow(this.max, 1.0/3.0) - Math.pow(this.min, 1.0/3.0))) /N), 3);
		}
		for(int i=0; i<this.imgRows; i++){
			for(int j=0; j<this.imgCols; j++){
				this.threeDPixMod[i][j][0] = this.threeDPix[i][j][0];
				this.threeDPixMod[i][j][1] = newR[this.threeDPix[i][j][1]];
				this.threeDPixMod[i][j][2] = newG[this.threeDPix[i][j][2]];
				this.threeDPixMod[i][j][3] = newB[this.threeDPix[i][j][3]];
			}
		}
		//test
//		for(int i=0; i<256; i++){
//			System.out.print(newR[i] + " ");
//			System.out.print(newG[i] + " ");
//			System.out.print(newB[i]);
//			System.out.println();
//		}
		this.save();
	}

	private void generateHistrogram() { //single channel chosen. GRAY SCALE
		for (int i = 0; i < this.imgRows; i++) {
			for (int j = 0; j < this.imgCols; j++) {
				this.histogramR[ this.threeDPix[i][j][1] ]++;
				this.histogramG[ this.threeDPix[i][j][2] ]++;
				this.histogramB[ this.threeDPix[i][j][3] ]++;
				
//				System.out.print(this.threeDPix[i][j][1] + " ");
//				System.out.print(this.threeDPix[i][j][2] + " ");
//				System.out.println(this.threeDPix[i][j][3]);
			}
		}
	}

}
