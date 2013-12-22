package geometry;

import java.util.Arrays;
import java.util.List;

import static geometry.Vector3D.subtract;
import static geometry.Vector3D.crossProduct;
import static geometry.Vector3D.normalize;

public class Face {
    public final List<Integer> points;
    private final Mesh mesh;
    private double[] normal;
    private float[] color = {1.0f, 1.0f, 1.0f};

    public Face(Mesh mesh, List<Integer> points) {
        this.mesh = mesh;
        this.points = points;
        if (points.size() < 3) {
            System.out.println("WARNING: face with " + points.size() + "vertices found.");
            return;
        }
        setNormal(normalize(crossProduct(
                subtract(mesh.getVector(points.get(1)), mesh.getVector(points.get(0))),
                subtract(mesh.getVector(points.get(2)), mesh.getVector(points.get(1)))
        )));
    }

    public Face(Mesh mesh, Integer... points) {
        this(mesh, Arrays.asList(points));
    }

    public double[] getNormal() {
        return normal;
    }

    public float[] getColor() {
        return color;
    }

    public Face setNormal(Vector3D normal) {
        return setNormal(normal.toArray());
    }

    public Face setNormal(double... normal) {
        if (normal.length != 3) {
            System.out.println("Normal is not a 3D vector");
            return this;
        }
        this.normal = normal;
        return this;
    }

    public Face setColor(float... color) {
        if (color.length != 4) {
            System.out.println("Color is not in OGL format (have " + color.length + " components).");
            return this;
        }
        this.color = color;
        return this;
    }

    public Integer getNextPointInFaceOrder(int inPointOrder) {
        if (inPointOrder != points.size() - 1)
            return points.get(inPointOrder + 1);
        else
            return points.get(0);
    }

    public Integer getPreviousPointInFaceOrder(int inPointOrder) {
        if (inPointOrder != 0)
            return points.get(inPointOrder - 1);
        else
            return points.get(points.size() - 1);
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

