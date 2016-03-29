package morphologicalOps;

import operations.Operation;

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
        int[][][]aux = new int[this.imgRows][this.imgCols][4];
        for(int i=0; i<this.imgRows; i++){
            for(int j=0; j<this.imgCols; j++){
                for(int k=0; k<4; k++){
                    aux[i][j][k] = this.threeDPixMod[i][j][k];
                }
            }
        }
        this.save(this.fileName + "_hmt."); // format mistake => crash before
        this.comprobacionEstado();
    }

}
