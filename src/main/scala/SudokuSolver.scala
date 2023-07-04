package sudoku

/**
 * Solves a Sudoku grid.
 *
 * @param grid The Sudoku grid to solve.
 * @return An option containing the solved Sudoku grid if a solution exists, or None if no solution is found.
 */
def solveSudoku(grid: SudokuGrid): Option[SudokuGrid] = {

    /**
      * Gets the valid numbers that can be placed in a cell.
      *
      * @param grid The Sudoku grid.
      * @param row The row index of the cell.
      * @param col The column index of the cell.
      * @return The valid numbers that can be placed in a cell.
      */
    def getValidNumbers(grid: SudokuGrid, row: Int, col: Int): IndexedSeq[Int] = {
        val availableNumbers = (1 to 9)
        val line = grid.cells(row)
        val column = grid.cells.map(_ (col))
        val squareRow = 3 * (row / 3)
        val squareCol = 3 * (col / 3)
        val square = grid.cells.slice(squareRow, squareRow + 3)
            .flatMap(arr => arr.slice(squareCol, squareCol + 3))

        availableNumbers.filterNot(value => line.contains(value))
            .filterNot(value => column.contains(value))
            .filterNot(value => square.contains(value))
    }

    /**
      * Recursive helper function to solve the grid.
      *
      * @param grid The Sudoku grid.
      * @param row The current row index.
      * @param col The current column index.
      * @return An option containing the solved grid if a solution is found, or None otherwise.
      */
    def solveHelper(grid: SudokuGrid, row: Int, col: Int): Option[SudokuGrid] = {
        if (row == 9)
            Some(grid) // All cells have been filled, grid is solved
        else if (grid.cells(row)(col) != 0)
            solveHelper(grid, nextRow(row, col), nextCol(col)) // Move to the next cell
        else
            getValidNumbers(grid, row, col)
                .flatMap(value => solveHelper(SudokuGrid(grid.cells.updated(row, grid.cells(row).updated(col, value))), nextRow(row, col), nextCol(col)))
                .headOption
    }

    /**
      * Calculates the next row index.
      *
      * @param row The current row index.
      * @param col The current column index.
      * @return The next row index.
      */
    def nextRow(row: Int, col: Int): Int =
        if (col == 8) row + 1 else row

    /**
      * Calculates the next column index.
      *
      * @param col The current column index.
      * @return The next column index.
      */
    def nextCol(col: Int): Int =
        (col + 1) % 9

    solveHelper(grid, 0, 0)
}