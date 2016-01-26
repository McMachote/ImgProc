package filters;

import operations.Operation;

@SuppressWarnings("serial")
public class MeanSquareError extends Operation{
     
    public MeanSquareError(String img_string) throws InterruptedException {
        super(img_string);
    }
 
    @Override
    protected void executeOp() {
        // TODO Auto-generated method stub
        int sum_sq = 0;
        double mse;
        for(int row = 0;row < imgRows;row++){
            for(int col = 0;col < imgCols;col++){
                int imag1 = (threeDPix[row][col][0] + threeDPix[row][col][1] + 
                        threeDPix[row][col][2] + threeDPix[row][col][3]) / 4;
                int imag2 = (threeDPixMod[row][col][0] + threeDPixMod[row][col][1] +
                        threeDPixMod[row][col][2] + threeDPixMod[row][col][3]) /4;
                int err = imag1 - imag2;
                sum_sq += (err * err);
            }
        }
        mse = (double)sum_sq / (imgRows * imgCols);
        System.out.println("The mse is:" + mse);
    }

}