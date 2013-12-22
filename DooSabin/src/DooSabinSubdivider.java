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
        for (Integer id: inMesh.getAllPointsIDs()) {
            processingPoints.put(id, new ProcessingPoint(id));
        }
        List<ProcessingEdge> processingEdges = new ArrayList<ProcessingEdge>();
        int pointsCounter = 0;

        for (Face face: inMesh.getFaces()) {
            ProcessingFace processingFace = new ProcessingFace(face.points.size());
            processingFace.setFacePoint(getAverage(inMesh.getVectors(face.points)));

            for (int i = 0; i < face.points.size(); i++) {
                int currentPoint = face.points.get(i);
                int previousPoint = face.getPreviousPointInFaceOrder(i);
                int nextPoint = face.getNextPointInFaceOrder(i);

                ProcessingPoint processingPoint = processingPoints.get(currentPoint);

                ProcessingEdge inEdge = processingPoint.getEdge(previousPoint);
                if (inEdge == null) {
                    inEdge = new ProcessingEdge(previousPoint, currentPoint,
                            getAverage(inMesh.getVectors(previousPoint, currentPoint)));
                    processingEdges.add(inEdge);
                    processingPoint.associatedEdges.add(inEdge);
                    processingPoints.get(previousPoint).associatedEdges.add(inEdge);
                }
                ProcessingEdge outEdge = processingPoint.getEdge(nextPoint);
                if (outEdge == null) {
                    outEdge = new ProcessingEdge(currentPoint, nextPoint,
                            getAverage(inMesh.getVectors(currentPoint, nextPoint)));
                    processingEdges.add(outEdge);
                    processingPoint.associatedEdges.add(outEdge);
                    processingPoints.get(nextPoint).associatedEdges.add(outEdge);
                }

                Point3D newPoint = new Point3D(pointsCounter++, getAverage(
                        inMesh.getPoint(processingPoint.originalPoint).getVector(),
                        processingFace.facePoint,
                        inEdge.midPoint,
                        outEdge.midPoint));

                newMesh.addPoint(newPoint);
                processingFace.newPoints.add(newPoint.getId());
                processingPoint.newPoints.add(newPoint.getId());
                if (inEdge.pointTo == currentPoint) {
                    inEdge.newPoints[2] = newPoint.getId();
                } else {
                    inEdge.newPoints[0] = newPoint.getId();
                }
                if (outEdge.pointFrom == currentPoint) {
                    outEdge.newPoints[3] = newPoint.getId();
                } else {
                    outEdge.newPoints[1] = newPoint.getId();
                }
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
            this.associatedEdges = new ArrayList<ProcessingEdge>(3);
            this.newPoints = new ArrayList<Integer>(3);
        }

        public ProcessingEdge getEdge(int otherPoint) {
            for (ProcessingEdge edge: associatedEdges) {
                if (edge.pointFrom == otherPoint || edge.pointTo == otherPoint)
                    return edge;
            }
            return null;
        }
    }

    private static class ProcessingEdge {
        public Vector3D midPoint;
        public int pointFrom, pointTo;
        public int[] newPoints = new int[4];

        private ProcessingEdge(int pointFrom, int pointTo, Vector3D midPoint) {
            this.pointFrom = pointFrom;
            this.pointTo = pointTo;
            this.midPoint = midPoint;
        }
    }

    private static class ProcessingFace {
        public List<ProcessingEdge> associatedEdges;
        public List<Integer> newPoints;
        public Vector3D facePoint;

        private ProcessingFace(int pointsCount) {
            this.associatedEdges = new ArrayList<ProcessingEdge>(pointsCount);
            this.newPoints = new ArrayList<Integer>(pointsCount);
        }

        public void setFacePoint(Vector3D facePoint) {
            this.facePoint = facePoint;
        }
    }
}
