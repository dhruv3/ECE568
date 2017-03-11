package BayesianCurveFitting;

import Jama.Matrix;

public class MatrixOpClass {
	private int M;	// order of curve fitting
	private int N;	// size of data
	private double[] testPointsArray;
	private double[] dataValues;
	private double alpha = 0.1;
	private double beta =500;
	private Matrix S;
	private Matrix I;
	
	public MatrixOpClass(double[] testPointsArray, double[] dataValues, int m) {
		M = m;
		N = testPointsArray.length;
		this.testPointsArray = testPointsArray;
		this.dataValues= dataValues;
		I = Matrix.identity(M + 1, M + 1);
		getMatrix();
	}
	
	// Equation 1.72 Bishop
	private void getMatrix() {
		Matrix sum = new Matrix(M + 1, M + 1);
		for(int i = 0; i < N; i++) {
			Matrix phi = getPhiX(testPointsArray[i]);
			sum = sum.plus(phi.times(phi.transpose()));
		}
		sum = sum.times(beta);
		S = I.times(alpha).plus(sum);
		S = S.inverse();
	}
	
	// Equation 1.70 Bishop
	public double getMx(double testPointsArray) {
		Matrix sum = new Matrix(M + 1, 1);
		for(int i = 0; i < N; i++) {
			Matrix phi = getPhiX(this.testPointsArray[i]);
			sum = sum.plus(phi.times(dataValues[i]));
		}
		Matrix mat = getPhiX(testPointsArray).transpose().times(beta);
		mat = (mat.times(S)).times(sum);
		return mat.get(0, 0);
	}
	
	// Equation 1.71 Bishop
	public double getS2(double testPointsArray) {
		Matrix mat = getPhiX(testPointsArray).transpose().times(S).times(getPhiX(testPointsArray));
		double element = mat.get(0, 0);
		return (1 / beta + element);
	}
	
	private Matrix getPhiX(double testPointsArray) {
		double[] phiVal = new double[M + 1];
		for(int i = 0; i <= M; i++) {
			phiVal[i] = Math.pow(testPointsArray, i);
		}
		Matrix phi = new Matrix(phiVal, M + 1);
		return phi;
	}
	
}
