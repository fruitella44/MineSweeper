package minesweeper.com;

import javax.swing.*;
import java.awt.*;

public class Window {
    private static JFrame frame;
    private static String title;

    public Window(int width, int height, int gridsize, String title, Game game) {
        Window.title = title;
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.DARK_GRAY);

    }
}
