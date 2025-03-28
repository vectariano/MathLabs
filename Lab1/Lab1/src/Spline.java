public class Spline {
    private int n;
    private double[] x;
    private double[] y;
    private double[] b;
    private double[] c;
    private double[] d;
    private int iflag;

    public Spline(int n, double[] x, double[] y, double[] b, double[] c, double[] d, int iflag) {
        this.n = n;
        this.x = x;
        this.y = y;
        this.b = b;
        this.c = c;
        this.d = d;
        this.iflag = iflag;
    }

    public double[] getB() {
        return b;
    }

    public double[] getC() {
        return c;
    }

    public double[] getD() {
        return d;
    }

    public int getIflag() {
        return iflag;
    }

    public void spline() {

        int nm1, ib, i;
        double t;
        int ascend;

        nm1 = n - 1;
        iflag = 0;

        if (n < 2) {  /* no possible interpolation */
            iflag = 1;
            return;
        }

        ascend = 1;
        for (i = 1; i < n; ++i) if (x[i] <= x[i - 1]) ascend = 0;
        if (ascend == 0) {
            iflag = 2;
            return;
        }

        if (n >= 3) {    /* ---- At least quadratic ---- */

   /* ---- Set up the symmetric tri-diagonal system
           b = diagonal
           d = offdiagonal
           c = right-hand-side  */
            d[0] = x[1] - x[0];
            c[1] = (y[1] - y[0]) / d[0];
            for (i = 1; i < nm1; ++i) {
                d[i] = x[i + 1] - x[i];
                b[i] = 2.0 * (d[i - 1] + d[i]);
                c[i + 1] = (y[i + 1] - y[i]) / d[i];
                c[i] = c[i + 1] - c[i];
            }

   /* ---- Default End conditions
           Third derivatives at x[0] and x[n-1] obtained
           from divided differences  */
            b[0] = -d[0];
            b[nm1] = -d[n - 2];
            c[0] = 0.0;
            c[nm1] = 0.0;
            if (n != 3) {
                c[0] = c[2] / (x[3] - x[1]) - c[1] / (x[2] - x[0]);
                c[nm1] = c[n - 2] / (x[nm1] - x[n - 3]) - c[n - 3] / (x[n - 2] - x[n - 4]);
                c[0] = c[0] * d[0] * d[0] / (x[3] - x[0]);
                c[nm1] = -c[nm1] * d[n - 2] * d[n - 2] / (x[nm1] - x[n - 4]);
            }

            /* Forward elimination */
            for (i = 1; i < n; ++i) {
                t = d[i - 1] / b[i - 1];
                b[i] = b[i] - t * d[i - 1];
                c[i] = c[i] - t * c[i - 1];
            }

            /* Back substitution */
            c[nm1] = c[nm1] / b[nm1];
            for (ib = 0; ib < nm1; ++ib) {
                i = n - ib - 2;
                c[i] = (c[i] - d[i] * c[i + 1]) / b[i];
            }

            /* c[i] is now the sigma[i] of the text */

            /* Compute the polynomial coefficients */
            b[nm1] = (y[nm1] - y[n - 2]) / d[n - 2] + d[n - 2] * (c[n - 2] + 2.0 * c[nm1]);
            for (i = 0; i < nm1; ++i) {
                b[i] = (y[i + 1] - y[i]) / d[i] - d[i] * (c[i + 1] + 2.0 * c[i]);
                d[i] = (c[i + 1] - c[i]) / d[i];
                c[i] = 3.0 * c[i];
            }
            c[nm1] = 3.0 * c[nm1];
            d[nm1] = d[n - 2];

        }  /* at least quadratic */ else  /* if n >= 3 */ {  /* linear segment only  */
            b[0] = (y[1] - y[0]) / (x[1] - x[0]);
            c[0] = 0.0;
            d[0] = 0.0;
            b[1] = b[0];
            c[1] = 0.0;
            d[1] = 0.0;
        }
    }
}
