package XorUsingBP;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
//https://www.youtube.com/watch?v=aVId8KMsdUU
class XORBackProp {

	static double w_11 = getRandWeight()/100.0;
	static double w_12 = getRandWeight()/100.0;
	static double w_21 = getRandWeight()/100.0;
	static double w_22 = getRandWeight()/100.0;
	static double w_31 = getRandWeight()/100.0;
	static double w_32 = getRandWeight()/100.0;

	public static double getRandWeight () {
		Random generator = new Random();
		int number = generator.nextInt(100);
		return number;
	}	

	public static void main(String[] args) throws IOException {

		double eta, error;
		
		Scanner inp = new Scanner(System.in);
		System.out.println("Enter learning rate(eta):");
		eta = inp.nextDouble();

		System.out.println("Set target error:");
		error = inp.nextDouble();
		double error1 = error * -1;

		System.out.println("Initial weights are:");
		System.out.println(w_11 + ", " + w_12 + ", " + w_21 + ", " + w_22 + ", " + w_31 + ", " + w_32);

		double e = bpAlgo(0, 0, 0, eta);
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.println("First-batch error is: " + df.format(e));

		int i = 0;
		int count = 0;

		while( !(e <= error && e >= error1)) {
			count = count % 4;
			switch(count){
				case 0:
					e = bpAlgo(0, 0, 0, eta);
					break;
				case 1:
					e = bpAlgo(0, 1, 1, eta);
					break;
				case 2:
					e = bpAlgo(1, 0, 1, eta);
					break;
				case 3:
					e = bpAlgo(1, 1, 0, eta);
					break;
			}
			if(i > 50000)
				break;
			count++;
			i++;
		}
		
		System.out.println("The final weights are: ");
		System.out.println(df.format(w_11) + ", " + df.format(w_12) + ", " + df.format(w_21) + ", " + df.format(w_22) + ", " + df.format(w_31) + ", " + df.format(w_32));

		System.out.println("Final Error: " + df.format(e));

		System.out.println("Number of batch runs: " + ( i + 1 ));
		
	}

	public static double bpAlgo (int ip1, int ip2, int target, double eta) {

		double error, node1, output, node2, e1, e2;

		node1 = 1 / (1 + Math.exp( - ((ip1 * w_11) + (ip2 * w_21))));
		node2 = 1 / (1 + Math.exp( - ((ip1 * w_12) + (ip2 * w_22))));
		output = 1 / (1 + Math.exp(-((node1 * w_31) + (node2 * w_32))));
		
		//error = (slope of sigmoid)*(expected - output)
		error = output * (1 - output) * (target - output);
		
		//weight = weight + learning_rate * error * input
		w_31 = w_31 + (eta * error * node1);
		w_32 = w_32 + (eta * error * node2);
		//accumulated errors
		//error = transfer_derivative(output) * (weight_k * error_j) 
		e1 = node1 * (1 - node1) * (error * w_31);
		e2 = node2 * (1 - node2) * (error * w_32);
		//weight = weight + learning_rate * error * input
		w_11 = w_11 + (eta * e1 * ip1);
		w_12 = w_12 + (eta * e2 * ip1);
		w_21 = w_21 + (eta * e1 * ip2);
		w_22 = w_22 + (eta * e2 * ip2);
		return error;
	}
}