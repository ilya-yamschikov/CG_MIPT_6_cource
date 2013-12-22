import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static String TITLE = "DooSabin subdivision example (via OpenGL)";  // window's title
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
                // Create the OpenGL rendering panel
                final MyJoglPanel GLPanel = new MyJoglPanel();
                GLPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

                // Create a animator that drives panel' display() at the specified FPS.
                final FPSAnimator animator = new FPSAnimator(GLPanel, FPS, true);

                // Create the top-level container
                final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame

                //SETTINGS PANEL
                JPanel settingsPanel = new JPanel();
                settingsPanel.setLayout(new GridBagLayout());
                final JLabel counter = new JLabel("0");
                JButton buttonLess = new JButton("<");
                buttonLess.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        counter.setText(GLPanel.reduceModelSubdivisions() + "");
                    }
                });
                JButton buttonMore = new JButton(">");
                buttonMore.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        counter.setText(GLPanel.addModelSubdivisions() + "");
                    }
                });
                settingsPanel.add(buttonLess, getConstraints(0,0));
                settingsPanel.add(counter, getConstraints(1,0));
                settingsPanel.add(buttonMore, getConstraints(2,0));
                Container pane = frame.getContentPane();
                pane.add(settingsPanel, BorderLayout.PAGE_END);

                // MENU BAR
                MenuBar menuBar = new MenuBar();
                Menu menu = new Menu("Objects");
                menuBar.add(menu);
                MenuItem cubeMenuItem = new MenuItem("Cube");
                cubeMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        counter.setText("0");
                        GLPanel.setModel(e.getActionCommand());
                    }
                });
                menu.add(cubeMenuItem);

                frame.setMenuBar(menuBar);

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
                GLPanel.addMouseWheelListener(GLPanel);
                frame.setTitle(TITLE);
                frame.pack();
                frame.setVisible(true);
                animator.start(); // start the animation loop
            }
        });
    }
}
