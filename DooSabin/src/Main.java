import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static String TITLE = "JOGL 2.0 Setup (GLCanvas)";  // window's title
    private static final int CANVAS_WIDTH = 640;  // width of the drawable
    private static final int CANVAS_HEIGHT = 480; // height of the drawable
    private static final int FPS = 60; // animator's target frames per second

    private static GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints constrains = new GridBagConstraints();
        constrains.fill = GridBagConstraints.HORIZONTAL;   // компоненты (не) растягиваются
        constrains.gridx = x;
        constrains.gridy = y;
        constrains.insets = new Insets(2, 2, 4, 2);
        constrains.gridwidth = 1;
        constrains.anchor = GridBagConstraints.CENTER;    // компонент прижимается к ...
        return constrains;
    }

    public static void main(String args[]) {
        // Run the GUI codes in the event-dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the OpenGL rendering canvas
                MyJoglPanel GLPanel = new MyJoglPanel();
                GLPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

                // Create a animator that drives canvas' display() at the specified FPS.
                final FPSAnimator animator = new FPSAnimator(GLPanel, FPS, true);

                // Create the top-level container
                final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame

                //SETTINGS PANEL
                ImageIcon downButtonIcon = new ImageIcon("down.png");
                downButtonIcon.setImage(downButtonIcon.getImage().getScaledInstance(24, 20, java.awt.Image.SCALE_SMOOTH));
                ImageIcon upButtonIcon = new ImageIcon("up.png");
                upButtonIcon.setImage(upButtonIcon.getImage().getScaledInstance(24,20,java.awt.Image.SCALE_SMOOTH));
                JPanel settingsPanel = new JPanel();
                settingsPanel.setLayout(new GridBagLayout());
                JButton buttonUp = new JButton();
                buttonUp.setIcon(upButtonIcon);
                JButton buttonDown = new JButton();
                buttonDown.setIcon(downButtonIcon);
                JLabel counter = new JLabel("0");
                settingsPanel.add(buttonUp, getConstraints(0,0));
                settingsPanel.add(counter, getConstraints(0,1));
                settingsPanel.add(buttonDown, getConstraints(0,2));
                Container pane = frame.getContentPane();
                pane.add(settingsPanel, BorderLayout.LINE_END);


                pane.add(GLPanel);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Use a dedicate thread to run the stop() to ensure that the
                        // animator stops before program exits.
                        new Thread() {
                            @Override
                            public void run() {
                                if (animator.isStarted()) animator.stop();
                                System.exit(0);
                            }
                        }.start();
                    }
                });
                GLPanel.addMouseListener(GLPanel);
                GLPanel.addMouseMotionListener(GLPanel);
                frame.setTitle(TITLE);
                frame.pack();
                frame.setVisible(true);
                animator.start(); // start the animation loop
            }
        });
    }
}
