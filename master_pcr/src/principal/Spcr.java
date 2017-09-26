package principal;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import model.MonitoringData;
import resources.MultipleLinearRegression;
import resources.generateGraph;

import com.opencsv.CSVReader;

public class Spcr {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String teste = "michel";
		String oi = "junio";
		teste = teste.concat(oi);
		System.out.println(teste);
		MultipleLinearRegression regression = new MultipleLinearRegression();
		ArrayList<MonitoringData> dadosR = readCSV();
		ArrayList<MonitoringData> dadosTest = readCSVTest();
		
		for (int i = 0; i < dadosR.size(); i++) {
			dadosR.get(i).toString();
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		regression.createRegression(dadosR);
		String caractere;
		for (int i = 0; i < dadosTest.size(); i++) {
			regression.estimateValue(dadosTest.get(i));
			dataset.addValue(((Number)regression.getEstimateValue(dadosTest.get(i))), "Estimated Value", Integer.toString(i));
			dataset.addValue(((Number)dadosTest.get(i).getTime()), "Real Value", Integer.toString(i));
		}
//		regression = new MultipleLinearRegression();
//		int n = 0;
//		while (n < 20) {
//			dadosR.add(dadosTest.get(n));
//			n++;
//		}
//		regression.createRegression(dadosR);
//		for (int i = 9; i < dadosTest.size(); i++) {
//			regression.estimateValue(dadosTest.get(i));
//			dataset.addValue(((Number)regression.getEstimateValue(dadosTest.get(i))), "Estimated Value ADD", Integer.toString(i-9));
//		}
		
//		generateGraph chart = new generateGraph("Time Real Vs Time Estimated", "Time Real Vs Time Estimated", dataset);
//		chart.pack();
//		RefineryUtilities.centerFrameOnScreen(chart);
//		chart.setVisible(true);

		// System.out.println("Regressão:\n ( " + regression.beta(0) + " +
		// nReads * " + regression.beta(1) + " + sizeFile * "
		// + regression.beta(2) + " + cpuUsage * " + regression.beta(3) + " +
		// nCpu * " + regression.beta(4) + " )"
		// + "\n R^2 = " + regression.R2());

	}

	public static ArrayList<MonitoringData> readCSV() throws FileNotFoundException, IOException {
		ArrayList<MonitoringData> dadosAll = new ArrayList();
		String fileCSV = "";
//		fileCSV = "execucoes/mediaTodas.csv";
		//fileCSV = "execucoes/testeMedia.csv";
		fileCSV = "execucoes/sintetico.csv";
		
		CSVReader reader;
		reader = new CSVReader(new FileReader(fileCSV), ',', '&', 1);
		String[] line = null;
		List allRows = null;
		allRows = reader.readAll();
		double media = 0;
		int countProgram = 0;

		for (Object row : allRows) {
			MonitoringData dados = new MonitoringData();
			
			line = (String[]) row;
			int countLine = 0;
			for (String x : line) {
				// System.out.print(x + " - ");
				countLine++;
			}
			// System.out.println("\n" + Double.parseDouble(line[1]) + "
			// Teste");
//			if (line[26].equals("\"Trinity\"")) {
//				countProgram++;
//			}
			// System.out.println("\n");
//			dados.setTime(Double.parseDouble(line[1]));
//			dados.setCpuUsage(Double.parseDouble(line[2]) + Double.parseDouble(line[3]));
//			dados.setnReads(Double.parseDouble(line[27]));
//			dados.setSizeFile(Double.parseDouble(line[29]));
//			dados.setnCpu(Double.parseDouble(line[28]));
//			dados.setnProcs(Double.parseDouble(line[23]));
			
			dados.setTime(Double.parseDouble(line[1]));
			dados.setCpuUsage(Double.parseDouble(line[10]));
			dados.setnReads(Double.parseDouble(line[7]));
			dados.setSizeFile(Double.parseDouble(line[9]));
			dados.setnCpu(Double.parseDouble(line[8]));
//			dados.setnProcs(Double.parseDouble(line[]));			

		}
		// System.out.println(countProgram);
		media = media / allRows.size();
		// System.out.println(Arrays.toString(dadosAll.toArray()));
		System.out.println("N. de obs.: " + dadosAll.size());
		reader.close();
		return dadosAll;
	}

	public static ArrayList<MonitoringData> readCSVTest() throws FileNotFoundException, IOException {
		ArrayList<MonitoringData> dadosAll = new ArrayList();
		String fileCSV = "";
		// fileCSV =
		// "C:/Users/Samsung/Desktop/trinity/SR38Trinity/mediaTrinity.csv";
		//fileCSV = "C:/Users/Samsung/Desktop/trinity/SR38Trinity/mediaTrinity.csv";
//		fileCSV = "execucoes/testeMedia.csv";		
		fileCSV = "execucoes/mediaTodas.csv";
		CSVReader reader;
		reader = new CSVReader(new FileReader(fileCSV), ',', '&', 1);
		String[] line = null;
		List allRows = null;
		allRows = reader.readAll();
		double media = 0;
		int countProgram = 0;

		for (Object row : allRows) {
			MonitoringData dados = new MonitoringData();

			line = (String[]) row;
			int countLine = 0;
			for (String x : line) {
				// System.out.print(x + " - ");
				countLine++;
			}
			// System.out.println("\n" + Double.parseDouble(line[1]) + "
			// Teste");
			if (line[26].equals("\"Trinity\"")) {
				countProgram++;
			}
			// System.out.println("\n");
			dados.setCpuUsage(Double.parseDouble(line[2]) + Double.parseDouble(line[3]));
			dados.setnReads(Double.parseDouble(line[27]));
			dados.setSizeFile(Double.parseDouble(line[29]));
			dados.setnCpu(Double.parseDouble(line[28]));
			dados.setnProcs(Double.parseDouble(line[23]));
			dados.setTime(Double.parseDouble(line[1]));
			dadosAll.add(dados);
		}
		// System.out.println(countProgram);
		media = media / allRows.size();
		// System.out.println(Arrays.toString(dadosAll.toArray()));
		System.out.println("N. de obs. de teste: " + dadosAll.size());
		reader.close();
		return dadosAll;
	}
}
