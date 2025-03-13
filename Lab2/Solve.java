public class Solve {
    private int n;
    private int ndim;
    private double[][] a;
    private double[][] b;
    private int[] pivot;

    public Solve(int n, int ndim, double[][] a, double[][] b, int[] pivot) {
        this.n = n;
        this.ndim = ndim;
        this.a = a;
        this.b = b;
        this.pivot = pivot;
    }

    public int solve () {
        int    i, j, k, m;
        double t;

        if (n == 1)
        {
            /* trivial */
            b[0][0] /= a[0][0];
        }
        else
        {
            /* Forward elimination: apply multipliers. */
            for (k = 0; k < n-1; k ++)
            {
                m = pivot[k];
                t = b[m][0]; b[m][0] = b[k][0]; b[k][0] = t;
                for (i = k+1; i < n; ++i) {
                    b[i][0] += a[i][k] * t;
                }
            }

            /* Back substitution. */
            for (k = n-1; k >= 0; --k)
            {
                t = b[k][0];
                for (j = k+1; j < n; ++j) {
                    t -= a[k][j] * b[j][0];
                }
                b[k][0] = t / a[k][k];
            }
        }

        return 0;
    }
}
