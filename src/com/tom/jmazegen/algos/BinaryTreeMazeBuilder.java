package com.tom.jmazegen.algos;

import com.tom.jmazegen.maze.Cell;
import com.tom.jmazegen.maze.Grid;

import java.util.ArrayList;
import java.util.Random;

public class BinaryTreeMazeBuilder extends MazeBuilder {
    private final ArrayList<Cell> neighbours = new ArrayList<>();
    private final Random rand = new Random();

    public BinaryTreeMazeBuilder() {
        this.grid = new Grid(20, 20);
    }

    public BinaryTreeMazeBuilder(Grid grid) {
        this.grid = grid;
    }

    /**
     * Helper function that randomly chooses a neighbour of the cell.
     * @param cell the cell, which neighbours are chosen from
     * @return the selected neighbour or null (if no bottom or right neighbour is available).
     */
    private Cell chooseNeighbourOf(Cell cell) {
        if (cell.bottom != null) {
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

    @Override
    public void buildMaze() {
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
