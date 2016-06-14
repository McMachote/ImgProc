package morphologicalOps;

import operations.Operation;

public class M3 extends Operation {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public M3(String img_string) throws InterruptedException {
        super(img_string);
    }

    @Override
    protected void executeOp() {
        int x = 189, y = 120;
        // generate initial image X0 in the destination image
        int[][][] initialPoint = new int[this.imgRows][this.imgCols][4];

        for (int i = 0; i < this.imgRows; i++) {
            for (int j = 0; j < this.imgCols; j++) {
                for (int k = 0; k < 4; k++) {
                    initialPoint[i][j][k] = 255;
                }
            }
        }
        // set initial point in X0
        initialPoint[y][x][0] = this.threeDPix[y][x][0];
        initialPoint[y][x][1] = this.threeDPix[y][x][1];
        initialPoint[y][x][2] = this.threeDPix[y][x][2];
        initialPoint[y][x][3] = this.threeDPix[y][x][3];

        this.threeDPixMod = MorphologicalOperation.m3(initialPoint, this.threeDPix, this.imgRows, this.imgCols);
        
        this.save(this.fileName + "_m3.");
    }

}
