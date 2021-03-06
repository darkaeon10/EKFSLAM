package pathcomparison;

import java.io.File;
import java.util.ArrayList;

import util.FileLog;

import commondata.PointDouble;

public class CSVComparatorDriver {

	public static void main(String[] args) {

		// for each folder in the csvcompare folder,
		// print folder name, and determine its correct dataset
		// read all csv files:
		// count number of points, generate correct path.
		// print csv file and its accuracy

		String FOLDER_CSV_RESULT_POINTS = "csv_result_points";

		ArrayList<File> folders = CSVReader.getFolders(FOLDER_CSV_RESULT_POINTS);

		for (File datasetFolder : folders) {

			FileLog resultFile = new FileLog(datasetFolder + "/position_error.txt");
			resultFile.append("Folder: " + datasetFolder + "\r\n");
			System.out.println("Folder: " + datasetFolder);
			ArrayList<File> csvFiles = CSVReader.getCSVFilesFromFolder(datasetFolder);

			for (File csvFile : csvFiles) {

				System.out.println(csvFile.getName());
				ArrayList<PointDouble> pathToCheck = CSVReader.readCSVPoints(csvFile);
				ArrayList<PointDouble> correctPath = PathGenerator
						.generate(datasetFolder.getName(), pathToCheck.size());

				double avgPositionError = PathComparator.compare(pathToCheck, correctPath);
				resultFile.append(csvFile.getName() + "(" + pathToCheck.size() + " steps): " + avgPositionError
						+ "m\r\n");

			}

			resultFile.writeToFile();

		}

	}

}
