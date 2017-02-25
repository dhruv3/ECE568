package BayesianCurveFitting;

import java.util.Scanner;

public class MainFile {
	
	public static void main(String[] args) {
		//declare and initialize variables
		ReadDataClass dataReadObject;
		MatrixOpClass matOpObj;
		String flag = "Y";
		int m = 4; //polynomial power
		//declare and initialize variables
		
		while(flag.toUpperCase().equals("Y")) {			
			System.out.println("Choose data entries you want to test");
			Scanner sc = new Scanner(System.in);
			
			int totalDataEntries = sc.nextInt();
			//ensure that correct data entries are picked
			while(totalDataEntries <= 0 || totalDataEntries > 20) {
				System.out.println("Pick between 1 to 20");
				totalDataEntries = sc.nextInt();
			}
			//ensure that correct data entries are picked
			
			double[] testPointsArray = new double[totalDataEntries];
			for(int i = 0; i < totalDataEntries; i++) {
				testPointsArray[i] = i + 1;
			}
			
			dataReadObject = new ReadDataClass(totalDataEntries, "1");
			
			double[] dataValues = dataReadObject.getPrices();
			matOpObj = new MatrixOpClass(testPointsArray, dataValues, m);
			double estimateValue = matOpObj.getMx(totalDataEntries + 1);
			double actualPrice = dataReadObject.getActualPrice(totalDataEntries);
			double error = ((actualPrice - estimateValue)/actualPrice) * 100;
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Analysis done on Test Data");
			System.out.printf("Price predicted is: %.2f\n", estimateValue);
			System.out.printf("Actual Price is: %.2f\n", actualPrice);
			System.out.printf("Percentage error is: %.2f%%\n", error);
			System.out.println("Try Again? (Y/N)");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			flag = sc.next();
			
		}
	}
}
