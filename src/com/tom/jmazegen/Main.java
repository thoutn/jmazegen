package com.tom.jmazegen;

import com.tom.jmazegen.algos.BinaryTreeMazeBuilder;
import com.tom.jmazegen.maze.Grid;
import com.tom.jmazegen.presenter.MazePresenter;

public class Main {

    public static void main(String[] args) {
        MazePresenter presenter;
        Grid grid = new Grid(20, 20);

        BinaryTreeMazeBuilder algo_ = new BinaryTreeMazeBuilder(grid);
        algo_.buildMaze();

        presenter = new MazePresenter(grid);
        presenter.showImage();

    }
}
