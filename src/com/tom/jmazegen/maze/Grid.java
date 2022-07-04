package com.tom.jmazegen.maze;

import java.util.ArrayList;
import java.util.Random;


/**
 * The Grid class used to represent the maze.
 */
public class Grid {
    final int width;
    final int height;

    ArrayList<ArrayList<Cell>> cells;

    protected final Random rand = new Random();

    /**
     * Constructor method of class Grid.
     * @param width width of the maze
     * @param height height of the maze
     */
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        this.cells = new ArrayList<>(height);

        prepareGrid();
        configureGrid();
    }

    /**
     * Creates a matrix for storing the individual cells. The matrix created is of size <em>width</em>×<em>height</em>.
     */
    private void prepareGrid() {
        for (int row_ = 0; row_ < height; row_++) {
            cells.add(new ArrayList<>(width));
            for (int col_ = 0; col_ < width; col_++) {
                cells.get(row_).add(new Cell(row_, col_));
            }
        }
    }

    /**
     *  Configures the maze
     *  <ul>
     *      <li>links the cells to their neighbours</li>
     *      <li>defines the maze boundaries</li>
     *  </ul>
     */
    private void configureGrid() {
        for (ArrayList<Cell> row : cells) {
            for (Cell cell : row) {
                int row_ = cell.row;
                int col_ = cell.column;

                cell.top = createNeighbours(row_ - 1, col_);
                cell.bottom = createNeighbours(row_ + 1, col_);
                cell.right = createNeighbours(row_, col_ + 1);
                cell.left = createNeighbours(row_, col_ - 1);
            }
        }
    }

    /**
     * Helper function for method <em>configureGrid()</em>.
     * @param row row ID of the neighbour
     * @param col column ID of the neighbour
     * @return the neighbour at <em>row</em> and <em>col</em> or <em>null</em> if it's outside the maze boundary.
     */
    protected Cell createNeighbours(int row, int col) {
        if ((0 <= row && row <= width - 1) && (0 <= col && col <= height - 1)) {
            return cells.get(row).get(col);
        } else {
            return null;
        }
    }

    /**
     * Getter for a random cell.
     * @return a random cell of the maze.
     */
    public Cell getRandomCell() {
        int row_ = rand.nextInt(cells.size());
        int col_ = rand.nextInt(cells.get(row_).size());
        return cells.get(row_).get(col_);
    }

    /**
     * Getter for maze size.
     * @return the size (number of cells) of the maze.
     */
    public int getSize() {
        return width * height;
    }
}
