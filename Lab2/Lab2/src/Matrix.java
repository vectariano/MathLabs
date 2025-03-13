public class Matrix {

    private double[][] matrix;
    private int rows;
    private int cols;

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.cols = matrix[0].length;
    }

    public double[][] transpose() {
        double[][] transposedMatrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix[j][i] += matrix[i][j];
            }
        }
        return transposedMatrix;
    }

    public double[][] mult(double[][] inMatrix) {
        if (inMatrix[0].length != 1) {
            double[][] result1 = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    for (int k = 0; k < cols; k++) {
                        result1[i][j] += matrix[i][k] * inMatrix[k][j];
                    }
                }
            }
            return result1;
        }
        else {
            double[][] result2 = new double[rows][1];
            for (int i = 0; i < rows; i++) {
                for (int k = 0; k < cols; k++) {
                    result2[i][0] += matrix[i][k] * inMatrix[k][0];
                }
            }
            return result2;
        }
    }

    public void printMatrix() {
        if (cols != 1) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.printf("%10.4f", matrix[i][j]);
                }
                System.out.println();
            }
        }
        else {
            for (int i = 0; i < rows; i++) {
                System.out.printf("%10.4f\n", matrix[i][0]);
            }

        }
    }
}
