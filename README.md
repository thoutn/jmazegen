# jmazegen

A maze generator written in Java 17. The package implements several algorithms
to generate rectangular or circular mazes. It also includes a feature to visualise
the generated maze.

The older version of this package with som optimisation / experimentation is
[here](https://github.com/thoutn/archive_jmazegen). 

The Python version of this package is in the 
[pymazegen](https://github.com/thoutn/pymazegen) repo. 

## Folder structure

The project follows a standard folder structure:
- `exemples/` contains a file with examples on how to use the package.
- `src/` contains the source code, the main package.
  The `jmazegen` package has several subpackages:
    - `algos/` contains all the implemented maze generation algorithms.
      Each algorithm is contained in its own file.
    - `maze/` contains the classes that are used to represent the maze - `Cell` and `Grid`
      for generating rectangular mazes, and `CirCell` and `CircGrid` for circular mazes
      represented in polar coordinates.
    - `presenter/` contains the main methods to visualise the generated mazes.

## How to install and run the project

*TBW*

## How to use the project

*TBW*

## License

[MIT License](LICENSE)