package sip.p1;


public class Constants {

	public static final String LS = System.lineSeparator();
	
	public static final String HELP = "-h help" +
			Constants.LS + "-b Brightness modification (--brightness)" +
			Constants.LS + "-c Contrast modification (--contrast [factor])" +
			Constants.LS + "-n Negative (--negative)" +
			Constants.LS + "-hf Horizontal flip (--hflip)" +
			Constants.LS + "-vf Vertical flip (--vflip)" +
			Constants.LS + "-df Diagonal flip (--dflip)" +
			Constants.LS + "-s Shrinking (--shrinking)" +
			Constants.LS + "-e Enlarge (--enlarge)" +
			Constants.LS + "-a Alpha-Trimmed (--alpha-trimmed)" +
			Constants.LS + "-mse Mean-Square error (--mean-square)" +
			Constants.LS + "-pmse Peak-Mean-Square error (--peak-mean-square error)" +
			Constants.LS + "-snr Signal-Noise ratio (--signal-noise-ratio)";
	
	public static final String imageNotFound = "404 - Image not found.";
	
	public static final String executingBrightnessOp   = "Performing brightness modification...";
	public static final String executingContrastOp     = "Performing contrast modification...";
	public static final String executingNegativeOp     = "Performing negative modification...";
	public static final String executingHorizontalFlip = "Performing horizontal flip...";
	public static final String executingVerticalFlip   = "Performing vertical flip...";
	public static final String executingDiagonalFlip   = "Performing diagonal flip...";
	public static final String executingShrinking      = "Performing shrinking...";
	public static final String executingEnlarge        = "Performing enlarge...";
	public static final String processingHistogram     = "Processing histogram...";
	
	
}
