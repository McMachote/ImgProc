package morphologicalOps;

import operations.Operation;

/**
 * Used same mask than in the example of pdf (lecture 9, pag.39)
 * 
 *      W G W 
 *      R G W 
 *      G g W
 * 
 *      B = (B1, B2)
 *      B1 = Green (G + g) g = o 
 *      B2 = Red (R) 
 *      W = White (inactive
 * squares)
 * 
 * @author jm
 *
 */
public class HMTtransform extends Operation {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HMTtransform(String img_string) throws InterruptedException {
        super(img_string);
    }

    @Override
    protected void executeOp() {
        for (int i = 1; i < this.imgRows-1; i++) {
            for (int j = 1; j < this.imgCols-1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k == 0) {
                        threeDPixMod[i][j][k] = threeDPix[i][j][k];
                    } else {
                        if (threeDPix[i-1][j][k] == 0 && threeDPix[i][j][k] == 0 && threeDPix[i+1][j][k] == 0 && threeDPix[i+1][j+1][k] == 0 && threeDPix[i][j-1][k] == 255) {//green =0, red = 255
                            threeDPixMod[i][j-1][k] = 0;
                        } else {
                            threeDPixMod[i][j-1][k] = 255;
                        }
                    }
                }
            }
        }
        this.save(this.fileName + "_hmt."); // format mistake => crash before
        this.comprobacionEstado();
    }

}
