package geometry;

public class Point3D {
    private int id;
    private Vector3D vector;

    public Point3D(int id, double x, double y, double z) {
        this.id = id;
        this.vector = new Vector3D(x, y, z);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double[] toArray() {
        return vector.toArray();
    }

    public Vector3D getVector() {
        return vector;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (! (other instanceof Point3D)) return false;

        Point3D that = (Point3D) other;
        return that.id == this.id;
    }
}
