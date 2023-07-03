package sudoku
import zio._
import zio.json._
import scala.io.Source
import java.io.{File, FileWriter}


object Main extends ZIOAppDefault {

  implicit val sudokuGridDecoder: JsonDecoder[SudokuGrid] =
    DeriveJsonDecoder.gen[SudokuGrid]

  implicit val sudokuGridEncoder: JsonEncoder[SudokuGrid] =
    DeriveJsonEncoder.gen[SudokuGrid]

  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- Console.print("Enter the path to the JSON file containing the Sudoku problem:")
      path <- Console.readLine
      // Step 1 : Deserialization --> Read & Parse Json file
      sudokuGrid = readAndParseJson(path)

      // Step 2 : Check grid validity
      _ <- sudokuGrid.flatMap(grid =>
          if (grid.isValid) ZIO.unit
          else ZIO.fail(new Throwable("Invalid Sudoku Grid"))
      )

      // Step 3 : Display
      _ <- Console.printLine("Sudoku Grid to solve:")
      _ <- sudokuGrid.flatMap(grid => Console.print(grid))

      // Step 4 : Sudoku Solver
      solvedGridOption <- sudokuGrid.flatMap(grid => ZIO.fromOption(solveSudoku(grid)))
          .fold(_ => None, Some(_))
      solvedGrid <- ZIO.fromOption(solvedGridOption).orElseFail(new Throwable("No solution found"))
      _ <- Console.printLine("Solved Sudoku Grid:")
      _ <- Console.printLine(solvedGrid)

      // Step 5 : Serialization --> Serialize 
      _ <- Console.printLine("Serialized Sudoku Grid:")
      json = solvedGrid.toJsonPretty
      // Step 6 : Save to JSON file
      _ <- ZIO.attempt {
        val file = new File(path)
        val directory = file.getParent
        val originalFileName = file.getName.stripSuffix(".json")
        val modifiedFileName = s"${originalFileName}_solved.json"
        val modifiedPath = new File(directory, modifiedFileName).getPath
        val writer = new FileWriter(modifiedPath)
        writer.write(json)
        writer.close()
      }.catchAll(error => Console.printLine(s"Failed to save JSON file: $error"))

    } yield ()

  /**
    * Use the Source method to read a file from the path
    * 
    * @param path
    * @return String
    */
  def readFile(path: String): String =
    Source.fromFile(path).getLines.mkString
  

  /**
    * Check if the grid is validing according to 2 constraints :
      Cells is an array of 9 arrays
      Each of these 9 arrays is also an array of 9
    * 
    * @param jsonEither
    * @return `true` if the grid is valid, `false` otherwise
    */
  def verifyJson(jsonEither: Either[String, SudokuGrid]): Boolean =
    jsonEither match {
      case Right(sudokuGrid) =>
        sudokuGrid.cells.length == 9 && sudokuGrid.cells.forall(_.length == 9)
      case _ => false
    }

  /**
    * Use the readFile method to read the file and then parse it in order to deserialized it
    *
    * @param path
    * @return IO[Throwable, SudokuGrid]
    */
  def readAndParseJson(path: String): IO[Throwable, SudokuGrid] =
    val jsonFile = readFile(path)
    val grid: Either[String, SudokuGrid] = jsonFile.fromJson[SudokuGrid]
     verifyJson(grid) match {
      case false => ZIO.fail(new Throwable("Structure Error"))
      case true  => ZIO.fromEither(grid).mapError(e => new Throwable(e))
    }
  }

