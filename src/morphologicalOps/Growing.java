package morphologicalOps;

import operations.Operation;

public class Growing extends Operation {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Growing(String img_string) throws InterruptedException {
        super(img_string);
    }

    @Override
    protected void executeOp() {
        
        this.threeDPixMod = MorphologicalOperation.regionGrwoing(this.threeDPix, this.imgRows, this.imgCols);
        this.save(this.fileName + "_growing.");
    }

}
