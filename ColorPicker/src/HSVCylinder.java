import javax.media.opengl.GL2;
import java.awt.*;

public class HSVCylinder {
    private final int zSteps = 5;
    private final int rotateSteps = 80;
    private final int innerSteps = 5;
    private double startAngle = 0.0;
    private int startColorAngle = 0;
    private int startColorAngleStep = 3;

    public void draw(GL2 gl) {
        int ro, z, i;
        RGB color;

        //BOTTOM
        for (ro = 0; ro < 3 * rotateSteps / 4; ro++) {
            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glNormal3d(0.0, -1.0, 0.0);
            setColor(new HSV(getColorAngle(ro), 100, 0), gl);
            gl.glVertex3dv(getNthPointOnXZplane(ro).to3DAddY(-0.5), 0);
            setColor(new HSV(getColorAngle(ro + 1), 100, 0), gl);
            gl.glVertex3dv(getNthPointOnXZplane(ro + 1).to3DAddY(-0.5), 0);
            setColor(new HSV(0, 0, 0), gl);
            gl.glVertex3d(0.0, -0.5, 0.0);
            gl.glEnd();
        }
          
        // SIDE
        for (z = 0; z <  zSteps; z++) {
            for (ro = 0; ro < 3 * rotateSteps / 4; ro++) {
                gl.glBegin(GL2.GL_QUADS);
                gl.glNormal3d(getNthPointOnXZplane(ro).x, 0.0, getNthPointOnXZplane(ro).y);
                setColor(new HSV(getColorAngle(ro), 100, (int) (100.0 * z / zSteps)), gl);
                gl.glVertex3dv(getNthPointOnXZplane(ro).to3DAddY(z * 1.0 / zSteps - 0.5), 0);
                setColor(new HSV(getColorAngle(ro + 1), 100, (int) (100.0 * z / zSteps)), gl);
                gl.glVertex3dv(getNthPointOnXZplane(ro+1).to3DAddY(z * 1.0 / zSteps - 0.5), 0);
                setColor(new HSV(getColorAngle(ro + 1), 100, (int) (100.0 * (z+1) / zSteps)), gl);
                gl.glVertex3dv(getNthPointOnXZplane(ro+1).to3DAddY((z+1) * 1.0 / zSteps - 0.5), 0);
                setColor(new HSV(getColorAngle(ro), 100, (int) (100.0 * (z+1) / zSteps)), gl);
                gl.glVertex3dv(getNthPointOnXZplane(ro).to3DAddY((z+1) * 1.0 / zSteps - 0.5), 0);
                gl.glEnd();
            }
        }

        //INNER X
        for (z = 0; z < zSteps; z++) {
            for (i = 0; i < innerSteps; i++) {
                gl.glBegin(GL2.GL_QUADS);
                gl.glNormal3d(0.0, 0.0, 1.0);

                setColor(new HSV(startColorAngle, (int) (100.0 * i / innerSteps), (int) (100.0 * z / zSteps)), gl);
                gl.glVertex3d(i * 1.0 / innerSteps, z * 1.0 / zSteps - 0.5, 0.0);

                setColor(new HSV(startColorAngle, (int) (100.0 * (i + 1) / innerSteps), (int) (100.0 * z / zSteps)), gl);
                gl.glVertex3d((i + 1) * 1.0 / innerSteps, z * 1.0 / zSteps - 0.5, 0.0);

                setColor(new HSV(startColorAngle, (int) (100.0 * (i+1) / innerSteps), (int) (100.0 * (z+1) / zSteps)), gl);
                gl.glVertex3d((i + 1) * 1.0 / innerSteps, (z + 1) * 1.0 / zSteps - 0.5, 0.0);

                setColor(new HSV(startColorAngle, (int) (100.0 * i / innerSteps), (int) (100.0 * (z + 1) / zSteps)), gl);
                gl.glVertex3d(i * 1.0 / innerSteps, (z + 1) * 1.0 / zSteps - 0.5, 0.0);

                gl.glEnd();
            }
        }

        //INNER Z
        for (z = 0; z < zSteps; z++) {
            for (i = 0; i < innerSteps; i++) {
                gl.glBegin(GL2.GL_QUADS);
                gl.glNormal3d(0.0, 0.0, 1.0);

                setColor(new HSV((startColorAngle + 270) % 360, (int) (100.0 * i / innerSteps), (int) (100.0 * z / zSteps)), gl);
                gl.glVertex3d(0.0, z * 1.0 / zSteps - 0.5, -i * 1.0 / innerSteps);

                setColor(new HSV((startColorAngle + 270) % 360, (int) (100.0 * (i + 1) / innerSteps), (int) (100.0 * z / zSteps)), gl);
                gl.glVertex3d(0.0, z * 1.0 / zSteps - 0.5, -(i + 1) * 1.0 / innerSteps);

                setColor(new HSV((startColorAngle + 270) % 360, (int) (100.0 * (i+1) / innerSteps), (int) (100.0 * (z+1) / zSteps)), gl);
                gl.glVertex3d(0.0, (z + 1) * 1.0 / zSteps - 0.5, -(i + 1) * 1.0 / innerSteps);

                setColor(new HSV((startColorAngle + 270) % 360, (int) (100.0 * i / innerSteps), (int) (100.0 * (z + 1) / zSteps)), gl);
                gl.glVertex3d(0.0, (z + 1) * 1.0 / zSteps - 0.5, -i * 1.0 / innerSteps);

                gl.glEnd();
            }
        }

        //TOP
        for (ro = 0; ro < 3 * rotateSteps / 4; ro++) {
            gl.glBegin(GL2.GL_TRIANGLES);
            gl.glNormal3d(0.0, 1.0, 0.0);
            setColor(new HSV(getColorAngle(ro), 100, 100), gl);
            gl.glVertex3dv(getNthPointOnXZplane(ro).to3DAddY(0.5), 0);
            setColor(new HSV(getColorAngle(ro + 1), 100, 100), gl);
            gl.glVertex3dv(getNthPointOnXZplane(ro + 1).to3DAddY(0.5), 0);
            setColor(new HSV(0, 0, 100), gl);
            gl.glVertex3d(0.0, 0.5, 0.0);
            gl.glEnd();
        }
    }

