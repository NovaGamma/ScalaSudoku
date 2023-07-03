package sudoku

import zio._
import zio.json._
import scala.io.Source
import zio.nio.file.Path
import zio.json.codegen.JsonType.JArray


object Main extends ZIOAppDefault {

  implicit val sudokuGridDecoder: JsonDecoder[SudokuGrid] =
    DeriveJsonDecoder.gen[SudokuGrid]

  def run: ZIO[Any, Throwable, Unit] =
    //
    for {
      _ <- Console.print("Enter the path to the JSON file containing the Sudoku problem:")
      path <- Console.readLine
      // Step 1 : Deserialization --> Read & Parse Json file
      sudokuGrid = readAndParseJson(path)
      _ <- Console.printLine(s"Successfully read & parsed JSON: $sudokuGrid")
      
      // Step 2 : Sudoku Solver

      // Step 3 : Serialization --> Serialize and Verification of the structure
    } yield ()

  def readFile(path: String): String =
    Source.fromFile(path).getLines.mkString

  def readAndParseJson(path: String): IO[String, SudokuGrid] =
    val jsonFile = readFile(path)
    val grid: Either[String, SudokuGrid] = jsonFile.fromJson[SudokuGrid]
    ZIO.fromEither(grid)
  
}
