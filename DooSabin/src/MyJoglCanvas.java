import geometry.Mesh;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL.*;  // GL constants
import static javax.media.opengl.GL2.*; // GL2 constants

public class MyJoglCanvas extends GLCanvas implements GLEventListener {
    private GLU glu;  // for the GL Utility

    private float rotation = 0.0f;

    private Model model = new Model(Mesh.CUBE);

    private final float[] WHITE = {1.0f, 1.0f, 1.0f, 1.0f};
    private final float[] DARK_GREY = {0.2f, 0.2f, 0.2f, 1.0f};
    private final float[] BLACK = {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] RED = {1.0f, 0.0f, 0.0f, 1.0f};

    public MyJoglCanvas() {
        this.addGLEventListener(this);
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();                         // get GL Utilities
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glEnable(GL_DEPTH_TEST); // enables depth testing
        gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
        gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting
        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_LIGHT0);
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context

        if (height == 0) height = 1;   // prevent divide by zero
        float aspect = (float)width / height;

        // Set the view port (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
        gl.glLoadIdentity();             // reset projection matrix
        glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar

        // Enable the model-view transform
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity(); // reset
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
        gl.glLoadIdentity();  // reset the model-view matrix

        // ----- Your OpenGL rendering code here (Render a white triangle for testing) -----
        gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{1.0f, 4.0f, 1.0f, 1.0f}, 0);
        rotation += 1.5f;
        gl.glPushMatrix();
        gl.glTranslatef(-1.5f, -1.5f, -6.0f); // translate into the screen
        gl.glRotatef(rotation , 0.0f, 1.0f, 0.0f); // rotate about the y-axis
        model.draw(gl);
        gl.glPopMatrix();
    }

    public void dispose(GLAutoDrawable drawable) {}
}