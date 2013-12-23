import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.FloatBuffer;

import static javax.media.opengl.GL.*;  // GL constants
import static javax.media.opengl.GL2.*; // GL2 constants

public class MyJoglPanel extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private GLU glu;  // for the GL Utility

    private int WIDTH = Main.CANVAS_WIDTH, HEIGHT = Main.CANVAS_HEIGHT;
    private JLabel labelRGB;
    private JLabel labelHSV;
    private JLabel colorBox;

    private float rotationX = -135.0f;
    private float rotationY = -45.0f;
    private float zoom = -3.0f;
    private float zoomAmount = 0.03f;

    private HSVCylinder cylinder = new HSVCylinder();

    private final ReadPixelContext readPixelContext = new ReadPixelContext();

    public static final float[] WHITE = {1.0f, 1.0f, 1.0f, 1.0f};
    public static final float[] GREY = {0.5f, 0.5f, 0.5f, 1.0f};
    public static final float[] DARK_GREY = {0.2f, 0.2f, 0.2f, 1.0f};
    public static final float[] BLACK = {0.0f, 0.0f, 0.0f, 1.0f};
    public static final float[] RED = {1.0f, 0.0f, 0.0f, 1.0f};
    public static final float[] BLUE = {0.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] GREEN = {0.0f, 1.0f, 0.0f, 1.0f};
    public static final float[] YELLOW = {1.0f, 1.0f, 0.0f, 1.0f};

    private int mousePositionX;
    private int mousePositionY;

    public MyJoglPanel(GLCapabilities capabilities, JLabel labelRGB, JLabel colorBox, JLabel labelHSV) {
        super(capabilities);
        this.addGLEventListener(this);
        this.labelRGB = labelRGB;
        this.colorBox = colorBox;
        this.labelHSV = labelHSV;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();                         // get GL Utilities
        gl.glClearColor(0.6f, 0.6f, 0.6f, 1.0f); // set background (clear) color
        gl.glClearDepth(1.0f);      // set clear depth value to farthest
        gl.glEnable(GL_DEPTH_TEST); // enables depth testing
        gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
        gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting
    }

    @Override
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

        WIDTH = width;
        HEIGHT = height;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
        gl.glLoadIdentity();  // reset the model-view matrix

        // ----- Your OpenGL rendering code here (Render a white triangle for testing) -----
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, zoom); // translate into the screen
        gl.glRotatef(rotationX, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotationY , 1.0f, 0.0f, 0.0f);
        cylinder.draw(gl);
        gl.glPopMatrix();

        if (readPixelContext.toRead) {
            FloatBuffer buffer = FloatBuffer.allocate(4);
            gl.glReadBuffer(GL2.GL_FRONT);
            gl.glReadPixels(readPixelContext.x, readPixelContext.y, 1, 1, GL2.GL_RGBA, GL2.GL_FLOAT, buffer);
            float[] pixels = buffer.array();
            readPixelContext.lastColor = new HSVCylinder.RGB(pixels[0], pixels[1], pixels[2]);
            readPixelContext.toRead = false;
            labelRGB.setText(readPixelContext.lastColor.toString());
            labelHSV.setText(readPixelContext.lastColor.toHSV().toString());
            colorBox.setBackground(new Color(readPixelContext.lastColor.getR(), readPixelContext.lastColor.getG(), readPixelContext.lastColor.getB()));
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        readPixelContext.x = e.getX();
        readPixelContext.y = HEIGHT - e.getY();
        readPixelContext.toRead = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePositionX = e.getX();
        mousePositionY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        rotationX += (e.getX() - mousePositionX)/ 2.0f;
        mousePositionX = e.getX();
        rotationY += (e.getY() - mousePositionY)/ 2.0f;
        mousePositionY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        cylinder.addStartAngle(e.getWheelRotation());
    }

    private class ReadPixelContext {
        public int x, y;
        public boolean toRead = false;
        public HSVCylinder.RGB lastColor;
    }

}