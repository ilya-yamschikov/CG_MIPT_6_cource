package geometry;

import java.util.*;

public class Mesh {
    public static final Mesh CUBE = getCube();
    public static final Mesh TRIANGLE_2D = get2DTriangleWith1Face();
    public static final Mesh COMPLEX_TRIANGLE_2D = get2DTriangleWith4Faces();

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

    public Vector3D getVector(int id) {
        return points.get(id).getVector();
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

        cube.addFace(new Face(cube, 4,3,2,1));
        cube.addFace(new Face(cube, 1,2,6,5));
        cube.addFace(new Face(cube, 2,3,7,6));
        cube.addFace(new Face(cube, 3,4,8,7));
        cube.addFace(new Face(cube, 4,1,5,8));
        cube.addFace(new Face(cube, 5,6,7,8));

        return cube;
    }

    private static Mesh get2DTriangleWith1Face() {
        double[] center = {0.0, 0.0, 0.0};
        double scale = 1.0;

        Mesh triangle = new Mesh();
        triangle.addPoint(new Point3D(1, -0.5 * scale + center[0], -0.289 * scale + center[1], 0.0 * scale + center[2]));
        triangle.addPoint(new Point3D(2,  0.0 * scale + center[0],  0.577 * scale + center[1], 0.0 * scale + center[2]));
        triangle.addPoint(new Point3D(3,  0.5 * scale + center[0], -0.289 * scale + center[1], 0.0 * scale + center[2]));

        triangle.addFace(new Face(triangle, 3, 2, 1));

        return triangle;
    }

    private static Mesh get2DTriangleWith4Faces() {
        double[] center = {0.0, 0.0, 0.0};
        double scale = 1.0;

        Mesh triangle = new Mesh();
        triangle.addPoint(new Point3D(1, -0.5  * scale + center[0], -0.289 * scale + center[1], 0.0 * scale + center[2]));
        triangle.addPoint(new Point3D(2, -0.25 * scale + center[0],  0.144 * scale + center[1], 0.0 * scale + center[2]));
        triangle.addPoint(new Point3D(3,  0.0  * scale + center[0],  0.577 * scale + center[1], 0.0 * scale + center[2]));
        triangle.addPoint(new Point3D(4,  0.25 * scale + center[0],  0.144 * scale + center[1], 0.0 * scale + center[2]));
        triangle.addPoint(new Point3D(5,  0.5  * scale + center[0], -0.289 * scale + center[1], 0.0 * scale + center[2]));
        triangle.addPoint(new Point3D(6,  0.0  * scale + center[0], -0.289 * scale + center[1], 0.0 * scale + center[2]));

        triangle.addFace(new Face(triangle, 6, 2, 1));
        triangle.addFace(new Face(triangle, 6, 4, 2));
        triangle.addFace(new Face(triangle, 6, 5, 4));
        triangle.addFace(new Face(triangle, 4, 3, 2));

        return triangle;
    }
}
