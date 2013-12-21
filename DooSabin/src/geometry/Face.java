package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Face {
    public List<Integer> points;
    private double[] normal;

    public Face(Integer... points) {
        if (points != null)
            this.points = Arrays.asList(points);
        else
            this.points = new ArrayList<Integer>(3);
    }

    public double[] getNormal() {
        return normal;
    }

    public Face setNormal(double... normal) {
        if (normal.length != 3) {
            System.out.println("Normal is not a 3D vector");
            return this;
        }
        this.normal = normal;
        return this;
    }

    public void addPoint(Integer point) {
        for (int pID: points) {
            if (pID == point) {
                System.out.println("Face already contains point with id: '" + point + "'.");
                return;
            }
        }
        points.add(point);
    }
}

