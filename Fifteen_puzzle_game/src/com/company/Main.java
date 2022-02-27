package com.company;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::run);
    }
    private static void openGame(int option) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game of Fifteen");
        frame.setResizable(true);
        frame.getContentPane().add(new PicturesGame(option, frame), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static void run() {
        String[] options = new String[]{"Fifteen puzzle game with pictures",
                "Fifteen puzzle game with numbers"};
        int option = JOptionPane.showOptionDialog(null,
                "Choose your game", "Fifteen puzzle game",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
        if (option == 0) {
            String[] options2 = new String[]{"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6"};
            int option2 = JOptionPane.showOptionDialog(null, "Levels",
                    "Fifteen puzzle game with pictures",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, null);
            openGame(option2);
        } else if (option == 1) {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Game of Fifteen");
            frame.setResizable(true);
            frame.add(new NumbersGame(frame), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}

