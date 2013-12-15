package bspline;

import java.awt.*;
import stdlib.StdDraw;

public class Graph{
    private double[] x;
    private double[] y;

    public Graph(double[] x, double[] y) {
        setX(x);
        setY(y);
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    private static double[] scale(double[] arr, double scale) {
        assert arr != null && arr.length > 0;
        double minX = arr[0], maxX= arr[0];
        for (double element: arr) {
            if (element < minX)
                minX = element;
            if (element > maxX)
                maxX = element;
        }

        double originalScale = maxX - minX;
        double[] newArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = scale * (arr[i] - minX) / originalScale;
        }

        return newArr;
    }

    public Graph getScaled(double scale) {
        return new Graph(scale(getX(), scale), scale(getY(), scale));
    }

    public void draw(double x0, double y0, double scale) {
        assert x != null && y != null;
        assert x.length == y.length;

        double minX = x[0], maxX = x[x.length - 1];

        StdDraw.setPenColor(Color.BLUE);
        for(int i = 0; i < x.length - 1; i++) {
            StdDraw.line(Drawer.transform(x[i] - minX, x0, scale / (maxX - minX)), Drawer.transform(y[i], y0, scale), Drawer.transform(x[i + 1] - minX, x0, scale / (maxX - minX)), Drawer.transform(y[i + 1], y0, scale));
        }
        StdDraw.setPenColor(Color.BLACK);
    }

    public static Graph sum(Graph[] graphs) {
        double[] x = graphs[0].x.clone();
        double[] y = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            for (Graph graph: graphs) {
                y[i] += graph.y[i];
            }
        }

        return new Graph(x, y);
    }

}