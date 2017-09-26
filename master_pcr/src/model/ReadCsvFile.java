package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class ReadCsvFile {
	private String pathFile;
	private MonitoringData data;
	private ArrayList dataList;

	public static void readCSV() throws FileNotFoundException, IOException {
		String fileCSV = "";
		fileCSV = "execucoes/mediaTodas.csv";
		CSVReader reader;
		reader = new CSVReader(new FileReader(fileCSV), ',', '&', 1);
		String[] line = null;
		List allRows = reader.readAll();
		double media = 0;
		int countProgram = 0;
		for (Object row : allRows) {
			line = (String[]) row;
			int countLine = 0;
			for (String x : line) {
				System.out.print(x + " - ");
				countLine++;
			}
			if (line[26].equals("\"Trinity\"")) {
				countProgram++;
			}
			System.out.println("\n");
		}
		System.out.println(countProgram);
		media = media / allRows.size();
		// System.out.println(media);
		reader.close();
	}
}
