package com.tom.jmazegen.maze;

import java.util.ArrayList;


/**
 * Modified Cell class for usage in the CircGrid (mazes represented in polar coordinates).
 */
public class CircCell extends Cell {
    ArrayList<Cell> bottom = new ArrayList<>();

    /**
     * Constructor method calling super().
     * @param row the row ID of the cell
     * @param column the column ID of the cell
     */
    public CircCell(int row, int column) {
        super(row, column);
    }

    /**
     * Almost identical to the superclass implementation, except of the way the bottom neighbours are added
     * to the list of all neighbours.
     * @return list of all the cell's neighbours.
     */
    @Override
    public ArrayList<Cell> neighbours() {
        ArrayList<Cell> lst = new ArrayList<>();

        if (top != null) {
            lst.add(top);
        }
        if (!bottom.isEmpty()) {
            lst.addAll(bottom);
        }
        if (right != null) {
            lst.add(right);
        }
        if (left != null) {
            lst.add(left);
        }

        return lst;
    }
}
