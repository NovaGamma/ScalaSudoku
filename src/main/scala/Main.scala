package sudoku
import zio._
import zio.json._
import scala.io.Source
import scala.util.{Success,Failure}


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

      // Step 2 : Verification of the Json structure
      
      // Step 3 : Display
      
      // Step 4 : Sudoku Solver

      // Step 5 : Serialization --> Serialize 
    } yield ()

  def readFile(path: String): String =
    Source.fromFile(path).getLines.mkString
  

  def verifyJson(jsonEither: Either[String, SudokuGrid]): Boolean =
    jsonEither match {
      case Right(sudokuGrid) =>
        sudokuGrid.cells.length == 9 && sudokuGrid.cells.forall(_.length == 9)
      case _ => false
    }

  def readAndParseJson(path: String): IO[String, SudokuGrid] =
    val jsonFile = readFile(path)
    val grid: Either[String, SudokuGrid] = jsonFile.fromJson[SudokuGrid]
/*   val isValidJson = verifyJson(grid) match {
      case Failure(e) => println("Error Structure")
      case Success(grid) =>  ZIO.fromEither(grid)
     }  */ 

     verifyJson(grid) match {
      case false => ZIO.fail("Error Structure")
      case true  => ZIO.fromEither(grid)
    }
  }

