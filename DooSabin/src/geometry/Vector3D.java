package geometry;

import java.util.List;

public class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double[] toArray() {
        double[] vector = new double[3];
        vector[0] = x;
        vector[1] = y;
        vector[2] = z;

        return vector;
    }

    public static Vector3D getAverage(List<Vector3D> vectors) {
        return getAverage(vectors.toArray(new Vector3D[vectors.size()]));
    }

    public static Vector3D getAverage(Vector3D ... vectors) {
        double x = 0.0, y = 0.0, z = 0.0;
        for (Vector3D vector : vectors) {
            x += vector.x;
            y += vector.y;
            z += vector.z;
        }
        return new Vector3D(x / vectors.length, y / vectors.length, z / vectors.length);
    }


    public static Vector3D crossProduct(Vector3D u, Vector3D v) {
        return new Vector3D(u.y * v.z - u.z * v.y, - u.x * v.z + u.z * v.y, u.x * v.y - u.y * v.x);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

}
