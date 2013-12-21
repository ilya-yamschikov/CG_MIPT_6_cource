package geometry;

import org.omg.CORBA._PolicyStub;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Face {
    public List<Integer> points;

    public Face(Integer... points) {
        if (points != null)
            this.points = Arrays.asList(points);
        else
            this.points = new ArrayList<Integer>(3);
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

