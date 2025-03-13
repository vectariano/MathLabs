import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

class GraphPanel extends JPanel {
    private final double[] x;
    private final double[] yLagrange;
    private final double[] ySpline;
    private final int width = 600, height = 400;
    private final double xMin, xMax, yMin, yMax;

    public GraphPanel(double[] x, double[] yLagrange, double[] ySpline) {
        this.x = x;
        this.yLagrange = yLagrange;
        this.ySpline = ySpline;

        xMin = -1.0;
        xMax = 1.0;
        yMin = -1.5;
        yMax = 1.5;

        setPreferredSize(new Dimension(width, height));
    }

    private int scaleX(double xValue) {
        return (int) ((xValue - xMin) / (xMax - xMin) * width);
    }

    private int scaleY(double yValue) {
        return height - (int) ((yValue - yMin) / (yMax - yMin) * height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        g.setColor(Color.BLACK);
        g.drawLine(scaleX(xMin), scaleY(0), scaleX(xMax), scaleY(0));
        g.drawLine(scaleX(0), scaleY(yMin), scaleX(0), scaleY(yMax));


        g.setColor(Color.RED);
        for (int i = 0; i < x.length; i++) {
            int px = scaleX(x[i]);
            int pyL = scaleY(yLagrange[i]);
            int pyS = scaleY(ySpline[i]);

            g.fillOval(px - 3, pyL - 3, 6, 6);
            g.fillOval(px - 3, pyS - 3, 6, 6);
        }

        g.setColor(Color.BLUE);
        for (int i = 1; i < x.length; i++) {
            g.drawLine(scaleX(x[i - 1]), scaleY(yLagrange[i - 1]),
                    scaleX(x[i]), scaleY(yLagrange[i]));
        }

        g.setColor(Color.GREEN);
        for (int i = 1; i < x.length; i++) {
            g.drawLine(scaleX(x[i - 1]), scaleY(ySpline[i - 1]),
                    scaleX(x[i]), scaleY(ySpline[i]));
        }
        drawLegend(g);
    }
    private void drawLegend(Graphics g) {
        int legendX = width - 90;
        int legendY = 20;
        int rectSize = 20;
        int textOffset = 30;

        g.setColor(Color.BLUE);
        g.fillRect(legendX, legendY, rectSize, rectSize);
        g.setColor(Color.BLACK);
        g.drawString("Lagrange",legendX + textOffset, legendY + 15);

        g.setColor(Color.GREEN);
        g.fillRect(legendX, legendY + 30, rectSize, rectSize);
        g.setColor(Color.BLACK);
        g.drawString("Spline", legendX + textOffset, legendY + 45);

    }

    private void drawAxes(Graphics g) {
        g.setColor(Color.BLACK);

        // Ось X
        g.drawLine(scaleX(xMin), scaleY(0), scaleX(xMax), scaleY(0));

        // Ось Y
        g.drawLine(scaleX(0), scaleY(yMin), scaleX(0), scaleY(yMax));

        // Отмечаем деления на оси X
        for (double xTick = xMin; xTick <= xMax; xTick += 0.2) {
            int xPos = scaleX(xTick);
            g.drawLine(xPos, scaleY(0) - 5, xPos, scaleY(0) + 5);
            g.drawString(String.format("%.1f", xTick), xPos - 10, scaleY(0) + 20);
        }

        // Отмечаем деления на оси Y
        for (double yTick = yMin; yTick <= yMax; yTick += 0.5) {
            int yPos = scaleY(yTick);
            g.drawLine(scaleX(0) - 5, yPos, scaleX(0) + 5, yPos);
            g.drawString(String.format("%.1f", yTick), scaleX(0) - 30, yPos + 5);
        }
    }

}
