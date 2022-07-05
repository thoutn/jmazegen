package com.tom.jmazegen.presenter;

import com.tom.jmazegen.maze.Cell;
import com.tom.jmazegen.maze.CircGrid;
import com.tom.jmazegen.maze.Grid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Presenter is a class with all the methods used to render the generated maze
 * as an image or print it onto the console (only available for the rectangular mazes
 * for obvious reason).
 *
 * There is also a method to save the generated maze into an image file (.PNG).
 */
public final class Presenter {
    static Grid grid_;
    private static int cellSize_;
    private static int wallThickness_;
    private static int size;

    private static String name_;

    private static int w;
    private static int h;
    private static BufferedImage bimage;

    private static final int ANTIALIAS = 4;

    /**
     * Don't let anyone instantiate this class.
     */
    private Presenter() {}

    /**
     * Reads the generated maze to be rendered as an image.
     * @param grid the maze generated by the algorithm.
     */
    public static void configImg(Grid grid) {
        configImg(grid,20, 2);
    }

    /**
     * Reads the generated maze to be rendered as an image and
     * sets the configuration of the rendered image.
     * @param grid the maze generated by the algorithm
     * @param cellSize the size of each maze cell in pixels
     * @param wallThickness the thickness of the maze walls in pixels
     */
    public static void configImg(Grid grid, int cellSize, int wallThickness) {
        configImg(grid, cellSize, wallThickness, "Maze");
    }

    /**
     * Reads the generated maze to be rendered as an image and
     * sets the configuration of the rendered image.
     * @param grid the maze generated by the algorithm
     * @param cellSize the size of each maze cell in pixels
     * @param wallThickness the thickness of the maze walls in pixels
     * @param name the title of the rendered maze showing in the pop-up window
     */
    public static void configImg(Grid grid, int cellSize, int wallThickness, String name) {
        grid_ = grid;
        cellSize_ = cellSize;
        wallThickness_ = wallThickness;
        size = cellSize_ + wallThickness_;
        name_ = name;

        h = setSize(grid_.getHeight()) * ((grid_ instanceof CircGrid) ? 2 : 1);
        w =  (grid_ instanceof CircGrid) ? h : setSize(grid_.getWidth());
    }

    /**
     * Helper function that calculates the size of the maze in pixels.
     * @param size the width or height of the maze (number of cells)
     * @return The width or height of the maze in pixels.
     */
    private static int setSize(int size) {
        return size * (cellSize_ + wallThickness_);
    }

    /**
     * Renders the image of the maze.
     */
    private static void buildImg() {
        if (grid_ instanceof CircGrid) buildCircImg();
        else buildRectImg();
    }

    /**
     * Initialises the image build.
     * @return The initialised image.
     */
    private static Graphics2D initImg() {
        bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D img = bimage.createGraphics();

        img.setColor(Color.LIGHT_GRAY);
        img.setStroke(new BasicStroke(wallThickness_));

        return img;
    }

    /**
     * Helper function.
     * Changes the size (in pixels) of the maze that suits {@link Presenter#buildRectImg()} method.
     */
    private static void setRectSize() {
        w += wallThickness_/2;
        h += wallThickness_/2;
    }

