package morphologicalOps;

public class MorphologicalOperation {

    public static void dilation(int[][][] source, int[][][] destination, int height, int width) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k != 0 && source[i][j][k] == 0) {
                        destination[i - 1][j - 1][k] = 0;
                        destination[i - 1][j][k] = 0;
                        destination[i - 1][j + 1][k] = 0;
                        destination[i][j - 1][k] = 0;
                        destination[i][j + 1][k] = 0;
                        destination[i + 1][j - 1][k] = 0;
                        destination[i + 1][j][k] = 0;
                        destination[i + 1][j + 1][k] = 0;
                    } else {
                        destination[i][j][k] = 255;
                    }
                }
            }
        }
    }

    public static void erosion(int[][][] source, int[][][] destination, int height, int width) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k != 0 && source[i - 1][j - 1][k] == 0 && source[i - 1][j][k] == 0
                            && source[i - 1][j + 1][k] == 0 && source[i][j - 1][k] == 0
                            && source[i][j][k] == 0 && source[i][j + 1][k] == 0
                            && source[i + 1][j - 1][k] == 0 && source[i + 1][j][k] == 0
                            && source[i + 1][j + 1][k] == 0) {
                        // default value or k
                    } else {
                        destination[i][j][k] = 255;
                    }
                }
            }
        }
    }

    
    
}
