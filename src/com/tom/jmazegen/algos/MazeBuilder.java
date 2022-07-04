package com.tom.jmazegen.algos;

import com.tom.jmazegen.maze.Grid;

public abstract class MazeBuilder {
    Grid grid;

    void setGrid(Grid grid) {
        this.grid = grid;
    }

    abstract void buildMaze();
}
