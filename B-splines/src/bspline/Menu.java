package bspline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static bspline.ValidationResult.ValidationCode.FAIL;
import static bspline.ValidationResult.ValidationCode.SUCCESS;

public class Menu {
    final JFrame settingsFrame = new JFrame();
    final Drawer drawer = new Drawer();

    private void setFrameParameters() {
        settingsFrame.setSize(400, 180);
        settingsFrame.setTitle("Settings");
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setResizable(false);
    }

    private static GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints constrains = new GridBagConstraints();
        constrains.fill = GridBagConstraints.HORIZONTAL;   // компоненты (не) растягиваются
        constrains.gridx = x;
        constrains.gridy = y;
        constrains.insets = new Insets(2, 2, 4, 2);
        constrains.gridwidth = 1;
        constrains.anchor = GridBagConstraints.NORTH;    // компонент прижимается к ...
        return constrains;
    }

    private static GridBagConstraints getConstraints(int x, int y, int anchor) {
        GridBagConstraints constrains = getConstraints(x,y);
        constrains.anchor = anchor;
        return constrains;
    }

    private static JPanel getDefaultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    private static Integer validateIntegerInput(String in) {
        Integer res;
        try {
            res = Integer.parseInt(in);
        } catch (NumberFormatException e) {
            return null;
        }
        return res;
    }

    private static Double validateDoubleInput(String in) {
        Double res;
        try {
            res = Double.parseDouble(in);
        } catch (NumberFormatException e) {
            return null;
        }
        return res;
    }

    private static ValidationResult validateParameters(int pointsCount, int splineDegree, double[] knots) {
        if (pointsCount < 2)
            return new ValidationResult(FAIL, "Points count should be > 1");
        if (knots.length != pointsCount + splineDegree + 1)
            return new ValidationResult(FAIL,
                    "Values doesn't satisfy condition (knots count = points count + spline degree + 1) \n "
                            +" knots count = " + knots.length + "; points count = " + pointsCount + "; spline degree = " + splineDegree);
        if ( splineDegree < 1 || splineDegree > pointsCount - 1)
            return new ValidationResult(FAIL,
                    "Values doesn't satisfy condition (1 <= spline degree <= points count - 1) \n "
                            +"points count = " + pointsCount + "; spline degree = " + splineDegree);
        for (int i = 0; i < knots.length-1; i++) {
            if (knots[i] >knots[i+1])
                return new ValidationResult(FAIL, "Knots values are not in non decreasing order:\n knots[" + i + "] = " + knots[i] + "; knots[" + (i+1) + "] = " + knots[i+1]);
        }

        return new ValidationResult(SUCCESS);
    }

    private static ActionListener buttonOKListener(final Drawer drawer, final JTextField nSource, final JTextField kSource, final JTextArea knotsSource) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer pointsCount = validateIntegerInput(nSource.getText());
                Integer splineDegree = validateIntegerInput(kSource.getText());
                if (pointsCount == null) {
                    JOptionPane.showMessageDialog(null, "Wrong input: points count is not an integer number");
                    return;
                }
                if (splineDegree == null) {
                    JOptionPane.showMessageDialog(null, "Wrong input: spline degree is not an integer number");
                    return;
                }
                String[] strKnotsArray = knotsSource.getText().split("\\s*,\\s*");
                double[] knots = new double[strKnotsArray.length];
                for (int i = 0; i < strKnotsArray.length; i++) {
                    Double buf = validateDoubleInput(strKnotsArray[i]);
                    if (buf == null)  {
                        JOptionPane.showMessageDialog(null, "Wrong input: knots is not an array of numbers");
                        return;
                    }
                    knots[i] = buf;
                }

                ValidationResult validationResult = validateParameters(pointsCount, splineDegree, knots);
                if (!validationResult.passed()) {
                    JOptionPane.showMessageDialog(null, "Input parameters are not valid: \n" + validationResult.getMsg());
                } else {
                    drawer.drawAll(pointsCount, splineDegree + 1, knots);
                }
            }
        };
    }

    private static ActionListener buttonExitListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }

    private void setControls(JPanel panel) {
        JPanel nkPanel = getDefaultPanel();
            JPanel nPanel = getDefaultPanel();
                nPanel.add(new JLabel("Points count: "), getConstraints(0,0, GridBagConstraints.WEST));
                JTextField nTextField = new JTextField(Integer.toString(5), 5);
                nPanel.add(nTextField, getConstraints(1,0));
        nkPanel.add(nPanel,  getConstraints(0,0, GridBagConstraints.WEST));
            JPanel kPanel = getDefaultPanel();
                kPanel.add(new JLabel("spline degree: "), getConstraints(0,0, GridBagConstraints.WEST));
                JTextField kTextField = new JTextField(Integer.toString(2), 5);
                kPanel.add(kTextField, getConstraints(1,0));
        nkPanel.add(kPanel,  getConstraints(1,0));

        JPanel knotsPanel = getDefaultPanel();
            knotsPanel.add(new JLabel("knots:"), getConstraints(0,0, GridBagConstraints.NORTH));
            JTextArea knotsTextArea = new JTextArea(3, 25);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(knotsTextArea);
            knotsTextArea.setLineWrap(true);
            knotsTextArea.setText("0,0,0,2,2,3,3,3");
            knotsPanel.add(scrollPane, getConstraints(1,0));

        JPanel buttonsPanel = getDefaultPanel();
            JButton buttonOK = new JButton("OK");
            buttonOK.addActionListener(buttonOKListener(drawer, nTextField, kTextField, knotsTextArea));
            buttonsPanel.add(buttonOK, getConstraints(1,0));
            JButton buttonExit = new JButton("Exit");
            buttonExit.addActionListener(buttonExitListener());
            buttonsPanel.add(buttonExit, getConstraints(0, 0));

        panel.add(nkPanel, getConstraints(0,0));
        panel.add(knotsPanel, getConstraints(0,1));
        panel.add(buttonsPanel, getConstraints(0,2));
    }

    private void setupPanel(JPanel panel) {
        panel.setLayout(new GridBagLayout());
    }

    public void createMenu() {
        setFrameParameters();

        JPanel mainPanel = new JPanel();
        setupPanel(mainPanel);
        setControls(mainPanel);
        settingsFrame.add(mainPanel);

        settingsFrame.requestFocusInWindow();
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
    }

    public static void main(String args[]) {
        Menu menu = new Menu();
        menu.createMenu();
    }
}
