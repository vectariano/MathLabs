import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        int n = 7;
        int nxk = 19;
        double[] x = {-1.0, -0.960, -0.860, -0.790, 0.220, 0.500, 0.930};
        double[] y = {-1.0, -0.151, 0.894, 0.986, 0.895, 0.500, -0.306};
        double[] b = new double[n];
        double[] c = new double[n];
        double[] d = new double[n];
        double[] xk = new double[nxk];
        double[] resSeval = new double[nxk];
        double[] resLagrange = new double[nxk];
        int iFlag = 0;
        int last = 0;

        Spline spline = new Spline(n, x, y, b, c, d, iFlag);
        spline.spline();
        JFrame splineFrame = new JFrame("Spline ratio");
        splineFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splineFrame.setSize(1000, 200);
        JTextArea splineTextArea = new JTextArea();
        splineTextArea.setEditable(false);
        StringBuilder splineString = new StringBuilder();
        splineString.append("b ").append(Arrays.toString(spline.getB())).append("\n").append("c ")
                .append(Arrays.toString(spline.getC())).append("\n").append("d ")
                .append(Arrays.toString(spline.getD()));
        splineTextArea.setText(splineString.toString());
        JScrollPane splineScrollPane = new JScrollPane(splineTextArea);
        splineFrame.add(splineScrollPane, BorderLayout.CENTER);
        splineFrame.setVisible(true);

        for (int k = 1; k < 20; k++) {
            xk[k - 1] = -1 + 0.1 * k;
            Seval seval19 = new Seval(n, xk[k - 1], x, y, b, c, d, last);
            Lagrange lagrange = new Lagrange(n, x, y, xk[k - 1]);
            resSeval[k - 1] = seval19.seval();
            resLagrange[k - 1] = lagrange.LagrangeCalc();
        }

        JFrame frame = new JFrame("Interpolation results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("x\tspline\tlagrange\n");
        for (int k = 0; k < nxk; k++) {
            stringBuilder.append(String.format("%.2f\t%.6f\t%.6f\n", xk[k], resSeval[k],
                    resLagrange[k]));
        }
        textArea.setText(stringBuilder.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

        JFrame graphFrame = new JFrame("Graph");
        graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphFrame.setSize(650, 450);

        GraphPanel graphPanel = new GraphPanel(xk, resLagrange, resSeval);
        graphFrame.add(graphPanel);
        graphFrame.setVisible(true);

        Function<Double, Double> tanFunc = t -> Math.tan(t) / t;
        double eps = 10e-6;
        double ABSERR = 0.00001;
        double RELERR = 0.00001;

        Quanc8 firstInt = new Quanc8(tanFunc, 1, Math.PI / 2 - eps, ABSERR, RELERR);
        Quanc8 secondInt= new Quanc8(tanFunc, Math.PI / 2 + eps, 2, ABSERR, RELERR);

        firstInt.calculate();
        secondInt.calculate();

        double result = firstInt.getRESULT() + secondInt.getRESULT();
        double errest = firstInt.getERREST() + secondInt.getERREST();
        double flag = firstInt.getFLAG() + secondInt.getFLAG();
        int noFun = firstInt.getNOFUN() + secondInt.getNOFUN();

        System.out.println("First int:");
        System.out.println(firstInt.getRESULT() + " " + firstInt.getERREST() + " " + firstInt.getFLAG() +
                " " + firstInt.getNOFUN());
        System.out.println("Second int:");
        System.out.println(secondInt.getRESULT() + " " + secondInt.getERREST() + " " + secondInt.getFLAG() +
                " " + secondInt.getNOFUN());
        System.out.println("Integral:");
        System.out.println(result + " " + errest + " " + flag + " " + noFun);

    }
}