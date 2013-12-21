package geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mesh {
    public static final Mesh CUBE = getCube();

    private Map<Integer, Point3D> points;
    private List<Face> faces;

    public Mesh() {
        points = new HashMap<Integer, Point3D>();
        faces = new ArrayList<Face>();
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void addFace(Face face) {
        for (Integer point: face.points) {
            if (! points.containsKey(point)) {
                System.out.println("No point with id: '" + point + "' in mesh.");
                return;
            }
        }
        faces.add(face);
    }

    public Point3D getPoint(int id) {
        return points.get(id);
    }

    public void addPoint(Point3D point) {
        if (points.containsKey(point.getId())) {
            System.out.println("Mesh already contains point with id: '" + point + "'.");
            return;
        }
        points.put(point.getId(), point);
    }

    private static Mesh getCube() {
        Mesh cube = new Mesh();
        cube.addPoint(new Point3D(1, 0.0, 0.0, 0.0));
        cube.addPoint(new Point3D(2, 1.0, 0.0, 0.0));
        cube.addPoint(new Point3D(3, 1.0, 1.0, 0.0));
        cube.addPoint(new Point3D(4, 0.0, 1.0, 0.0));
        cube.addPoint(new Point3D(5, 0.0, 0.0, 1.0));
        cube.addPoint(new Point3D(6, 1.0, 0.0, 1.0));
        cube.addPoint(new Point3D(7, 1.0, 1.0, 1.0));
        cube.addPoint(new Point3D(8, 0.0, 1.0, 1.0));

        cube.addFace(new Face(1,2,3,4));
        cube.addFace(new Face(1,2,6,5));
        cube.addFace(new Face(2,3,7,6));
        cube.addFace(new Face(3,4,8,7));
        cube.addFace(new Face(4,1,5,8));
        cube.addFace(new Face(5,6,7,8));

        return cube;
    }
}
