# Sudoku Solver 

Sudoku Solver is a program that can solve Sudoku. It takes a Sudoku grid as input, thanks to a json file, and finds a solution if one exists. The program is implemented in Scala using the ZIO library.

## Authors
- Elvin AUPIAIS--BERTHY
- Monica-Pauline BLANC
- Hugo JAMINON

## Getting Started

1. Clone the repository: `git clone https://github.com/NovaGamma/ScalaSudoku.git`
2. Navigate to the project directory : `cd ScalaSudoku`
3. Build the project: `sbt compile`

## Usage

1. Prepare a JSON file containing the Sudoku problem in the following format :

```json
{
    "cells": [
        [5, 3, 0, 0, 7, 0, 0, 0, 0],
        [6, 0, 0, 1, 9, 5, 0, 0, 0],
        [0, 9, 8, 0, 0, 0, 0, 6, 0],
        [8, 0, 0, 0, 6, 0, 0, 0, 3],
        [4, 0, 0, 8, 0, 3, 0, 0, 1],
        [7, 0, 0, 0, 2, 0, 0, 0, 6],
        [0, 6, 0, 0, 0, 0, 2, 8, 0],
        [0, 0, 0, 4, 1, 9, 0, 0, 5],
        [0, 0, 0, 0, 8, 0, 0, 7, 9]
    ]
}
```
Note: Use 0 to represent empty cells.
You can also use one of our existing grid in: `GridExamples/`

2. Run the program: `sbt run`
3. Enter the path to the JSON file containing the Sudoku grid when prompted.
4. The program will read, verify the grid structure and parse the JSON and then solve the grid.
5. The solution will be displayed in the console and saved to a JSON file.

## Implementation Details
The progam is structured as follows :
- `Main.scala`: The entry point of the program. It handles the execution, including reading and parsing the JSON file, solving the Sudoky and serializing the solution.
- `SudokuGrid.scala`: Defines the `SudokuGrid` case class, which represents a Sudoku grid. It also provides methods to check its validity and to generate a string representation.
- `SudokuSolver.scala`: Contains the `solveSudoku` function, which solves the Sudoku grid using a backtracking algorithm. It utilizes helper functions to calculate valid numbers for each cell and recursively solve the grid.
- `MySuite.scala`: Contains the tests to check if our implementation is correct. You can run them using: `sbt test`

The program follows these steps :
1. Read and Parse JSON: The program prompts the user to enter the path to a JSON file, it verify its structure and parse it into a SudokuGrid object.
2. Verify Grid Validity: The program verifies that the Sudoku grid follows the rules of Sudoku by checking the range of cell values and ensuring that each row, column, and sub-grid contains distinct values.
3. Solve Sudoku: The program calls the solveSudoku function, which use the backtracking algorithm to solve the grid.
4. Serialize Solution: Once a solution is found, the program serializes the solved grid to a JSON file.
5. Display Solution: The program displays the solution in the console by generating a formatted string representation of the Sudoku grid.

### Project compiled with Scala 3
