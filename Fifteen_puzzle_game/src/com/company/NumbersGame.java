package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public  class NumbersGame extends JPanel {

    private MyButton lastButton;
    private final JFrame frame;
    private int moves;

    public NumbersGame(JFrame frame) {
        this.frame = frame;
        this.moves = 0;
        initUI();
    }

    public void initUI() {

        ArrayList<Point> solution = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                solution.add(new Point(i, j));

        ArrayList<MyButton> buttons = new ArrayList<>();
        setBorder(BorderFactory.createLineBorder(Color.gray));
        int dim = 500;
        setPreferredSize(new Dimension(dim, dim));
        setLayout(new GridLayout(4, 4, 0, 0));

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {

                MyButton button = new MyButton(String.valueOf(i * 4 + j + 1));
                button.putClientProperty("position", new Point(i, j));

                if (i == 3 && j == 3) {
                    lastButton = new MyButton();
                    lastButton.setBorderPainted(false);
                    lastButton.setContentAreaFilled(false);
                    lastButton.setLastButton();
                    lastButton.putClientProperty("position", new Point(i, j));
                } else {
                    buttons.add(button);
                }
            }

        int NUMBER_OF_BUTTONS = 16;
        int countInversions;
        int ok;

        do {
            countInversions = 0;
            Collections.shuffle(buttons);
            for (int i = 0; i < buttons.size(); i++) {
                int y = Integer.parseInt(buttons.get(i).getText());
                for (int j = 0; j < i; j++) {
                    int x = Integer.parseInt(buttons.get(j).getText());
                    if (x > y) {
                        countInversions++;
                    }
                }
            }
            if (countInversions % 2 == 0)
                ok = 1;
            else
                ok = 0;

        } while (ok == 0);

        buttons.add(lastButton);

        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {

            MyButton btn = buttons.get(i);
            add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
            int key = 0;
            btn.addActionListener(new ClickAction(buttons, this, solution, key, frame));
        }
    }

    public int getMoves() {

        return moves;
    }

    public void incMoves() {

        moves++;
    }
}





