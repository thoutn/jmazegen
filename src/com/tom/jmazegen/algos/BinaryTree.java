package com.tom.jmazegen.algos;

import com.tom.jmazegen.maze.Cell;
import com.tom.jmazegen.maze.CircCell;
import com.tom.jmazegen.maze.CircGrid;
import com.tom.jmazegen.maze.Grid;

import java.util.ArrayList;
import java.util.Random;

public final class BinaryTree {
    static Grid grid;
    private static final ArrayList<Cell> neighbours = new ArrayList<>();
    private static final ArrayList<Cell> neigh_ = new ArrayList<>();
    private static final Random rand = new Random();

    /**
     * Don't let anyone instantiate this class.
     */
    private BinaryTree() {}

    /**
     * Helper function that randomly chooses a neighbour of the cell.
     * @param cell the cell, which neighbours are chosen from
     * @return the selected neighbour or null (if no bottom or right neighbour is available).
     */
    private static Cell chooseNeighbourOf(Cell cell) {
        if (grid instanceof CircGrid) {
            CircCell c_ = (CircCell) cell;
            neighbours.addAll(c_.bottom);
        } else if (cell.bottom != null) {
            neighbours.add(cell.bottom);
        }
        if (cell.right != null) {
            neighbours.add(cell.right);
        }

        if (!neighbours.isEmpty()) {
            Cell neighbour = neighbours.get(rand.nextInt(neighbours.size()));
            neighbours.clear();
            return neighbour;
        } else {
            return null;
        }
    }

    public static void buildMaze(Grid grid_) {
        grid = grid_;
        for (ArrayList<Cell> row : grid.getCells()) {
            for (Cell cell : row) {
                Cell neighbour = chooseNeighbourOf(cell);

                if (neighbour != null) {
                    cell.linkTo(neighbour);
                }
            }
        }
    }

}
