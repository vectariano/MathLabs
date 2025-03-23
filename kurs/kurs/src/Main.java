
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws RKF45Exception {

//        Decomp/Solve
        double A, B, C, D, L, M, K, g;
        int n = 4, ndim = 10, flag = 0;
        int[] pivot = new int[n];
        double cond = 0.0;

        double[][] a = {
                {5, 7, 6, 5},
                {7, 10, 8, 7},
                {6, 8, 10, 9},
                {5, 7, 9, 10}
        };

        double[][] b = {
                {24},
                {32},
                {40},
                {36},
        };

        Decomp decomp = new Decomp(n, ndim, a, cond, pivot, flag);
        decomp.decomp();

        Solve solve = new Solve(n, ndim, a, b, pivot);
        solve.solve();

        A = b[0][0];
        B = b[1][0];
        C = b[2][0];
        D = b[3][0];

        System.out.println("A = " + A + "\n" + "B = " + B + "\n" + "C = " + C + "\n" + "D = " + D);

//        Quanc8
        Function<Double, Double> func = x -> 1 / Math.pow((x - 1) * Math.pow((x + 1), 2), 1.0 / 3.0);
        double ABSERR = 0;
        double RELERR = 10e-10;
        double a_ = 2.0;
        double b_ = 4.0;

        Quanc8 integral = new Quanc8(func, a_, b_, ABSERR, RELERR);
        integral.calculate();

        L = Math.pow(integral.getRESULT() - 0.65730045, 4) + 1;

        System.out.println("L = " + L);


//        Zeroin
        Function<Double, Double> func2 = x -> Math.log10(3 * x - 1) + Math.exp(2 * x - 1);
        double left = 0.37, right = 0.4, t = 10e-7;

        Zeroin solution = new Zeroin(func2, left, right, t);

        M = 2.587103 * solution.complete();

        System.out.println("M = " + M);

//        RKF
        int NEQN = 4;
        double[] y = new double[4];
        y[0] = A;
        y[1] = B;
        y[2] = D;
        y[3] = C;
        K = 39.24;
        double[] YP = new double[NEQN];
        double T = 0.0;
        double TOUT = 4.0;
        double RELERR_ = 10e-10;
        double ABSERR_ = 0;
        double H = 0.1;
        int NFE = 0;
        int MAXNFE = 5000;
        int IFLAG = 1;
        g = 9.81;
        FuncInterf system = new Eqn(K, M, L, g);

        System.out.println("K = " + K);
        System.out.println("RKF45");
        System.out.println(String.format("\t %5s \t %7s \t %8s \t %6s", "Step", "x", "O", "flag"));

        for (int i = 1; i < 41; i++) {
            RKF45 rkf45 = new RKF45(
                    system, NEQN, y, YP, T, TOUT, RELERR_, ABSERR_, H, NFE, MAXNFE, IFLAG
            );
            rkf45.complete();

            T += H;

            System.out.println(String.format(
                    "%10.1f %s %10.6f %s %10.6f %s\t %d",
                    T, "|", y[0], "|", y[2], "|", IFLAG)
            );
        }
    }
}