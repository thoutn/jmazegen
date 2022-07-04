package com.tom.jmazegen.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * The Cell class used to represent a cell of the maze.
 */
public class Cell {
    int row;
    int column;

    HashMap<Cell, Boolean> links = new HashMap<>();

    Cell top = null;
    Cell bottom = null;
    Cell right = null;
    Cell left = null;

    /**
     * Constructor method of class Cell.
     * @param row the row ID of the cell
     * @param column the column ID of the cell
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Creates a link between the current and specified cell given as a parameter.
     * @param cell the neighbour to link the cell to
     */
    public void linkTo(Cell cell) {
        this.linkTo(cell, true);
    }

    protected void linkTo(Cell cell, boolean bidirect) {
        links.put(cell, true);
        if (bidirect) {
            cell.linkTo(this, false);
        }
    }

    /**
     * Check if it is linked to any cell in the maze.
     * @return set of neighbours the cell is linked to
     */
    public Set<Cell> hasLinkedCells() {
        return links.keySet();
    }

    /**
     * Checks if it is linked to the specified cell given as a parameter.
     * @param cell a neighbour of the cell
     * @return  'True' if neighbour is linked to the cell and
     *          'False' if they are not linked.
     */
    public boolean isLinkedTo(Cell cell) {
        return links.containsKey(cell);
    }

    /**
     * Returns a list of all adjacent neighbours to cell.
     * @return list of all the cell's neighbours.
     */
    public ArrayList<Cell> neighbours() {
        ArrayList<Cell> lst = new ArrayList<>();

        if (top != null) {
            lst.add(top);
        }
        if (bottom != null) {
            lst.add(bottom);
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
