package com.tom.jmazegen.maze;

import java.util.ArrayList;

/**
 * Modified Grid class used to represent mazes in polar coordinates.
 */
public class CircGrid extends Grid {
    private int size_ = 0;

    /**
     * Constructor method calling super().
     * @param height the height (radius) of the circular maze
     */
    public CircGrid(int height) {
        super(1, height);
    }

    /**
     * Creates a matrix for storing the individual cells.
     * The matrix size is computed during construction, such to obtain a cell <em>width</em>
     * similar in dimension to its <em>height</em>.
     */
    private void prepareGrid() {
        cells.add(new ArrayList<>(width));
        cells.get(0).add(new CircCell(0, 0));
        size_ = 1;

        for (int row_ = 1; row_ < height; row_++) {
            cells.add(new ArrayList<>());

            int previous_count = cells.get(row_ - 1).size();
            int ratio = (int) Math.round((row_ * 2 * Math.PI) / previous_count);
            int cell_count = previous_count * ratio;

            for (int col_ = 0; col_ < cell_count; col_++) {
                cells.get(row_).add(new CircCell(row_, col_));
            }

            size_ += cell_count;
        }
    }

    /**
     * Modified version of the same method of class Cell.
     * It enables to connect multiple bottom cells to each top cell.
     */
    private void configureCells() {
        for (ArrayList<Cell> row : cells) {
            for (Cell cell : row) {
                int row_ = cell.row;
                int col_ = cell.column;

                if (row_ > 0) {
                    if (col_ != 0) {
                        cell.left = cells.get(row_).get(col_ - 1);
                    } else {
                        cell.left = cells.get(row_).get(cells.get(row_).size() - 1);
                    }

                    if (col_ != row.size() - 1) {
                        cell.right = cells.get(row_).get(col_ + 1);
                    } else {
                        cell.right = cells.get(row_).get(0);
                    }

                    int ratio = cells.get(row_).size() / cells.get(row_ - 1).size();
                    CircCell parent = (CircCell) cells.get(row_ - 1).get(col_ / ratio);
                    cell.top = parent;
                    parent.bottom.add(cell);
                }
            }
        }
    }

//    public Cell getRandomCell() {
//        int row_ = rand.nextInt(0, height);
//        int col_ = rand.nextInt(0, cells.get(row_).size());
//        return cells.get(row_).get(col_);
//    }

    @Override
    public int getSize() {
        return size_;
    }
}