    public void addStartAngle(int n) {
        startColorAngle = (startColorAngle + n * startColorAngleStep + 360) % 360;
    }

    private static void setColor(HSV color, GL2 gl) {
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, color.toRGB().toFloatArray(), 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, color.toRGB().toFloatArray(), 0);
        gl.glColor3fv(color.toRGB().toFloatArray(), 0);
    }

    private int getColorAngle(int i) {
        return ((int)(i * 360.0 / rotateSteps + startColorAngle)) % 360;
    }

    private  Vertex2D getNthPointOnXZplane(int i) {
        return new Vertex2D(Math.cos(2 * i * Math.PI / rotateSteps + startAngle), Math.sin(2 * i * Math.PI / rotateSteps + startAngle));
    }

    public static class Vertex2D {
        private Vertex2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double[] to3DAddY(double y) {
            return new double[]{x, y, this.y};
        }

        public double x;
        public double y;
    }

    public static class HSV {
        private int H, S, V;

        private HSV(int h, int s, int v) {
            H = h;
            S = s;
            V = v;
        }

        public RGB toRGB() {
            Color color = new Color(Color.HSBtoRGB(H / 360.0f, S / 100.0f, V / 100.0f));
            return new RGB(color.getRed(), color.getGreen(), color.getBlue());
        }

        @Override
        public String toString() {
            return "H: " + H + " S: " + S +  " V: " + V;
        }
    }

    public static class RGB {
        private int R, G, B;

        public int getR() {
            return R;
        }

        public int getG() {
            return G;
        }

        public int getB() {
            return B;
        }

        public RGB(int r, int g, int b) {
            R = r;
            G = g;
            B = b;
        }

        public RGB(float r, float g, float b) {
            R = (int)(r * 255);
            G = (int)(g * 255);
            B = (int)(b * 255);
        }

        public float[] toFloatArray() {
            return new float[] {R / 255.0f, G / 255.0f, B / 255.0f, 1.0f};
        }

        public HSV toHSV() {
            double R = this.R / 255.0;
            double G = this.G / 255.0;
            double B = this.B / 255.0;

            double min = Math.min(Math.min(R,G),B);
            double max = Math.max(Math.max(R, G), B);

            int H,S,V;

            if (max == min){
                H = 0;
            } else if (max == R && G >= B) {
                H = (int)(60 * ((G-B))/(max - min));
            } else if (max == R && G < B) {
                H = (int)(60 * ((G-B))/(max - min)) + 360;
            } else if (max == G) {
                H = (int)(60 * ((B-R))/(max - min)) + 120;
            } else {
                H = (int)(60 * ((R-G))/(max - min)) + 240;
            }

            if (max == 0.0) {
                S = 0;
            } else {
                S = (int)((1.0 - min / max) * 100);
            }

            V = (int)(max * 100);

            return new HSV(H, S, V);
        }

        @Override
        public String toString() {
            return "R: " + R + " G: " + G +  " B: " + B;
        }
    }
}
