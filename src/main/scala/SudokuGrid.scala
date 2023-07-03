package sudoku

/**
  * Represents a Sudoku grid.
  *
  * @param cells A 2-dimensional vector representing the Sudoku grid cells.
  *              Each inner vector represents a row, and each int value represents the cell value (0 for empty).
  *              The outer vector represents the entire grid. 
  */

case class SudokuGrid(cells: Vector[Vector[Int]])
{

    /**
     * Checks if the Sudoku grid is valid according to the rules of Sudoku.
     *
     * @return `true` if the grid is valid, `false` otherwise.
     */
    def isValid: Boolean = {
        val validRange = cells.flatten.forall(value => value >= 0 && value <= 9)

        val validRows = cells.forall(row => row.filter(_ != 0).distinct.length == row.filter(_ != 0).length)

        val validColumns = cells.transpose.forall(column => column.filter(_ != 0).distinct.length == column.filter(_ != 0).length)

        val validSubGrids = {
            val subGrids = cells.grouped(3).flatMap(_.transpose.grouped(3))
            subGrids.forall(subGrid =>
            subGrid.flatten.filter(_ != 0).distinct.length == subGrid.flatten.filter(_ != 0).length)
        }

        validRange && validRows && validColumns && validSubGrids
    }

    /**
      * Returns a string representation of the Sudoku grid.
      * The grid is formatted with separators for lines and sub-grids.
      * Empty cells (with value 0) are represented as spaces.
      *
      * @return A string representing the Sudoku grid.
      */
    override def toString: String = {
        val separator = "+-------+-------+-------+\n"
        cells.zipWithIndex.map { case (row, rowIndex) =>
            val formattedRow = row.map(cellValue => if (cellValue == 0) " " else cellValue.toString).grouped(3).map(_.mkString(" ")).mkString(" | ")
            val rowSeparator = if (rowIndex % 3 == 2) separator else ""
            if (rowIndex == 0) s"$separator| $formattedRow |\n" else s"| $formattedRow |\n$rowSeparator"
        }.grouped(3).map(_.mkString).mkString
    }
}
