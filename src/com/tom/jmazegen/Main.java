package com.tom.jmazegen;

import com.tom.jmazegen.algos.BinaryTree;
import com.tom.jmazegen.maze.Grid;
import com.tom.jmazegen.presenter.Presenter;

public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid(10);

        BinaryTree.buildMaze(grid);

        Presenter.configImg(grid,20, 2, "BTree");
        Presenter.showImage();
        Presenter.saveImage("mazos.png");
//        Presenter.printToConsole();

    }
}
