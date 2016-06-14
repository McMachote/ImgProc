package morphologicalOps;

import operations.histogram.Histogram;

public class MorphologicalOperation {

    private static int[][][] newImage(int height, int width) {
        int[][][] ret = new int[height][width][4];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 4; k++) {
                    ret[i][j][k] = 255;
                }
            }
        }
        return ret;
    }

    public static void dilation(int[][][] source, int[][][] destination, int height, int width) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k != 0 && source[i][j][k] == 0) {
                        destination[i - 1][j - 1][k] = 0;
                        destination[i - 1][j][k] = 0;
                        destination[i - 1][j + 1][k] = 0;
                        destination[i][j - 1][k] = 0;
                        destination[i][j][k] = 0; // added to m3
                        destination[i][j + 1][k] = 0;
                        destination[i + 1][j - 1][k] = 0;
                        destination[i + 1][j][k] = 0;
//                        destination[i + 1][j + 1][k] = 0;
                    } else {
//                         destination[i][j][k] = source[i][j][k]; // try
//                         destination[i][j][k] = 255; //quitted to m3
                    }
                }
            }
        }
    }

    public static void dilation2(int[][][] source, int[][][] destination, int height, int width) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k != 0 && source[i][j][k] != 255) {
                        destination[i - 1][j - 1][k] = source[i][j][k];
                        destination[i - 1][j][k] = source[i][j][k];
                        destination[i - 1][j + 1][k] = source[i][j][k];
                        destination[i][j - 1][k] = source[i][j][k];
                        destination[i][j][k] = source[i][j][k]; // added to m3
                        destination[i][j + 1][k] = source[i][j][k];
                        destination[i + 1][j - 1][k] = source[i][j][k];
                        destination[i + 1][j][k] = source[i][j][k];
                        destination[i + 1][j + 1][k] = source[i][j][k];
                    } else {
                        // destination[i][j][k] = source[i][j][k]; // try
                        // destination[i][j][k] = 255; //quitted to m3
                    }
                }
            }
        }
    }

    public static void erosion(int[][][] source, int[][][] destination, int height, int width) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k != 0 &&
                            source[i - 1][j - 1][k] == 0 &&
                            source[i - 1][j][k] == 0 &&
                            source[i - 1][j + 1][k] == 0 && 
                            source[i][j - 1][k] == 0 && 
                            source[i][j][k] == 0 && 
                            source[i][j + 1][k] == 0 && 
                            source[i + 1][j - 1][k] == 0 && 
                            source[i + 1][j][k] == 0 
                          &&  source[i + 1][j + 1][k] == 0
                            ) {
                        destination[i][j][k] = 0;
                    } else {
//                        destination[i][j][k] = 255;
                    }
                }
            }
        }
    }

    public static int[][][] m3(int[][][] img, int[][][] prev, int[][][] original, int height, int width) {
        if (!sameImgs(img, prev, height, width)) {
            // DILATION
            int[][][] tmp = newImage(height, width);
            dilation(img, tmp, height, width);
            // INTERSECTION
            int[][][] img2 = newImage(height, width);
            img2 = intersection(tmp, original, height, width);
            // RECURSIVE CALL
            return m3(img2, img, original, height, width);
        }
        return img;
    }

    public static int[][][] m3(int[][][] img, int[][][] original, int height, int width) {
        int[][][] tmp = newImage(height, width);
        dilation(img, tmp, height, width);
        int[][][] img2 = newImage(height, width);
        img2 = intersection(tmp, original, height, width);
        return m3(img2, img, original, height, width);
    }

    private static int[][][] intersection(int[][][] img1, int[][][] img2, int height, int width) {
        int[][][] result = newImage(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k == 0) {
                        result[i][j][k] = 255;
                    } else {
                        if (img1[i][j][k] == img2[i][j][k]) {
                            result[i][j][k] = img1[i][j][k];
                        }
                    }
                }
            }
        }
        return result;
    }

    private static boolean sameImgs(int[][][] source, int[][][] destination, int height, int width) {
        boolean b = true;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (source[i][j][k] != destination[i][j][k]) {
                        b = false;
                    }
                }
            }
        }
        return b;
    }

    public static int[][][] regionGrwoing(int[][][] img, int height, int width) {
        int[][][] result = newImage(height, width);
        int gapWidth = 220; // determines the number of intensity values grouped in a gap.
        // =1 => original
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k == 4) {
                        result[i][j][k] = 255;
                    } else {
                        if(img[i][j][k] != 0 && img[i][j][k] != 255){
                            result[i][j][k] = img[i][j][k] / gapWidth;
                            result[i][j][k] = result[i][j][k] * gapWidth; 
                        }
                        else{ // save 100% black and whites
                            result[i][j][k] = img[i][j][k];
                        }
                    }
                }
            }
        }
        return result;
    }

}
