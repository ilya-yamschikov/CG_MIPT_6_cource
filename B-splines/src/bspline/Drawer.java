package bspline;

import javax.swing.*;
import stdlib.StdDraw;

public class Drawer {
    private int splits;
    private BasisFunction[] functions;

    private int BLOCK_SIZE_IN_PIXEL = 250;

    private final int MAX_WIDTH = 1000;
    private final int MAX_HEIGHT = 750;

    public Drawer() {
        splits = 200;
    }

    public static double transform(double value, double shift, double scale) {
        return value * scale + shift;
    }

    private static void drawAxis(double x0, double y0, double scale) {
        StdDraw.line(transform(-0.1, x0, scale), transform(0.0, y0, scale), transform(1.0, x0, scale), transform(0.0, y0, scale));
        StdDraw.line(transform(0.0, x0, scale), transform(-0.1, y0, scale), transform(0.0, x0, scale), transform(1.0, y0, scale));
        StdDraw.text(transform(0.95, x0, scale), transform(0.05, y0, scale), "X");
        StdDraw.text(transform(0.05, x0, scale), transform(0.95, y0, scale), "Y");
    }

    private static void drawName(BasisFunction function, double x, double y) {
        StdDraw.textRight(x, y, "N");
        StdDraw.textLeft(x + 0.01, y - 0.02, function.getI() + ", " + function.getK());
    }

    private static void drawName(String name, double x0, double y0, double scale) {
        StdDraw.text(transform(0.5, x0, scale), transform(0.9, y0, scale), name);
    }

    private static void drawName(BasisFunction function, double x0, double y0, double scale) {
        drawName(function, transform(0.5, x0, scale), transform(1.0, y0, scale));
    }

    private void drawGraph(Graph graph, double x0, double y0, double scale) {
        graph.draw(x0, y0, scale);
        drawAxis(x0, y0, scale);
    }

    public void drawAll(int pointsCount, int k, double[] knots) {
        int horizontalBlocksCount = Math.min(pointsCount + 1 ,MAX_WIDTH / BLOCK_SIZE_IN_PIXEL);
        int verticalBlocksCount = Math.min(1 + pointsCount / (MAX_WIDTH / BLOCK_SIZE_IN_PIXEL), MAX_HEIGHT / BLOCK_SIZE_IN_PIXEL);
        int canvasWidth = horizontalBlocksCount * BLOCK_SIZE_IN_PIXEL;
        int canvasHeight = verticalBlocksCount * BLOCK_SIZE_IN_PIXEL;

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0.0, horizontalBlocksCount);
        StdDraw.setYscale(0.0, verticalBlocksCount);

        if (pointsCount + 1 > horizontalBlocksCount * verticalBlocksCount)
            JOptionPane.showMessageDialog(null, "Not enough place to show all functions");

        functions = new BasisFunction[pointsCount];
        Graph[] graphs = new Graph[functions.length];
        StdDraw.show(0);
        for (int i = 1; i <= pointsCount; i++) {
            functions[i-1] = new BasisFunction(i, k, knots);
            graphs[i-1] = functions[i-1].getGraph(splits);
            drawGraph(graphs[i-1], (i-1) % horizontalBlocksCount, verticalBlocksCount - 1 - ((i-1) / horizontalBlocksCount), 0.8);
            drawName(functions[i-1], (i-1) % horizontalBlocksCount, verticalBlocksCount - 1 - ((i-1) / horizontalBlocksCount), 0.8);
        }

        Graph sum = Graph.sum(graphs);
        drawGraph(sum, pointsCount % horizontalBlocksCount, verticalBlocksCount - 1 - (pointsCount / horizontalBlocksCount), 0.8);
        drawName("sum", pointsCount % horizontalBlocksCount, verticalBlocksCount - 1 - (pointsCount / horizontalBlocksCount), 0.8);

        StdDraw.show(0);
    }

    public void setResolution(int splits) {
        this.splits = splits;
    }
}