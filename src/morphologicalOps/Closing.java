package morphologicalOps;

import operations.Operation;

public class Closing extends Operation {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String img_string;

    public Closing(String img_string) throws InterruptedException {
        super(img_string);
        this.img_string = img_string;
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
        
        MorphologicalOperation.dilation(this.threeDPix, aux, this.imgRows, this.imgCols);
        MorphologicalOperation.erosion(aux, this.threeDPixMod, this.imgRows, this.imgCols);
        
        String[] auxS = img_string.split("\\.");
        auxS = auxS[0].split("/");
        this.save(auxS[auxS.length-1] + "_closing."); // format mistake => crash before
        this.comprobacionEstado();
    }

}
