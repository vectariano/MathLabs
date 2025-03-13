public class Funcs implements FuncInterf {

    @Override
    public void f(int n, double t, double[] x, double[] dx) {
        dx[0] = -310 * x[0] - 3000 * x[1] +
                (1 / (10 * Math.pow(t, 2) + 1));
        dx[1] = x[0] + Math.exp(-2 * t);
    }
}
