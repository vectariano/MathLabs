public class Main {
    public static void main(String[] args) throws RKF45Exception {
        FuncInterf system = new Funcs();

        int NEQN = 2;
        double[] y = { 0.0, 1.0 };
        double[] YP = new double[NEQN];
        double T = 0.0;
        double TOUT = 0.4;
        double RELERR = 0.0001;
        double ABSERR = 0.0001;
        double H = 0.02;
        int NFE = 0;
        int MAXNFE = 5000;
        int IFLAG = 1;

        System.out.println("RKF45");
        System.out.println(String.format("\t %5s \t %7s \t %8s \t %6s", "Step", "z1", "z2", "flag"));

        for (int i = 0; i < 21; i++) {
            RKF45 rkf45 = new RKF45(
                    system, NEQN, y, YP, T, TOUT, RELERR, ABSERR, H, NFE, MAXNFE, IFLAG
            );
            rkf45.complete();
            System.out.println(String.format(
                    "%10.2f %s %10.6f %s %10.6f %s\t %d",
                    T, "|", y[0], "|", y[1], "|", IFLAG)
            );
            T += H;
        }

        System.out.println("\nRUNGE-KUTTA WITH 0.01");
        System.out.println(String.format("\t %5s \t %7s \t %8s \t %6s", "Step", "z1", "z2", "flag"));
        double[] z = { 0.0, 1.0 };
        double[] dz = new double[NEQN];
        double st = 0.0;
        double tt;
        double sh = 0.01;
        double[] k1 = new double[NEQN];
        double[] k2 = new double[NEQN];
        double[] k3 = new double[NEQN];
        double[] k4 = new double[NEQN];
        double[] zt = new double[NEQN];

        for (int i = 0; i < 41; i++) {
            system.f(NEQN, st, z, dz);
            k1[0] = sh * dz[0];
            k1[1] = sh * dz[1];

            tt = st + sh / 3;
            zt[0] = z[0] + k1[0] / 3;
            zt[1] = z[1] + k1[1] / 3;
            system.f(NEQN, tt, zt, dz);
            k2[0] = sh * dz[0];
            k2[1] = sh * dz[1];

            tt = st + 2 * sh / 3;
            zt[0] = z[0] - k1[0] / 3 + k2[0];
            zt[1] = z[1] - k1[1] / 3 + k2[1];
            system.f(NEQN, tt, zt, dz);
            k3[0] = sh * dz[0];
            k3[1] = sh * dz[1];

            tt = st + sh;
            zt[0] = z[0] + k1[0] - k2[0] + k3[0];
            zt[1] = z[1] + k1[1] - k2[1] + k3[1];
            system.f(NEQN, tt, zt, dz);
            k4[0] = sh * dz[0];
            k4[1] = sh * dz[1];

            z[0] += (k1[0] + 3 * k2[0] + 3 * k3[0] + k4[0]) / 8;
            z[1] += (k1[1] + 3 * k2[1] + 3 * k3[1] + k4[1]) / 8;

            if (Math.abs(st % 0.02) < 1e-10) {
                System.out.println(
                        String.format(
                                "%10.2f %s %10.6f %s %10.6f %s\t %d",
                                st, "|", z[0], "|", z[1], "|", IFLAG)
                );
            }

            st += sh;
        }

        System.out.println("\nRUNGE-KUTTA WITH 0.005");
        System.out.println(String.format("\t %5s \t %7s \t %8s \t %6s", "Step", "z1", "z2", "flag"));
        z = new double[]{ 0.0, 1.0 };
        dz = new double[NEQN];
        st = 0.0;
        tt = 0.0;
        sh = 0.005;
        k1 = new double[NEQN];
        k2 = new double[NEQN];
        k3 = new double[NEQN];
        k4 = new double[NEQN];
        zt = new double[NEQN];

        for (int i = 0; i < 81; i++) {
            system.f(NEQN, st, z, dz);
            k1[0] = sh * dz[0];
            k1[1] = sh * dz[1];

            tt = st + sh / 3;
            zt[0] = z[0] + k1[0] / 3;
            zt[1] = z[1] + k1[1] / 3;
            system.f(NEQN, tt, zt, dz);
            k2[0] = sh * dz[0];
            k2[1] = sh * dz[1];

            tt = st + 2 * sh / 3;
            zt[0] = z[0] - k1[0] / 3 + k2[0];
            zt[1] = z[1] - k1[1] / 3 + k2[1];
            system.f(NEQN, tt, zt, dz);
            k3[0] = sh * dz[0];
            k3[1] = sh * dz[1];

            tt = st + sh;
            zt[0] = z[0] + k1[0] - k2[0] + k3[0];
            zt[1] = z[1] + k1[1] - k2[1] + k3[1];
            system.f(NEQN, tt, zt, dz);
            k4[0] = sh * dz[0];
            k4[1] = sh * dz[1];

            z[0] += (k1[0] + 3 * k2[0] + 3 * k3[0] + k4[0]) / 8;
            z[1] += (k1[1] + 3 * k2[1] + 3 * k3[1] + k4[1]) / 8;

            if (Math.abs(st % 0.02) < 1e-10) {
                System.out.println(
                        String.format(
                                "%10.2f %s %10.6f %s %10.6f %s\t %d",
                                st, "|", z[0], "|", z[1], "|", IFLAG)
                );
            }

            st += sh;
        }
    }
}