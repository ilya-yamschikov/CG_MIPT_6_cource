package bspline;

public class BasisFunction {
    private final int i;
    private final int k;
    private final double[] knots;

    public BasisFunction(int i, int k, double[] knots) {
        this.i = i;
        this.k = k;
        this.knots = knots;
    }

    private double N(double x, int i, int k) {
        i--; // because in formula convention $i \in [1, n+1]$ and in Java arrays indexes start from 0
        if (k == 1) {
            if ((knots[i] <= x && x <  knots[i+1]) || (x == knots[knots.length-1]))
                return 1.0;
            else
                return 0.0;
        } else {
            double leftN = N (x, i+1, k-1);
            double leftDenominator = knots[i+k-1] - knots[i];
            double leftPart;
            if (leftN == 0 && leftDenominator == 0)
                leftPart = 0.0;
            else
                leftPart = (x - knots[i]) * leftN / leftDenominator;

            double rightN = N (x, i+2, k-1);
            double rightDenominator = knots[i+k] - knots[i+1];
            double rightPart;
            if (rightN == 0 && rightDenominator == 0)
                rightPart = 0.0;
            else
                rightPart= (knots[i+k] - x) * rightN / rightDenominator;

            return leftPart + rightPart;
        }
    }

    public Graph getGraph(int scale) {
        double[] x = new double[scale + 1];
        for (int i = 0; i < x.length; i++) {
            x[i] = knots[0] + i * (knots[knots.length - 1]- knots[0]) / ((double) scale);
        }

        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = N(x[i], this.i, this.k);
        }

        return new Graph(x, y);
    }

    public int getI() {
        return i;
    }

    public int getK() {
        return k;
    }
}
