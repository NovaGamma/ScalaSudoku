package sudoku

/**
  * Represents a Sudoku grid.
  *
  * @param cells A 2-dimensional vector representing the Sudoku grid cells.
  *              Each inner vector represents a row, and each int value represents the cell value (0 for empty).
  *              The outer vector represents the entire grid. 
  */
case class SudokuGrid(cells: Vector[Vector[Int]])