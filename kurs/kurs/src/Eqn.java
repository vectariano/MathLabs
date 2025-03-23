public class Eqn implements FuncInterf {
    private double K, M, L, g;

    public Eqn(double K, double M, double L, double g) {
        this.K = K;
        this.M = M;
        this.L = L;
        this.g = g;
    }

    @Override
    public void f(int n, double t, double[] x, double[] dx) {
        dx[0] = x[1];
        dx[1] = -K / M * x[0] - g * (1 - Math.cos(x[2])) + (L + x[0]) * Math.pow(x[3], 2);
        dx[2] = x[3];
        dx[3] = -g / (L + x[0]) * Math.sin(x[2]) - 2 / (L + x[0]) * x[1] * x[3];
    }
}
