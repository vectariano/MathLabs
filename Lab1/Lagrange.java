public class Lagrange {
    private int n;
    private double[] m1;
    private double[] m2;
    private double x;

    public Lagrange(int n, double[] m1, double[] m2, double x) {
        this.n = n;
        this.m1 = m1;
        this.m2 = m2;
        this.x = x;
    }

    public double LagrangeCalc() {
        double res = 0.0;
        for (int i = 0; i < n; i++) {
            double f = m2[i];
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    f *= (x - m1[j]) / (m1[i] - m1[j]);
                }
            }
            res += f;
        }
        return res;
    }
}
