import geometry.Face;
import geometry.Mesh;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private int subdivisionsCount = 0;
    private List<Mesh> subdivisions;

    public Model(Mesh mesh) {
        this.subdivisions = new ArrayList<Mesh>(5);
        this.subdivisions.add(mesh);
    }

    public void draw(GL2 gl) {
        draw(gl, subdivisions.get(subdivisionsCount));
    }

    private void draw(GL2 gl, Mesh mesh) {
        for (Face face: mesh.getFaces()) {
            gl.glBegin(GL2.GL_POLYGON);
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, face.getColor(), 0);
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, face.getColor(), 0);
            if (face.getNormal() != null)
                gl.glNormal3dv(face.getNormal(), 0);
            for (int pointID: face.points) {
                gl.glVertex3dv(mesh.getPoint(pointID).toArray(), 0);
            }
            gl.glEnd();
        }
    }
}
