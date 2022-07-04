package com.tom.jmazegen.presenter;

import com.tom.jmazegen.maze.Cell;
import com.tom.jmazegen.maze.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MazePresenter {
    Grid grid;
    private final int cellSize;
    private final int wallThickness;
    private final int size;

    private final String name;

    private final int w;
    private final int h;
    private BufferedImage bimage;

    public MazePresenter(Grid grid) {
        this(grid, "Maze");
    }

    public MazePresenter(Grid grid, String name) {
        this(grid, name, 20, 2);
    }

    public MazePresenter(Grid grid, String name, int cellSize, int wallThickness) {
        this.grid = Objects.requireNonNull(grid, "grid must not be null");
        this.cellSize = cellSize;
        this.wallThickness = wallThickness;
        this.size = cellSize + wallThickness;

        this.name = name;

        w = setSize(grid.getWidth());
        h = setSize(grid.getHeight());
    }

    private int setSize(int size) {
        return size * cellSize + (size + 1) * wallThickness;
    }

    private void buildImg() {
        bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D img = bimage.createGraphics();

        img.setColor(Color.LIGHT_GRAY);
        img.fillRect(0, 0, w, h);
        img.setColor(Color.BLACK);
        img.setStroke(new BasicStroke(wallThickness));

        for (ArrayList<Cell> row : grid.getCells()) {
            for (Cell cell : row) {
                int x1 = cell.getColumn() * size;
                int y1 = cell.getRow() * size;
                int x2 = (cell.getColumn() + 1) * size;
                int y2 = (cell.getRow() + 1) * size;

                int offset1 = wallThickness / 2;
                int offset2 = 1;

                if (cell.top == null) {
                    img.drawLine(x1, y1 + offset1, x2, y1 + offset1);
                }
                if (cell.left == null) {
                    img.drawLine(x1 + offset1, y1, x1 + offset1, y2 + offset1);
                }

                if (!cell.isLinkedTo(cell.bottom)) {
                    img.drawLine(x1 + offset1, y2 + offset1, x2 + offset1, y2 + offset1);
                }
                if (!cell.isLinkedTo(cell.right)) {
                    img.drawLine(x2 + offset1, y1 + offset1, x2 + offset1, y2 + offset1);
                }
            }
        }
    }

    private static class RenderPanel extends JPanel {
        BufferedImage img;

        public RenderPanel(BufferedImage image) {
            this.img = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public void showImage() {
        buildImg();
        JFrame f = new JFrame(name);
        RenderPanel p = new RenderPanel(bimage);

        f.add(p);
        f.pack();
        f.setSize(w, h);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void saveImage(String fileName) {
        buildImg();
        try {
            ImageIO.write(bimage, "PNG", new File("./" + fileName));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void printToConsole() {
        String output = "+" + "----+".repeat(grid.getWidth());
        System.out.println(output);

        String top;
        String bottom;
        String body = "    ";
        String rightBoundary;
        String corner = "+";
        String bottomBoundary;

        for (int i = 0; i < grid.getHeight(); i++) {
            top = "|";
            bottom = "+";

            for (int j = 0; j < grid.getWidth(); j++) {
                Cell cell = grid.getCells().get(i).get(j);
                if (cell == null) {
                    cell = new Cell(-1, -1);
                }

                rightBoundary = "|";
                if (cell.isLinkedTo(cell.right)) {
                    rightBoundary = " ";
                }
                top += body + rightBoundary;

                bottomBoundary = "----";
                if (cell.isLinkedTo(cell.bottom)) {
                    bottomBoundary = "    ";
                }
                bottom += bottomBoundary + corner;
            }

            System.out.println(top);
            System.out.println(bottom);
        }
    }
}
