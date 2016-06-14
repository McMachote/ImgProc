package morphologicalOps;

import operations.Operation;

public class Erosion extends Operation {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String img_string;

    // private int[] SE = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    public Erosion(String img_string) throws InterruptedException {
        super(img_string);
        this.img_string = img_string;
    }

    @Override
    protected void executeOp() {
        for(int i=0; i<this.imgRows;i++){
            for(int j=0; j<this.imgCols;j++){
                this.threeDPixMod[i][j][0] = 255;
                this.threeDPixMod[i][j][1] = 255;
                this.threeDPixMod[i][j][2] = 255;
                this.threeDPixMod[i][j][3] = 255;
            }
        }
        MorphologicalOperation.erosion(this.threeDPix, this.threeDPixMod, this.imgRows, this.imgCols);
        String[] aux = img_string.split("\\.");
        aux = aux[0].split("/");
        this.save(aux[aux.length-1] + "_erosion."); // format mistake => crash before
        this.comprobacionEstado();
    }

}
