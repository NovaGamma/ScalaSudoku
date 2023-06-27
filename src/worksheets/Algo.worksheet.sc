case class SudokuGrid(cells: Vector[Vector[Int]])

val initialGrid = SudokuGrid(
    Vector(
        Vector(5,3,0,0,7,0,0,0,0),
        Vector(6,0,0,1,9,5,0,0,0),
        Vector(0,9,8,0,0,0,0,6,0),
        Vector(8,0,0,0,6,0,0,0,3),
        Vector(4,0,0,8,0,3,0,0,1),
        Vector(7,0,0,0,2,0,0,0,6),
        Vector(0,6,0,0,0,0,2,8,0),
        Vector(0,0,0,4,1,9,0,0,5),
        Vector(0,0,0,0,8,0,0,7,9)
    )
)

val vector = Vector(5,3,0,0,7,0,0,0,0)
vector(0)
initialGrid.cells.map(_ (0))

val currentCellRow: Int = 1
val currentCellCol: Int = 1
val subTopLeftRow = 3 * (currentCellRow / 3)
val subTopLeftCol = 3 * (currentCellCol / 3)
initialGrid.cells.slice(subTopLeftRow, subTopLeftRow + 3).flatMap(row => row.slice(subTopLeftCol, subTopLeftCol + 3))

def isValid(grid: SudokuGrid, row: Int, col: Int, num: Int): Boolean = {
    val subTopLeftRow = 3 * (row / 3)
    val subTopLeftCol = 3 * (col / 3)

    !grid.cells(row).contains(num) &&
    !grid.cells.map(_ (col)).contains(num) &&
    !grid.cells.slice(subTopLeftRow, subTopLeftRow + 3)
        .flatMap(row => row.slice(subTopLeftCol, subTopLeftCol + 3))
        .contains(num)
}

Range(1, 9).filter(isValid(initialGrid, 0, 2, _))

def getStates(grid: SudokuGrid, row: Int, col: Int): Vector[Int] = {
    val start = Vector(1,2,3,4,5,6,7,8,9)
    val line = grid.cells(row)
    val column = grid.cells.map(_ (col))
    val squareRow = 3 * (row / 3)
    val squareCol = 3 * (col / 3)
    val square = grid.cells.slice(squareRow, squareRow + 3)
        .flatMap(arr => arr.slice(squareCol, squareCol + 3))

    start.filterNot(value => line.contains(value))
        .filterNot(value => column.contains(value))
        .filterNot(value => square.contains(value))
  }

getStates(initialGrid, 0, 2)
initialGrid.cells.updated(0, initialGrid.cells(0).updated(0, 1))


def solveSudoku(grid: SudokuGrid): Option[SudokuGrid] = {
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

    def nextRow(row: Int, col: Int): Int =
        if (col == 8) row + 1 else row

    def nextCol(col: Int): Int =
        (col + 1) % 9

    solveHelper(grid, 0, 0)
}

solveSudoku(initialGrid)