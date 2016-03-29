package operations;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public abstract class Operation extends Component {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected Image modImg;
    protected int[] oneDPix;
    protected int[][][] threeDPix;
    protected int[][][] threeDPixMod;
    protected Image rawImg;
    protected int imgCols;// Number of horizontal pixels
    protected int imgRows;// Number of rows of pixels
    protected String format, fileName;

    public Operation(String img_string) throws InterruptedException {
        this.load(img_string);
        // this.oneDPix = null;
    }

    public void performOperation() {
        executeOp();
        System.out.println("-- done");
    }

    protected abstract void executeOp();

    /**
     * Loads the pixel array on threeDPix
     */
    private void load(String img_string) throws InterruptedException {
        System.out.println(img_string);
        String[] auxName = img_string.split("/");
        String fileNameAux = auxName[auxName.length - 1];
        auxName = fileNameAux.split("\\.");
        this.fileName = auxName[0];
        this.format = auxName[1];
        BufferedImage img_buff = null;
        try {
            img_buff = ImageIO.read(getClass().getResource(img_string));
        } catch (Exception e) {
            System.err.println("File not found");
            System.exit(1);
        }
        this.rawImg = img_buff;
        this.imgCols = this.rawImg.getWidth(this);
        this.imgRows = this.rawImg.getHeight(this);
        this.oneDPix = new int[this.imgCols * this.imgRows];
        this.threeDPixMod = new int[this.imgRows][this.imgCols][4];
        PixelGrabber pgObj = new PixelGrabber(this.rawImg, 0, 0, -1, -1, false);
        if (pgObj.grabPixels()) {
            if ((pgObj.getStatus() & ImageObserver.ABORT) != 0) {
                System.err.println("image fetch aborted or errored");
                return;
            }
            try {
                this.oneDPix = (int[]) pgObj.getPixels();
            } catch (ClassCastException e) {
                WritableRaster cosa = img_buff.getRaster();
                int aux;
                for (int i = 0; i < pgObj.getHeight(); i++) {
                    for (int j = 0; j < pgObj.getWidth(); j++) {
                        aux = cosa.getSample(j, i, 0);
                        if (aux != 0) {
                            aux = 0xFFFFFFFF;
                        } else {
                            aux = 0xFF000000;
                        }
                        this.oneDPix[i * pgObj.getWidth() + j] = aux;
                    }
                }
            }
            convertToThreeDim();
        } else {
            System.out.println("Pixel grab not successful");
        }
    }

    protected void resize3DArray(int x, int y) {
        this.threeDPixMod = new int[y][x][4];
    }

    protected void resize1DArray(int x) {
        this.oneDPix = new int[x];
    }

    protected void comprobacionEstado() {
        JFrame v = new JFrame();
        JButton b = new JButton();
        b.setIcon(new ImageIcon(modImg));
        v.add(b);
        v.setSize(this.imgCols, this.imgRows);
        v.setDefaultCloseOperation(3);// EXIT_ON_CLOSE
        v.setVisible(true);
    }

    protected void save() {
        oneDPix = convertToOneDim(threeDPixMod, imgCols, imgRows);
        this.modImg = createImage(new MemoryImageSource(imgCols, imgRows, oneDPix, 0, imgCols));
        BufferedImage bi = new BufferedImage(this.imgCols, this.imgRows, BufferedImage.TYPE_INT_RGB);
        comprobacionEstado();
        try {
            File f = new File(this.fileName + "_mod." + this.format);
            for (int i = 0; i < this.imgRows; i++) {
                for (int j = 0; j < this.imgCols; j++) {
                    bi.setRGB(j, i, this.oneDPix[i * this.imgCols + j]);
                }
            }
            ImageIO.write(bi, this.format, f);
            System.out.println("-- saved");
        } catch (IOException e) {
            System.err.println("errooooooor");
            e.printStackTrace();
        }
    }

    protected void save(String name) {
        oneDPix = convertToOneDim(threeDPixMod, imgCols, imgRows);
        this.modImg = createImage(new MemoryImageSource(imgCols, imgRows, oneDPix, 0, imgCols));
        BufferedImage bi = new BufferedImage(this.imgCols, this.imgRows, BufferedImage.TYPE_INT_RGB);
        comprobacionEstado();
        try {
            File f = new File(name + this.format);
            for (int i = 0; i < this.imgRows; i++) {
                for (int j = 0; j < this.imgCols; j++) {
                    bi.setRGB(j, i, this.oneDPix[i * this.imgCols + j]);
                }
            }
            ImageIO.write(bi, this.format, f);
            System.out.println("-- saved");
        } catch (IOException e) {
            System.err.println("errooooooor");
            e.printStackTrace();
        }
    }

    protected void convertToThreeDim() {
        this.threeDPix = new int[this.imgRows][this.imgCols][4];
        for (int row = 0; row < this.imgRows; row++) {
            int[] aRow = new int[this.imgCols];
            for (int col = 0; col < this.imgCols; col++) {
                int element = row * this.imgCols + col;
                aRow[col] = this.oneDPix[element];
            }
            for (int col = 0; col < this.imgCols; col++) {
                // Alpha data
                this.threeDPix[row][col][0] = (aRow[col] >> 24) & 0xFF;
                // Red data
                this.threeDPix[row][col][1] = (aRow[col] >> 16) & 0xFF;
                // Green data
                this.threeDPix[row][col][2] = (aRow[col] >> 8) & 0xFF;
                // Blue data
                this.threeDPix[row][col][3] = (aRow[col]) & 0xFF;
            }
        }
    }

    protected int[] convertToOneDim(int[][][] data, int imgCols, int imgRows) {
        int[] oneDPix = new int[imgCols * imgRows * 4];
        for (int row = 0, cnt = 0; row < imgRows; row++) {
            for (int col = 0; col < imgCols; col++) {
                oneDPix[cnt] = ((data[row][col][0] << 24) & 0xFF000000) | ((data[row][col][1] << 16) & 0x00FF0000)
                        | ((data[row][col][2] << 8) & 0x0000FF00) | ((data[row][col][3]) & 0x000000FF);
                cnt++;
            }
        }
        return oneDPix;
    }

}
