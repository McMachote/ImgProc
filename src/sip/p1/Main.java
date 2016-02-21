package sip.p1;
import org.apache.commons.cli.*;
import operations.*;
import operations.geometric.*;
import operations.histogram.*;
import filters.*;
import fourier.FFT;
import fourier.DFT;
//import fourier.Fourier;

public class Main {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		try {
			Operation op = parseCommands(args);
            if(op != null)
            	op.performOperation();
            else
            	System.out.println(Constants.HELP);
		} catch(InterruptedException | ParseException e){
			System.out.println(Constants.HELP);
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println(endTime);
	}
	
	private static Operation parseCommands(String[] args) throws ParseException, InterruptedException {
		Options options = generateOption();
		CommandLineParser parser = null;
		CommandLine cmdLine      = null;
		parser  = new BasicParser();
		cmdLine = parser.parse(options, args);
		String img;
		Operation op = null;
		if(cmdLine.hasOption("-b")){
			img = cmdLine.getOptionValue("b");
			op = new Brightness(img);
		}
		if(cmdLine.hasOption("-n")){
			img = cmdLine.getOptionValue("n");
			op = new Negative(img);
		}
		if(cmdLine.hasOption("-c")){
			String aux = cmdLine.getOptionValue("c");
			float factor = Float.valueOf(aux);
			op = new Contrast(args[2], factor);
		}
		if(cmdLine.hasOption("-hf")){
			img = cmdLine.getOptionValue("hf");
			op = new HorizontalFlip(img);
		}
		if(cmdLine.hasOption("-vf")){
			img = cmdLine.getOptionValue("vf");
			op = new VerticalFlip(img);
		}
		if(cmdLine.hasOption("-df")){
			img = cmdLine.getOptionValue("df");
			op = new DiagonalFlip(img);
		}
		if(cmdLine.hasOption("-s")){
			img = cmdLine.getOptionValue("s");
			op = new Shrinking(img);
		}
		if(cmdLine.hasOption("-e")){
			img = cmdLine.getOptionValue("e");
			op = new Enlarge(img);
		}
		if(cmdLine.hasOption("-a")){
            img = cmdLine.getOptionValue("a");
            op = new AlphaTrimmed(img);
        }
		if(cmdLine.hasOption("-gmean")){
            img = cmdLine.getOptionValue("gmean");
            op = new Geometric(img);
        }
        if(cmdLine.hasOption("-mse")){
            img = cmdLine.getOptionValue("mse");
            op = new MeanSquareError(img);
        }
        if(cmdLine.hasOption("-pmse")){
            img = cmdLine.getOptionValue("pmse");
            op = new PeakMeanSquareError(img);
        }
        if(cmdLine.hasOption("-snr")){
            img = cmdLine.getOptionValue("snr");
            op = new PeakMeanSquareError(img);
        }
        if(cmdLine.hasOption("-histogram")){
        	String aux = cmdLine.getOptionValue("histogram");
			char channel = aux.toCharArray()[0];
            op = new Histogram(args[2], channel);
        }
        if(cmdLine.hasOption("-sexdetii")){
        	int [][] h = new int [3][3];
        	h[0][0] = 1; h[0][1] = 1; h[0][2] = 1;
        	h[1][0] = -1; h[1][1] = -2; h[1][2] = 1;
        	h[2][0] = -1; h[2][1] = -1; h[2][2] = 1;
        	img = cmdLine.getOptionValue("sexdetii");
            op = new Sexdetii(img, h);
        }
        if(cmdLine.hasOption("-sexdetiiconcret")){
        	img = cmdLine.getOptionValue("sexdetiiconcret");
            op = new SexdetiiConcret(img);
        }
        if(cmdLine.hasOption("-robers2")){
        	img = cmdLine.getOptionValue("robers2");
            op = new Robers2(img);
        }
        if(cmdLine.hasOption("-hpower")){ //Syntax: hpower min max img
        	int min, max;
        	min = Integer.parseInt(args[1]);
        	max = Integer.parseInt(args[2]);
            op = new Hpower(args[3], min, max);
        }
//        if(cmdLine.hasOption("-fft")){ //Syntax: hpower min max img
//        	img = cmdLine.getOptionValue("fft");
//            op = new Fourier(img);
//        }
//        if(cmdLine.hasOption("-ifft")){ //Syntax: hpower min max img
//        	img = cmdLine.getOptionValue("ifft");
//            op = new Fourier(img);
//        }
        if(cmdLine.hasOption("-dft")){ //Syntax: hpower min max img
        	img = cmdLine.getOptionValue("dft");
            op = new DFT(img);
        }
        if(cmdLine.hasOption("-fft")){ //Syntax: hpower min max img
        	img = cmdLine.getOptionValue("fft");
            op = new FFT(img);
        }
        return op;
	}

	private static Options generateOption() {
		Options options = new Options();
		options.addOption("b", "brightness" , true, "--brightness [-argument=value [...]]");
		options.addOption("n", "negative", true, "--negative [-argument=value [...]]");
		options.addOption("c", "contrast"  , true, "--contrast [-argument=value [...]]");
		options.addOption("hf", "hflip"  , true, "--hflip [-argument=value [...]]");
		options.addOption("vf", "vflip"  , true, "--vflip [-argument=value [...]]");
		options.addOption("df", "dflip"  , true, "--dflip [-argument=value [...]]");
		options.addOption("s", "shrinking"  , true, "--shrinking [-argument=value [...]]");
		options.addOption("e", "enlarge"  , true, "--enlarge [-argument=value [...]]");
		options.addOption("gmean", "geometric-mean filter"  , true, "--gmean [-argument=value [...]]");
		options.addOption("a", "alpha-trimmed"  , true, "--alpha-trimmed [-argument=value [...]]");
		options.addOption("mse", "mean-square error"  , true, "--mean-square-error [-argument=value [...]]");
		options.addOption("pmse", "peak-mean-square error"  , true, "--peak-mean-square error [-argument=value [...]]");
		options.addOption("snr", "signal-noise ratio"  , true, "--signal-noise-ratio [-argument=value [...]]");
		options.addOption("histogram", "histogram"  , true, "--histogram [-argument=value [R|G|B]]");
		options.addOption("sexdetii", "sexdetii"  , true, "--sexdetii ");
		options.addOption("sexdetiiconcret", "sexdetiiconcret"  , true, "--sexdetiiconcret ");
		options.addOption("hpower", "hpower"  , true, "--hpower min max [-argument=value [...]]");
		options.addOption("robers2", "robers2"  , true, "--robers2 [-argument=value [...]]");
//		options.addOption("ifft", "inversefourier"  , true, "--inversefourier [-argument=value [...]]");
//		options.addOption("fft2", "fourier2"  , true, "--fourier2 [-argument=value [...]]");
		options.addOption("dft", "dft"  , true, "--dft [-argument=value [...]]");
		options.addOption("fft", "fourier"  , true, "--fourier [-argument=value [...]]");
		return options;
	}

}
