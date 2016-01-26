package operations;

import sip.p1.Constants;

public class Brightness extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Brightness(String v) throws InterruptedException {
		super(v);
	}

	public void executeOp() {
		int brightness = 100;
	    for(int row = 0;row < imgRows;row++){
	    	for(int col = 0;col < imgCols;col++){
	    		threeDPixMod[row][col][0] = threeDPix[row][col][0] ;//+ brightness;
	    		threeDPixMod[row][col][1] = threeDPix[row][col][1] + brightness;
	    		threeDPixMod[row][col][2] = threeDPix[row][col][2] + brightness;
	    		threeDPixMod[row][col][3] = threeDPix[row][col][3] + brightness;
               if (threeDPixMod[row][col][0] < 0){
                	threeDPixMod[row][col][0] = 0;
                }
                else if (threeDPixMod[row][col][0] > 255)
                {
                	threeDPixMod[row][col][0] = 255;
                }
                if (threeDPixMod[row][col][1] < 0){
                	threeDPixMod[row][col][1] = 0;
                }
                else if (threeDPixMod[row][col][1] > 255)
                {
                	threeDPixMod[row][col][1] = 255;
                }
                if (threeDPixMod[row][col][2] < 0){
                	threeDPixMod[row][col][2] = 0;
                }
                else if (threeDPixMod[row][col][2] > 255)
                {
                	threeDPixMod[row][col][2] = 255;
                }
                if (threeDPixMod[row][col][3] < 0){
                	threeDPixMod[row][col][3] = 0;
                }
                else if (threeDPixMod[row][col][3] > 255)
                {
                	threeDPixMod[row][col][3] = 255;
                }
	    	}
	    }
		System.out.println(this.toString());
		this.save();
	}

	public String toString(){
		return Constants.executingBrightnessOp;
	}
	
}
