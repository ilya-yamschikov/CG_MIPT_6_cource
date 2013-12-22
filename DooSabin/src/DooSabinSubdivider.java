import geometry.Face;
import geometry.Mesh;
import geometry.Point3D;
import geometry.Vector3D;

import static geometry.Vector3D.getAverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DooSabinSubdivider {
    public static Mesh process(Mesh inMesh) {
        Mesh newMesh = new Mesh();
        List<ProcessingFace> processingFaces = new ArrayList<ProcessingFace>(inMesh.getFaces().size());
        Map<Integer, ProcessingPoint> processingPoints = new HashMap<Integer, ProcessingPoint>(inMesh.getPointsSize());
        List<ProcessingEdge> processingEdges = new ArrayList<ProcessingEdge>();
        int pointsCounter = 0;

        for (Face face: inMesh.getFaces()) {
            ProcessingFace processingFace = new ProcessingFace();
            processingFace.setFacePoint(getAverage(inMesh.getVectors(face.points)));

            for (int i = 0; i < face.points.size(); i++) {
                ProcessingPoint processingPoint;
                if (processingPoints.containsKey(face.points.get(i)))
                    processingPoint = processingPoints.get(face.points.get(i));
                else {
                    processingPoint = new ProcessingPoint(face.points.get(i));
                    processingPoints.put(processingPoint.originalPoint, processingPoint);
                }

                ProcessingEdge inEdge = processingPoint.getEdge(face.getPreviousPointInFaceOrder(i));
                if (inEdge == null) {
                    inEdge = new ProcessingEdge(face.getPreviousPointInFaceOrder(i), face.points.get(i),
                            getAverage(inMesh.getVectors(face.points.get(i), face.getPreviousPointInFaceOrder(i))));
                    processingPoint.associatedEdges.add(inEdge);
                }
                ProcessingEdge outEdge = processingPoint.getEdge(face.getNextPointInFaceOrder(i));
                if (outEdge == null) {
                    outEdge = new ProcessingEdge(face.points.get(i), face.getNextPointInFaceOrder(i),
                            getAverage(inMesh.getVectors(face.points.get(i), face.getNextPointInFaceOrder(i))));
                    processingPoint.associatedEdges.add(outEdge);
                }

                Point3D newPoint = new Point3D(pointsCounter++, getAverage(
                        inMesh.getPoint(processingPoint.originalPoint).getVector(),
                        processingFace.facePoint,
                        inEdge.midPoint,
                        outEdge.midPoint));

                newMesh.addPoint(newPoint);
                processingFace.newPoints.add(newPoint.getId());
                processingPoint.newPoints.add(newPoint.getId());
            }

            processingFaces.add(processingFace);
        }
        return newMesh;
    }

    private static class ProcessingPoint {
        public int originalPoint;
        public List<ProcessingEdge> associatedEdges;
        public List<Integer> newPoints;

        private ProcessingPoint(int originalPoint) {
            this.originalPoint = originalPoint;
        }

        public ProcessingEdge getEdge(int otherPoint) {
            for (ProcessingEdge edge: associatedEdges) {
                if (edge.point1 == otherPoint || edge.point2 == otherPoint)
                    return edge;
            }
            return null;
        }
    }

    private static class ProcessingEdge {
        public Vector3D midPoint;
        public int point1, point2;
        public int[] newPoints = new int[4];

        private ProcessingEdge(int point1, int point2, Vector3D midPoint) {
            this.point1 = point1;
            this.point2 = point2;
            this.midPoint = midPoint;
        }
    }

    private static class ProcessingFace {
        public List<ProcessingEdge> associatedEdges;
        public List<Integer> newPoints;
        public Vector3D facePoint;

        public void setFacePoint(Vector3D facePoint) {
            this.facePoint = facePoint;
        }
    }
}
