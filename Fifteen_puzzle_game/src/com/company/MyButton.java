package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

class MyButton extends JButton {

    private boolean isLastButton;
    private static final Color FOREGROUND_COLOR = new Color(9, 101, 99);

    public MyButton() {

        super();

        initUI();
    }

    public MyButton(Image image) {

        super(new ImageIcon(image));

        initUI();
    }

    public MyButton(String string){

        super(string);
        setFont(new Font("SansSerif", Font.BOLD, 40));
        setForeground(Color.white);
        setBackground(FOREGROUND_COLOR);
        setFocusPainted(false);
        initUI();
    }

    private void initUI() {

        isLastButton = false;
        setBorder(BorderFactory.createLineBorder(Color.gray));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        });
    }

    public void setLastButton() {

        isLastButton = true;
    }

    public boolean isLastButton() {

        return isLastButton;
    }
}
