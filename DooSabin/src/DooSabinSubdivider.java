import geometry.Face;
import geometry.Mesh;
import geometry.Point3D;

import java.util.ArrayList;
import java.util.List;

public class DooSabinSubdivider {
    public static Mesh process(Mesh inMesh) {
        Mesh newMesh = new Mesh();
        List<ProcessingFace> processingFaces = new ArrayList<ProcessingFace>(inMesh.getFaces().size());
        List<ProcessingPoint> processingPoints = new ArrayList<ProcessingPoint>(inMesh.getPointsSize());
        List<ProcessingEdge> processingEdges = new ArrayList<ProcessingEdge>();
        int pointsCounter = 0;

        for (Face face: inMesh.getFaces()) {
            ProcessingFace processingFace = new ProcessingFace();
            //Point3D point = new Point3D(-1, );
            processingFaces.add(processingFace);
        }
        return newMesh;
    }

    private static class ProcessingPoint {
        int originalPoint;
        List<Integer> newPoints;
    }

    private static class ProcessingEdge {
        int point1, point2;
        List<Integer> newPoints;
    }

    private static class ProcessingFace {
        List<Integer> newPoints;
    }
}