    /**
     * Main method for rectangular mazes.
     * <p>
     * Builds a rectangular maze from the provided representation (<em>grid</em>).
     */
    private static void buildRectImg() {
        setRectSize();
        Graphics2D img = initImg();
        img.fillRect(0, 0, w, h);
        img.setColor(Color.BLACK);

        for (ArrayList<Cell> row : grid_.getCells()) {
            for (Cell cell : row) {
                int x1 = cell.getColumn() * size;
                int y1 = cell.getRow() * size;
                int x2 = (cell.getColumn() + 1) * size;
                int y2 = (cell.getRow() + 1) * size;

                int offset1 = wallThickness_ / 2;
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

    /**
     * Helper function.
     * Changes some image setups that suits {@link Presenter#buildCircImg()} method.
     */
    private static void setAntialias() {
        w *= ANTIALIAS;
        h *= ANTIALIAS;
        cellSize_ *= ANTIALIAS;
        wallThickness_ *= ANTIALIAS;
        size *= ANTIALIAS;
    }

    /**
     * Helper function.
     * Resets the image setups, changed in {@link Presenter#setAntialias()} method.
     */
    private static void unsetAntialias() {
        w /= ANTIALIAS;
        h /= ANTIALIAS;
        cellSize_ /= ANTIALIAS;
        wallThickness_ /= ANTIALIAS;
        size /= ANTIALIAS;
    }

    /**
     * Resizes the image to achieve the antialising effect for circular mazes.
     */
    private static void resampleImg() {
        unsetAntialias();

        Image resultingImage = bimage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage outputImg = new BufferedImage(w, h, bimage.getType());
        outputImg.getGraphics().drawImage(resultingImage, 0, 0, null);
        bimage = outputImg;
    }

    /**
     * Main method for circular mazes.
     * <p>
     * Builds a circular maze from the provided representation (<em>grid</em>).
     */
    private static void buildCircImg() {
        setAntialias();
        Graphics2D img = initImg();

        double heightOfCentre = 0.75; // height ratio of centre cell
        // draws the background of the maze
        double offsetBkg = (heightOfCentre - 1)*size;
        img.fill(new Ellipse2D.Double(0 - offsetBkg, 0 - offsetBkg, h + 2*offsetBkg, h + 2*offsetBkg));
        img.setColor(Color.BLACK);

        // draws the maze
        for (int row_ = 1; row_ < grid_.getHeight(); row_++) {
            double rad1 = (row_ + heightOfCentre) * size;
            double rad0 = rad1 - size;
            // specifies the frame to draw Arc2D or Ellipse2D in
            Rectangle2D.Double shape = new Rectangle2D.Double(w / 2. - rad0, h / 2. - rad0,
                                                       2*rad0, 2*rad0);

            int noCols = grid_.getCells().get(row_).size();
            double theta = 360. / noCols;

            for (int col_ = 0; col_ < noCols; col_++) {
                Cell cell = grid_.getCells().get(row_).get(col_);

                if (!cell.isLinkedTo(cell.top)) {
                    img.draw(new Arc2D.Double(shape,col_ * theta, theta, Arc2D.OPEN));
                }

                if (!cell.isLinkedTo(cell.left)) {
                    double c = Math.cos(Math.toRadians(theta) * col_);
                    double s = Math.sin(Math.toRadians(theta) * col_);

                    // counter-clockwise rotation matrix for Arc2D -> R = ((c, s), (-s, c))
                    int x1 = (int) (c * (rad0 + wallThickness_));
                    int y1 = (int) (-s * (rad0 + wallThickness_));
                    int x2 = (int) (c * rad1);
                    int y2 = (int) (-s * rad1);

                    img.drawLine(x1 + w/2, y1 + h/2, x2 + w/2, y2 + h/2);
                }
            }

            if (row_ == grid_.getHeight() - 1) {
                img.draw(new Ellipse2D.Double(w / 2. - rad1, h / 2. - rad1, 2*rad1, 2*rad1));
            }
        }

        resampleImg();
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

    /**
     * Renders the image of the maze and shows it in a popup window.
     */
    public static void showImage() {
        buildImg();
        JFrame f = new JFrame(name_);
        RenderPanel p = new RenderPanel(bimage);

        f.add(p);
        f.pack();
        f.setSize(w, h);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    /**
     * Saves the image of the maze into a .PNG file.
     * @param fileName the name of the file (without extension) the generated image is saved to
     */
    public static void saveImage(String fileName) {
        buildImg();
        try {
            ImageIO.write(bimage, "PNG", new File("./" + fileName + ".png"));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Prints a rectangular maze onto the console.
     * @throws UnsupportedOperationException when using for a circular maze.
     */
    public static void printToConsole() {
        if (grid_ instanceof CircGrid) {
            throw new UnsupportedOperationException("Can't show circular mazes in console. " +
                    "This feature is available only for rectangular mazes.");
        }

        String output = "+" + "----+".repeat(grid_.getWidth());
        System.out.println(output);

        String top;
        String bottom;
        String body = "    ";
        String rightBoundary;
        String corner = "+";
        String bottomBoundary;

        for (int i = 0; i < grid_.getHeight(); i++) {
            top = "|";
            bottom = "+";

            for (int j = 0; j < grid_.getWidth(); j++) {
                Cell cell = grid_.getCells().get(i).get(j);
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