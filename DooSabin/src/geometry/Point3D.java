package geometry;

public class Point3D {
    private int id;

    private double x;
    private double y;
    private double z;

    public Point3D(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public double[] toArray() {
        double[] point = new double[3];
        point[0] = x;
        point[1] = y;
        point[2] = z;

        return point;
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
