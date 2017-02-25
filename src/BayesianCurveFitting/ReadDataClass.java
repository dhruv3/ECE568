package BayesianCurveFitting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadDataClass {
	private double[] dataValues;
	private String[] info = new String[200];
	int count = 0;
	
	public ReadDataClass(int totalDataEntries, String symbol) {
		String fileName = "TestData/myData_" + symbol + ".csv";
		dataValues = new double[totalDataEntries];
		String content = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			content = reader.readLine();
			while(content != null) {
				String[] data = content.split(",");
				info[count] = data[1];
				count++;
				content = reader.readLine();
			} 
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		} 
		for(int i = 0; i < totalDataEntries; i++) {
			dataValues[i] = Double.parseDouble(info[count - i - 1]);
		}
	}
	
	public double[] getPrices() {
		return dataValues;
	}

	public double getActualPrice(int totalDataEntries) {
		return Double.parseDouble(info[totalDataEntries]);
	}
}
