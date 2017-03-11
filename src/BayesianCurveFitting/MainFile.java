package BayesianCurveFitting;

import java.util.Scanner;

public class MainFile {
	
	public static void main(String[] args) {
		//declare and initialize variables
		ReadDataClass dataReadObject;
		MatrixOpClass matOpObj;
		String flag = "Y";
		int m = 5; //polynomial power
		double actualPrice = 0.0;
		double error = 0.0;
		//declare and initialize variables
		Scanner sc = new Scanner(System.in);

		while(flag.toUpperCase().equals("Y")) {			
			String[] dataSet = {"data1","data2","data3","data4","data5","Amazon","Facebook","Google","Twitter","Yahoo"};
			System.out.println("Please select a data-set:");
			for(int i = 0; i < dataSet.length; i++){
				System.out.println((i+1) + ". " + dataSet[i]);
			}
			int dataSetChosen = sc.nextInt();
			int totalDataEntries = 9;
			
			if(dataSetChosen > 5){
				System.out.println("Choose data entries you want to test");
				totalDataEntries = sc.nextInt();
				m = 11;
				//ensure that correct data entries are picked
				while(totalDataEntries <= 0 || totalDataEntries > 253) {
					System.out.println("Pick between 1 to 253");
					totalDataEntries = sc.nextInt();
				}
				//ensure that correct data entries are picked
			}
			
			double[] testPointsArray = new double[totalDataEntries];
			for(int i = 0; i < totalDataEntries; i++) {
				testPointsArray[i] = i + 1;
			}
			
			dataReadObject = new ReadDataClass(totalDataEntries, dataSet[dataSetChosen - 1]);
			
			double[] dataValues = dataReadObject.getPrices();
			matOpObj = new MatrixOpClass(testPointsArray, dataValues, m);
			double estimateValue = matOpObj.getMx(totalDataEntries + 1);
			
			if(dataSetChosen > 5){
				actualPrice = dataReadObject.getActualPrice(totalDataEntries);
				error = ((actualPrice - estimateValue)/actualPrice) * 100;
			}
			
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Analysis done on Test Data");
			System.out.printf("Price predicted is: %.2f\n", estimateValue);
			if(dataSetChosen > 5){
				System.out.printf("Actual Price is: %.2f\n", actualPrice);
				System.out.printf("Absolute Price is: %.2f\n", Math.abs(actualPrice - estimateValue));
				System.out.printf("Percentage error is: %.2f%%\n", error);
			}
			System.out.println("Try Again? (Y/N)");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			flag = sc.next();
			
		}
	}
}
