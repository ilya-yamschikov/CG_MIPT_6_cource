import geometry.Face;
import geometry.Mesh;

import javax.media.opengl.GL2;

public class Model {
    private Mesh originalMesh;

    public Model(Mesh mesh) {
        this.originalMesh = mesh;
    }

    public void draw(GL2 gl) {
        draw(gl, originalMesh);
    }

    private void draw(GL2 gl, Mesh mesh) {
        for (Face face: mesh.getFaces()) {
            gl.glBegin(GL2.GL_POLYGON);
            if (face.getNormal() != null)
                gl.glNormal3dv(face.getNormal(), 0);
            for (int pointID: face.points) {
                gl.glVertex3dv(mesh.getPoint(pointID).toArray(), 0);
            }
            gl.glEnd();
        }
    }
}
