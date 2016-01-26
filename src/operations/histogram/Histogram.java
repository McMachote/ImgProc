package operations.histogram;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import operations.Operation;
import sip.p1.Constants;

public class Histogram extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int channel_mask;
	protected int[] histogram;
	private int[] histogramImg;////////////////////
	private float mean_val;
	private float variance;
	private float standar_deviation;
	private float variation_coefficient;
	private float asymmetry_coefficient;
	private float flattering_coefficient;
	private float variation_coefficient2;
	private float information_source_entropy;

	/**
	 * RGB
	 * @param img_string
	 * @param channel
	 * @throws InterruptedException
	 */
	public Histogram(String img_string, char channel) throws InterruptedException {
		super(img_string);
		this.histogram = new int[256];
		this.histogramImg = new int[256];
		switch (channel) {
		case 'r':
		case 'R':
			this.channel_mask = 1;
			break;
		case 'g':
		case 'G':
			this.channel_mask = 2;
			break;
		case 'b':
		case 'B':
			this.channel_mask = 3;
			break;
		default:
			break;
		}
	}

	@Override
	protected void executeOp() {
		for (int i = 0; i < this.imgRows; i++) {
			for (int j = 0; j < this.imgCols; j++) {
				for (int k = 0; k < 4; k++) {
					if (k == 0) {
						this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
						//this.histogramImg[this.threeDPixMod[i][j][k]]++;
					}
					if (k == this.channel_mask) {
						this.threeDPixMod[i][j][k] = this.threeDPix[i][j][k];
						this.histogram[this.threeDPixMod[i][j][k]]++;
					}
				}
			}
		}
		this.stats();
		this.generateGraphic();
		this.save();
		System.out.println(this);
	}

	private void generateGraphic() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		final String histo = "Histogram";
		for(int i=0; i<this.histogram.length; i++){
			dataset.addValue(this.histogram[i] , Integer.toString(i) , Integer.toString(i));
		}
		JFreeChart barChart = ChartFactory.createBarChart("", "", "L", dataset,
				PlotOrientation.VERTICAL, false, true, false);
		int width = 640;
		int height = 480;
		File BarChart = new File("BarChart.jpeg");
		try {
			ChartUtilities.saveChartAsJPEG(BarChart, barChart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stats() {
		this.calculateMean();
		this.calculateVariance();
		this.calculateStandarDeviation();
		this.calculateVariationCoefficient();
		this.calculateAsymmetryCoefficient();
		this.calculateFlatteringCoefficient();
		this.calculateVariationCoefficient2();
		this.calculateInformationSourceEntropy();
	}

	@Override
	public String toString() {
		return Constants.processingHistogram + System.lineSeparator() + "Mean: " + this.mean_val
				+ System.lineSeparator() + "Variance: " + this.variance + System.lineSeparator() + "Standar deviation: "
				+ this.standar_deviation + System.lineSeparator() + "Variation coefficient: "
				+ this.variation_coefficient + System.lineSeparator() + "Asymetry coefficient: "
				+ this.asymmetry_coefficient + System.lineSeparator() + "Flattering coefficient: "
				+ this.flattering_coefficient + System.lineSeparator() + "Variation coefficient 2: "
				+ this.variation_coefficient2 + System.lineSeparator() + "Information source entropy: "
				+ this.information_source_entropy;
	}

	private void calculateMean() {
		float aux = 0;
		for (int i = 0; i < this.histogram.length; i++) {
			aux += this.histogram[i] * i;
		}
		this.mean_val = aux / (this.imgCols * this.imgRows);
	}

	private void calculateVariance() {
		float x = 0;
		for (int i = 0; i < 256; i++) {
			x += Math.pow((this.histogram[i] - this.mean_val), 2);
		}
		this.variance = x / (this.imgCols * this.imgRows);
	}

	private void calculateStandarDeviation() {
		this.standar_deviation = (float) Math.sqrt(this.variance);
	}

	private void calculateVariationCoefficient() {
		this.variation_coefficient = this.standar_deviation / this.mean_val;
	}

	private void calculateAsymmetryCoefficient() {
		float acum = 0;
		int N = this.imgCols * this.imgRows;
		for (int i = 0; i < this.histogram.length; i++) {
			acum += Math.pow((i - this.mean_val), 3) * this.histogram[i];
		}
		this.asymmetry_coefficient = (float) (1 / Math.pow(this.standar_deviation, 3)) * 1 / N * acum;
	}

	private void calculateFlatteringCoefficient() {
		float acum = 0;
		int N = this.imgCols * this.imgRows;
		for (int i = 0; i < this.histogram.length; i++) {
			acum += Math.pow((i - this.mean_val), 4) * this.histogram[i] - 3;
		}
		this.flattering_coefficient = (float) (1 / Math.pow(this.standar_deviation, 4)) * 1 / N * acum;
	}

	private void calculateVariationCoefficient2() {
		float acum = 0;
		int N = this.histogram.length;
		for (int i = 0; i < N; i++) {
			acum += Math.pow(this.histogram[i], 2);
		}
		this.variation_coefficient2 = (float) (Math.pow(1 / N, 2) * acum);
	}

	private void calculateInformationSourceEntropy() {
		float acum = 0, aux;
		int N = this.histogram.length;
		for (int i = 0; i < N; i++) {
			aux = this.histogram[i];
			if (aux > 0) {
				acum += aux * Math.log10(aux / N) / Math.log10(2);
			}
		}
		this.information_source_entropy = (-1 / N) * acum;
	}

}
