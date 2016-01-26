package operations;

import sip.p1.Constants;

public class Contrast extends Operation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float factor;

	public Contrast(String s, float factor) throws InterruptedException{
		super(s);
		this.factor = factor;
	}
	
	@Override
	public void executeOp() {
		float contrast = this.factor;//1;
	    for(int row = 0;row < imgRows;row++){
	    	for(int col = 0;col < imgCols;col++){
	    		threeDPixMod[row][col][0] = threeDPix[row][col][0];//(int)(((((threeDPix[row][col][0] / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
	    		threeDPixMod[row][col][1] = (int)(((((threeDPix[row][col][1] / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
	    		threeDPixMod[row][col][2] = (int)(((((threeDPix[row][col][2] / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
	    		threeDPixMod[row][col][3] = (int)(((((threeDPix[row][col][3] / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
 
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

	@Override
	public String toString() {
		return Constants.executingContrastOp;
	}

}
