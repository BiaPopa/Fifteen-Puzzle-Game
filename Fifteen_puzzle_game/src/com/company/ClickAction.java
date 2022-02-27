package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ClickAction extends AbstractAction {
    private final ArrayList<MyButton> buttons;
    private final JPanel panel;
    private final NumbersGame numbersGame;
    private final PicturesGame picturesGame;
    private final JFrame frame;
    private final ArrayList<Point> solution;
    private final int i;
    private int level;

    public ClickAction(ArrayList<MyButton> buttons, NumbersGame game,
                       ArrayList<Point> solution, int i, JFrame frame) {
        this.buttons = buttons;
        this.numbersGame = game;
        this.picturesGame = null;
        this.panel = game;
        this.frame = frame;
        this.solution = solution;
        this.i = i;
    }

    public ClickAction(ArrayList<MyButton> buttons, PicturesGame game,
                       ArrayList<Point> solution, int i, int level, JFrame frame) {
        this.buttons = buttons;
        this.numbersGame = null;
        this.picturesGame = game;
        this.panel = game;
        this.frame = frame;
        this.solution = solution;
        this.level = level;
        this.i = i;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        checkButton(e);
        checkSolution();
    }

    private void checkButton(ActionEvent e) {

        int nullButton = 0;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isLastButton()) {
                nullButton = i;
                break;
            }
        }

        MyButton button = (MyButton) e.getSource();
        int currentButton = buttons.indexOf(button);

        if ((currentButton - 1 == nullButton) || (currentButton + 1 == nullButton)
                || (currentButton - 4 == nullButton) || (currentButton + 4 == nullButton)) {
            Collections.swap(buttons, currentButton, nullButton);
            if (i == 0) {
                numbersGame.incMoves();
            } else {
                picturesGame.incMoves();
            }
            updateButtons();
        }
    }

    private void updateButtons() {

        panel.removeAll();

        for (JComponent btn : buttons) {

            panel.add(btn);
        }

        panel.validate();
    }

    private void checkSolution() {

        List<Point> current = new ArrayList<>();

        for (JComponent btn : buttons) {
            current.add((Point) btn.getClientProperty("position"));
        }

        if (compareList(solution, current)) {
            String[] options = new String[]{"Restart",
                    "Return to menu"};
            int moves;
            if (i == 0){
                moves = numbersGame.getMoves();
            } else {
                moves = picturesGame.getMoves();
            }
            int option= JOptionPane.showOptionDialog(null,
                    "moves: " + moves, "Congratulations",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, null);
            if(option == 0) {
                frame.getContentPane().remove(panel);
                 if(i == 0) {
                     frame.getContentPane().add(new NumbersGame(frame), BorderLayout.CENTER);
                 }
                 else{
                     frame.getContentPane().add(new PicturesGame(level, frame), BorderLayout.CENTER);
                 }
                frame.getContentPane().revalidate();
            }
            else if(option == 1){
                String[] restoptions = new String[]{"Fifteen puzzle game with pictures",
                        "Fifteen puzzle game with numbers"};
                int restoption = JOptionPane.showOptionDialog(null,
                        "Choose your game", "Fifteen puzzle game",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, restoptions, null);
                if (restoption == 0) {
                    String[] options2 = new String[]{"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6"};
                    int option2 = JOptionPane.showOptionDialog(null, "Levels",
                            "Fifteen puzzle game with pictures",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options2, null);
                    frame.getContentPane().remove(panel);
                    frame.getContentPane().add(new PicturesGame(option2, frame), BorderLayout.CENTER);
                    frame.getContentPane().revalidate();
                } else if (restoption == 1) {
                    frame.getContentPane().remove(panel);
                    frame.getContentPane().add(new NumbersGame(frame), BorderLayout.CENTER);
                    frame.getContentPane().revalidate();
                }
            }
        }
    }

    public static boolean compareList(List<Point> ls1, List<Point> ls2) {
        return ls1.toString().contentEquals(ls2.toString());
    }

}
