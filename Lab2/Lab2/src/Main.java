import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {
        int n = 8, ndim = 10, flag = 0;
        int[] pivot = new int[n];
        double cond = 0.0;
        double det, normaX1 = 0.0, normaX2X1 = 0.0, s = 0.0;
        double[] pmas = {1.0, 0.1, 0.01, 0.0001, 0.000001};
        for (int p = 0; p < pmas.length; p++) {

            System.out.println("Для p = " + String.format("%.6f", pmas[p]));

            double[][] a = {
                    {pmas[p] + 13, 2, 8, -7, 7, 5, -7, -7},
                    {7, 2, -4, 2, 3, 3, -1, -2},
                    {-7, 2, 1, 3, 6, -6, -3, -4},
                    {-2, -8, -6, -1, 6, 2, 1, -4},
                    {0, 4, -7, 1, 22, 0, -6, -6},
                    {0, -3, -6, 6, 4, 13, 0, 6},
                    {-8, -6, -4, 7, -5, -5, -2, 1},
                    {5, 5, -2, -2, -3, 0, -7, 14}
            };
            Matrix matrixA = new Matrix(a);
//            matrixA.printMatrix();
            double[][] b = {
                    {4 * pmas[p] + 6},
                    {36},
                    {-25},
                    {-57},
                    {32},
                    {62},
                    {-71},
                    {70}
            };
            Matrix matrixB = new Matrix(b);
//            matrixB.printMatrix();

            double[][] transposedA = matrixA.transpose();
            Matrix transposedMatrixA = new Matrix(transposedA);
//            transposedMatrixA.printMatrix();

            double[][] multiplicationATA = transposedMatrixA.mult(a);
            double[][] multiplicationATb = transposedMatrixA.mult(b);

            Matrix multiplication1 = new Matrix(multiplicationATA);
            Matrix multiplication2 = new Matrix(multiplicationATb);
//            multiplication1.printMatrix();
//            multiplication2.printMatrix();

            Decomp decomp = new Decomp(n, ndim, a, cond, pivot, flag);
            Solve solve = new Solve(n, ndim, a, b, pivot);
            decomp.decomp();

            solve.solve();

            System.out.print("Вектор решений: ");
            for (int i = 0; i < n; i++) {
                System.out.print(String.format("%10.4f", b[i][0]));
                normaX1 += pow(b[i][0], 2);
            }

            System.out.println();
            normaX1 = sqrt(normaX1);
            System.out.println("Число обусловленности A: " + String.format("%+E", decomp.getCond()));

            Decomp decomp1 = new Decomp(n, ndim, multiplicationATA, cond, pivot, flag);
            Solve solve1 = new Solve(n, ndim, multiplicationATA, multiplicationATb, pivot);

            decomp1.decomp();
            solve1.solve();

            System.out.print("Вектор решений: ");
            for (int i = 0; i < n; i++) {
                System.out.print(String.format("%10.4f", multiplicationATb[i][0]));
                normaX2X1 += pow(b[i][0] - multiplicationATb[i][0], 2);
            }
            normaX2X1 = sqrt(normaX2X1);
            System.out.println();

            System.out.println("Число обусловленности AT: " + String.format("%+E", decomp1.getCond()));

            s = normaX2X1 / normaX1;

            System.out.println("||X1-X2||/||X1|| = " + String.format("%+E", s));

            System.out.println("-----------------------------------" +
                    "------------------------------------------------------------");
        }
    }
}