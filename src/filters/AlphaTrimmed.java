package filters;

import operations.Operation;

@SuppressWarnings("serial")
public class AlphaTrimmed extends Operation {
 
    public AlphaTrimmed(String img_string) throws InterruptedException {
        super(img_string);
        // TODO Auto-generated constructor stub
    }
 
    @Override
    protected void executeOp() {
        // TODO Auto-generated method stub
        for(int row = 1;row < imgRows-1;row++){
            for(int col = 1;col < imgCols-1;col++){
                threeDPixMod[row][col][0] = 
                        (threeDPix[row-1][col][0]+ threeDPix[row][col-1][0] + 
                                threeDPix[row+1][col][0] + threeDPix[row][col+1][0] + 
                                threeDPix[row-1][col-1][0] + threeDPix[row+1][col+1][0] +
                                threeDPix[row-1][col+1][0]+ threeDPix[row+1][col-1][0])/8;//+ brightness;
                 
                threeDPixMod[row][col][1] =  
                        (threeDPix[row-1][col][1]+ threeDPix[row][col-1][1] + 
                                threeDPix[row+1][col][1] + threeDPix[row][col+1][1] + 
                                threeDPix[row-1][col-1][1] + threeDPix[row+1][col+1][1] +
                                threeDPix[row-1][col+1][1]+ threeDPix[row+1][col-1][1])/8;
                 
                threeDPixMod[row][col][2] =  
                        (threeDPix[row-1][col][2]+ threeDPix[row][col-1][2] + 
                                threeDPix[row+1][col][2] + threeDPix[row][col+1][2] + 
                                threeDPix[row-1][col-1][2] + threeDPix[row+1][col+1][2] +
                                threeDPix[row-1][col+1][2]+ threeDPix[row+1][col-1][2])/8;
                 
                threeDPixMod[row][col][3] =  
                        (threeDPix[row-1][col][3]+ threeDPix[row][col-1][3] + 
                                threeDPix[row+1][col][3] + threeDPix[row][col+1][3]+ 
                                threeDPix[row-1][col-1][3] + threeDPix[row+1][col+1][3] +
                                threeDPix[row-1][col+1][3]+ threeDPix[row+1][col-1][3])/8;
                
            }
        }
		this.save();
    }

}
