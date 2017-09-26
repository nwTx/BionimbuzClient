package resources;

import java.util.ArrayList;

import Jama.Matrix;
import Jama.QRDecomposition;
import model.MonitoringData;

public class MultipleLinearRegression {
	private int N = 0; // number of
	private int p = 0; // number of dependent variables
	private Matrix beta = null; // regression coefficients
	private double SSE; // sum of squared
	private double SST; // sum of squared
	private double[][] x;
	private double[] y;

	public void MultipleLinearRegressionCalc() {

		if (x.length != y.length) {
			throw new RuntimeException("dimensions don't agree");
		}
		N = y.length;
		p = x[0].length;

		Matrix X = new Matrix(x);

		// create matrix from vector
		Matrix Y = new Matrix(y, N);

		// find least squares solution
		QRDecomposition qr = new QRDecomposition(X);
		beta = qr.solve(Y);

		// mean of y[] values
		double sum = 0.0;
		for (int i = 0; i < N; i++) {
			sum += y[i];
		}
		double mean = sum / N;

		// total variation to be accounted for
		for (int i = 0; i < N; i++) {
			double dev = y[i] - mean;
			SST += dev * dev;
		}

		// variation not accounted for
		Matrix residuals = X.times(beta).minus(Y);
		SSE = residuals.norm2() * residuals.norm2();
	}

	public double beta(int j) {
		return beta.get(j, 0);
	}

	public double R2() {
		return 1.0 - SSE / SST;
	}

	public void showRegressionString() {
		System.out.println("Regressão:\n ( " + beta(0) + " + nReads * " + beta(1) + " + sizeFile * "
				+ beta(2) + " + cpuUsage * " + beta(3) + " + nCpu * " + beta(4) + " )" 
				+ "\n R^2 = " + R2());
	}

	public void dataPreparation(ArrayList<MonitoringData> dadosR) {
		System.out.println("N. de obs. para regressão: " + dadosR.size());
		int linhas = dadosR.size();
		int colunas = 5;
		int coluna = 0;
		x = new double[linhas][colunas];
		y = new double[linhas];
		for (int linha = 0; linha < linhas; linha++) {
			x[linha][coluna++] = 1;
			x[linha][coluna++] = Math.log10(dadosR.get(linha).getnReads());
			x[linha][coluna++] = Math.log10(dadosR.get(linha).getSizeFile());
			// x[linha][coluna] = Math.log10(dadosR.get(linha).getnProcs());
			x[linha][coluna++] = Math.log10(dadosR.get(linha).getCpuUsage());
			x[linha][coluna++] = Math.log10(dadosR.get(linha).getnCpu());
			y[linha] = Math.log10(dadosR.get(linha).getTime());
			coluna = 0;
		}
		
	}
	
	public void createRegression(ArrayList<MonitoringData> dadosR){
		dataPreparation(dadosR);
		MultipleLinearRegressionCalc();
		showRegressionString();
	}
	
	public double calcEstimate(MonitoringData params){
		return beta(0) 
				+ (Math.log10(params.getnReads()) * beta(1))
				+ (Math.log10(params.getSizeFile()) * beta(2)) 
				+ (Math.log10(params.getCpuUsage()) * beta(3)) 
				+ (Math.log10(params.getnCpu()) * beta(4));
	}
	
	public void estimateValue(MonitoringData params){
//		System.out.println("Regressão:\n ( " + beta(0) + " + " + params.getnReads() + " * " + beta(1) + " + "
//				+ params.getSizeFile() + " * " + beta(2) + " + " + params.getCpuUsage() + " * " + beta(3) + " + "
//				+ params.getnCpu() + " * "+ beta(4) + " )\n Estimate: " + Math.pow(10,calcEstimate(params)));
		System.out.println("Estimate Value: "+(int) (Math.pow(10,calcEstimate(params))- 0) +
				" Real value: " + (int) params.getTime() + 
				" Error: " + (int)( Math.abs( params.getTime() - Math.pow(10,calcEstimate(params))) - 0));
	}
	
	public double getEstimateValue(MonitoringData params){
		return  (int) Math.pow(10,calcEstimate(params));
	}
}
