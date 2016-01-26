package filters;

import operations.Operation;

@SuppressWarnings("serial")
public class SignalToNoiseRatio extends Operation {
 
    public SignalToNoiseRatio(String img_string) throws InterruptedException {
        super(img_string);
    }
 
    @Override
    protected void executeOp() {
        int sum_sq = 0, sum_img1 = 0;
        double snr;
        for(int row = 0;row < imgRows;row++){
            for(int col = 0;col < imgCols;col++){
                int imag1 = (threeDPix[row][col][0] + threeDPix[row][col][1] + 
                        threeDPix[row][col][2] + threeDPix[row][col][3]) / 4;
                int imag2 = (threeDPixMod[row][col][0] + threeDPixMod[row][col][1] +
                        threeDPixMod[row][col][2] + threeDPixMod[row][col][3]) /4;
                 
                int err = imag1 - imag2;
                sum_img1 += imag1*imag1;
                sum_sq += (err * err);
            }
        }
         
        snr = 10 * Math.log10(sum_img1 / sum_sq);
        System.out.println("The snr is:" + snr);
    }
 
}