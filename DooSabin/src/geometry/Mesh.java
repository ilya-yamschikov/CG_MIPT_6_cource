package geometry;

import java.util.*;

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

    public List<Point3D> getPoints(Integer[] ids) {
        List<Point3D> outPoints = new ArrayList<Point3D>(ids.length);
        for (int id : ids) {
            outPoints.add(points.get(id));
        }
        return outPoints;
    }

    public Set<Integer> getAllPointsIDs() {
        return points.keySet();
    }

    public List<Vector3D> getVectors(Integer ... ids) {
        List<Vector3D> outVectors = new ArrayList<Vector3D>(ids.length);
        for (int id : ids) {
            outVectors.add(points.get(id).getVector());
        }
        return outVectors;
    }

    public List<Vector3D> getVectors(List<Integer> ids) {
        return getVectors(ids.toArray(new Integer[ids.size()]));
    }

    public int getPointsSize() {
        return points.size();
    }

    public void addPoint(Point3D point) {
        if (point.getId() < 0) {
            System.out.println("Point ID cannot be < 0. Found: " + point.getId());
            return;
        }
        if (points.containsKey(point.getId())) {
            System.out.println("Mesh already contains point with id: '" + point + "'.");
            return;
        }
        points.put(point.getId(), point);
    }

    private static Mesh getCube() {
        double[] center = {0.0, 0.0, 0.0};
        double scale = 1.0;

        Mesh cube = new Mesh();
        cube.addPoint(new Point3D(1, -0.5 * scale + center[0], -0.5 * scale + center[1], -0.5 * scale + center[2]));
        cube.addPoint(new Point3D(2, 0.5 * scale + center[0], -0.5 * scale + center[1], -0.5 * scale + center[2]));
        cube.addPoint(new Point3D(3, 0.5 * scale + center[0], 0.5 * scale + center[1], -0.5 * scale + center[2]));
        cube.addPoint(new Point3D(4, -0.5 * scale + center[0], 0.5 * scale + center[1], -0.5 * scale + center[2]));
        cube.addPoint(new Point3D(5, -0.5 * scale + center[0], -0.5 * scale + center[1], 0.5 * scale + center[2]));
        cube.addPoint(new Point3D(6, 0.5 * scale + center[0], -0.5 * scale + center[1], 0.5 * scale + center[2]));
        cube.addPoint(new Point3D(7, 0.5 * scale + center[0], 0.5 * scale + center[1], 0.5 * scale + center[2]));
        cube.addPoint(new Point3D(8, -0.5 * scale + center[0], 0.5 * scale + center[1], 0.5 * scale + center[2]));

        cube.addFace(new Face(cube, 1,2,3,4).setNormal(0.0, 0.0, -1.0));
        cube.addFace(new Face(cube, 1,2,6,5).setNormal(0.0, -1.0, 0.0));
        cube.addFace(new Face(cube, 2,3,7,6).setNormal(1.0, 0.0, 0.0));
        cube.addFace(new Face(cube, 3,4,8,7).setNormal(0.0, 1.0, 0.0));
        cube.addFace(new Face(cube, 4,1,5,8).setNormal(-1.0, 0.0, 0.0));
        cube.addFace(new Face(cube, 5,6,7,8).setNormal(0.0, 0.0, 1.0));

        return cube;
    }
}
