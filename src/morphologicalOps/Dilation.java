package morphologicalOps;

import operations.Operation;

public class Dilation extends Operation {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String img_string;

    public Dilation(String img_string) throws InterruptedException {
        super(img_string);
        this.img_string = img_string;
    }

    @Override
    protected void executeOp() {
        MorphologicalOperation.dilation(this.threeDPix, this.threeDPixMod, this.imgRows, this.imgCols);
        String[] aux = img_string.split("\\.");
        aux = aux[0].split("/");
        this.save(aux[aux.length-1] + "_dilation."); // format mistake => crash before
        this.comprobacionEstado();
    }

}
