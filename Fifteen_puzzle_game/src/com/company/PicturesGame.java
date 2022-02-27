package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static javax.imageio.ImageIO.read;

public  class PicturesGame extends JPanel {

    private MyButton lastButton;
    private int moves;
    private final int level;
    private final JFrame frame;

    public PicturesGame(int level, JFrame frame) {
        this.level = level;
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
        setLayout(new GridLayout(4, 4, 0, 0));

        BufferedImage source = loadImage();
        int h = getNewHeight(source.getWidth(), source.getHeight());
        BufferedImage resized = resizeImage(source, h
        );

        int width = resized.getWidth(null);
        int height = resized.getHeight(null);

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                Image image = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / 4, i * height / 4,
                                (width / 4), height / 4)));

                MyButton button = new MyButton(image);
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
        }

        int countInversions;
        int ok;

        do {
            countInversions = 0;
            Collections.shuffle(buttons);
            for (int i = 0; i < buttons.size(); i++) {
                Point p1 = (Point) buttons.get(i).getClientProperty("position");
                int x1 = (int) p1.getX();
                int y1 = (int) p1.getY();
                for (int j = 0; j < i; j++) {
                    Point p2 = (Point) buttons.get(j).getClientProperty("position");
                    int x2 = (int) p2.getX();
                    int y2 = (int) p2.getY();
                    if (4 * x2 + y2 + 1 > 4 * x1 + y1 + 1)
                        countInversions++;
                }
            }
            if (countInversions % 2 == 0)
                ok = 1;
            else
                ok = 0;

        } while (ok == 0);

        buttons.add(lastButton);

        int NUMBER_OF_BUTTONS = 16;
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {

            MyButton btn = buttons.get(i);
            add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
            int key = 1;
            btn.addActionListener(new ClickAction(buttons, this, solution, key, level, frame));
        }
    }

    private int getNewHeight(int w, int h) {

        int DESIRED_WIDTH = 600;
        double ratio = DESIRED_WIDTH / (double) w;
        return (int) (h * ratio);
    }

    private BufferedImage loadImage() {
        BufferedImage image = null;
        String lev = "level";
        String jpg = ".jpg";
        try {
            image = read(new File(lev + level + jpg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int height) {
        BufferedImage resizedImage = new BufferedImage(600, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 600, height, null);
        g.dispose();
        return resizedImage;
    }
    public int getMoves() {

        return moves;
    }

    public void incMoves() {

        moves++;
    }
}


