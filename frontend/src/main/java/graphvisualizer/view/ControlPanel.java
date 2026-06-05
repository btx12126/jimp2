package graphvisualizer.view;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private final JButton loadFileButton;
    private final JRadioButton frRadioButton;
    private final JRadioButton tutteRadioButton;
    private final JTextField iterationField;
    private final JButton startButton;

    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        setPreferredSize(new Dimension(300, 0));

        loadFileButton = createModernButton("Wczytaj plik", new Color(70, 130, 180));
        loadFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel algoLabel = new JLabel("Wybierz algorytm:");
        algoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        algoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        frRadioButton = new JRadioButton("Freuchterman-Reingold", true);
        tutteRadioButton = new JRadioButton("Tutte");
        frRadioButton.setOpaque(false);
        tutteRadioButton.setOpaque(false);
        frRadioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutteRadioButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup algorithmGroup = new ButtonGroup();
        algorithmGroup.add(frRadioButton);
        algorithmGroup.add(tutteRadioButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setOpaque(false);
        radioPanel.add(frRadioButton);
        radioPanel.add(tutteRadioButton);
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iterationLabel = new JLabel("Liczba iteracji:");
        iterationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iterationLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        iterationField = new JTextField("10");
        iterationField.setMaximumSize(new Dimension(100, 30));
        iterationField.setAlignmentX(Component.CENTER_ALIGNMENT);
        iterationField.setHorizontalAlignment(JTextField.CENTER);

        startButton = createModernButton("Start", new Color(34, 139, 34));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(loadFileButton);
        add(algoLabel);
        add(radioPanel);
        add(iterationLabel);
        add(iterationField);
        add(Box.createVerticalGlue());
        add(startButton);
    }

    private JButton createModernButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }

    public JButton getLoadFileButton() { return loadFileButton; }
    public JRadioButton getFrRadioButton() { return frRadioButton; }
    public JRadioButton getTutteRadioButton() { return tutteRadioButton; }
    public JTextField getIterationField() { return iterationField; }
    public JButton getStartButton() { return startButton; }
}