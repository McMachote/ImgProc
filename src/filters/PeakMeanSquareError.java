package filters;

import operations.Operation;

public class PeakMeanSquareError extends Operation{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PeakMeanSquareError(String img_string) throws InterruptedException {
        super(img_string);
    }
 
    @Override
    protected void executeOp() {
        // TODO Auto-generated method stub
        int sum_sq = 0;
        double pmse;
        int max= (threeDPix[0][0][0] + threeDPix[0][0][1] + 
                threeDPix[0][0][2] + threeDPix[0][0][3]) / 4;;
        for(int row = 0;row < imgRows;row++){
            for(int col = 0;col < imgCols;col++){
                 
                int imag1 = (threeDPix[row][col][0] + threeDPix[row][col][1] + 
                        threeDPix[row][col][2] + threeDPix[row][col][3]) / 4;
                int imag2 = (threeDPixMod[row][col][0] + threeDPixMod[row][col][1] +
                        threeDPixMod[row][col][2] + threeDPixMod[row][col][3]) /4;
                 
                if ( max < imag1){
                    max = imag1;
                }
                int err = imag1 - imag2;// /(max*max)
                sum_sq += (err * err);
            }
        }
        pmse = (double)sum_sq / (imgRows * imgCols)*(max*max);
        System.out.println("The pmse is:" + pmse);
    }
 
}
