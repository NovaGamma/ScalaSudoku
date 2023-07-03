# Sudoku Solver 

Sudoku Solver is a program that can solve Sudoku. It takes a Sudoku grid as an input, thanks to a json file, and finds a solution if one exists. The program is implemented in Scala using the ZIO library.

## Authors
- Elvin AUPIAIS--BERTHY
- Monica-Pauline BLANC
- Hugo JAMINON

## Getting Started

1. Clone the repository: 
```
git clone https://github.com/NovaGamma/ScalaSudoku.git
```
2. Navigate to the project directory 
```
cd ScalaSudoku
```
3. Build the project
```
sbt compile
```

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

2. Run the program
```
sbt run
```
3. Enter the path to the JSON file containing the Sudoku grid when prompted.
4. The program will read, verify the grid structure and parse the JSON and then solve the grid.
5. The solution will be displayed in the console and saved to a JSON file.

## Implementation Details
The progam is structured as follows :
```
src                          
├─ main                      
│  └─ scala                  
│     ├─ Main.scala          
│     ├─ SudokuGrid.scala    
│     └─ SudokuSolver.scala  
├─ test                      
│  └─ scala                  
│     └─ MySuite.scala       
└─ worksheets                
   └─ Algo.worksheet.sc      
```

- `Main.scala`: The entry point of the program. It handles the execution, including reading and parsing the JSON file, solving the Sudoky and serializing the solution.
- `SudokuGrid.scala`: Defines the `SudokuGrid` case class, which represents a Sudoku grid. It also provides methods to check its validity and to generate a string representation.
- `SudokuSolver.scala`: Contains the `solveSudoku` function, which solves the Sudoku grid using a backtracking algorithm. It utilizes helper functions to calculate valid numbers for each cell and recursively solve the grid.
- `MySuite.scala`: Contains the tests to check if our implementation is correct. 
    * Check `isValid` function
    * Check `sudokuSolver` function
    * Check `JsonParser`


You can run them using: 
```
sbt test
```

The program follows these steps :
1. Read and Parse JSON: The program prompts the user to enter the path to a JSON file, it verify its structure and parse it into a SudokuGrid object.
2. Verify Grid Validity: The program verifies that the Sudoku grid follows the rules of Sudoku by checking the range of cell values and ensuring that each row, column, and sub-grid contains distinct values.
3. Solve Sudoku: The program calls the solveSudoku function, which use the backtracking algorithm to solve the grid.
4. Serialize Solution: Once a solution is found, the program serializes the solved grid to a JSON file.
5. Display Solution: The program displays the solution in the console by generating a formatted string representation of the Sudoku grid.
6. Save Json file: The programm will save the new solved grid into the one we wanted to be solved changing the name of the path (it adds _solved.json to the original file)

## Explanation of our imports
We use the entire ZIO library as it was suggested. In this library, we use :
- `zio-json` which is a powerful library for working with JSON in Scala, providing capabilities for both decoding and encoding JSON data (It was useful for the serialization and deserializaiton part).

To handle with file IO operations in Scala, we had to research how to read(unsolved grid) and how to write (solved grid):

1. Reading a file

Scala provides a native way to handle reading from a file : 
- `scala.io.Source` : It is a method that returns a BufferedSource, and its getLines method returns an Iterator that treats any of \r\n, \r, or \n as a line separator, so each element in the sequence is a line from the file. In our case we use mkString method in order to display all in a String.

2. Writing to a file

Unlike reading file, Scala doesn’t have any native writing capability, so writing to a file exclusively involves using Java classes :
- `java.io.{File, FileWriter}`: which are method that we use in the save part when we solve our grid. `File` will create a new file instance with the same path we put at the beginning and `FileWriter` will write in it.

### Project compiled with Scala 3.3.0

