package sudoku

import zio._
import zio.json._
import sudoku.Main.verifyJson
// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {

  test("test of SudokuGrid valid function") {

    /**
      * Expecting : true
      */
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

    assert(initialGrid.isValid)

    /**
      * Expecting : False
      * In the same subgrid (bottom left), we have two 9
      */
    val secondGrid = SudokuGrid(
      Vector(
          Vector(5,3,0,0,7,0,0,0,0),
          Vector(6,0,0,1,9,5,0,0,0),
          Vector(0,9,8,0,0,0,0,6,0),
          Vector(8,0,0,0,6,0,0,0,3),
          Vector(4,0,0,8,0,3,0,0,1),
          Vector(7,0,0,0,2,0,0,0,6),
          Vector(0,6,0,0,0,0,2,8,0),
          Vector(0,0,0,4,1,9,0,0,5),
          Vector(0,0,0,0,8,0,9,7,9)
      )
    )

    assert(!secondGrid.isValid)

    /**
      * Expecting : False
      * Sudoku number are between 1 and 9
      */
    val thirdGrid = SudokuGrid(
      Vector(
          Vector(5,12,0,0,7,0,0,0,0),
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

    assert(!thirdGrid.isValid)
  }

  /**
    * Expecting : Same grid as the expected one
    * Compare a grid with its solved grid
    */
  test("test of the sudoku solver") {
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

    val expectedGrid = SudokuGrid(
      Vector(
        Vector(5,3,4,6,7,8,9,1,2), 
        Vector(6,7,2,1,9,5,3,4,8), 
        Vector(1,9,8,3,4,2,5,6,7), 
        Vector(8,5,9,7,6,1,4,2,3), 
        Vector(4,2,6,8,5,3,7,9,1), 
        Vector(7,1,3,9,2,4,8,5,6), 
        Vector(9,6,1,5,3,7,2,8,4), 
        Vector(2,8,7,4,1,9,6,3,5), 
        Vector(3,4,5,2,8,6,1,7,9)
      )
    )

    assertEquals(solveSudoku(initialGrid), Option(expectedGrid))
  }

  test("test of json parser") {

    implicit val decoder: JsonDecoder[SudokuGrid] = DeriveJsonDecoder.gen[SudokuGrid]

    /**
      * Expected : True
      * Good Structure
      */
    val grid: Either[String, SudokuGrid] = """{
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
  }""".fromJson[SudokuGrid]

    /**
      * Expected : False
      * Cell and not Cells
      */
    val grid2: Either[String, SudokuGrid] = """{
    "cell": [
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
  }""".fromJson[SudokuGrid]

  /**
    * Expected : False 
    * 8 arrays instead of 9
    */
    val grid3: Either[String, SudokuGrid] = """{
      "cells": [
        [5, 3, 0, 0, 7, 0, 0, 0, 0],
        [6, 0, 0, 1, 9, 5, 0, 0, 0],
        [0, 9, 8, 0, 0, 0, 0, 6, 0],
        [8, 0, 0, 0, 6, 0, 0, 0, 3],
        [4, 0, 0, 8, 0, 3, 0, 0, 1],
        [7, 0, 0, 0, 2, 0, 0, 0, 6],
        [0, 6, 0, 0, 0, 0, 2, 8, 0],
        [0, 0, 0, 4, 1, 9, 0, 0, 5]
      ]
    }""".fromJson[SudokuGrid]

    /**
      * Expected : False 
      * Line 1 is not filled by 9 values
      */
    val grid4: Either[String, SudokuGrid] = """{
      "cells": [
        [5, 0, 0, 7, 0, 0, 0, 0],
        [6, 0, 0, 1, 9, 5, 0, 0, 0],
        [0, 9, 8, 0, 0, 0, 0, 6, 0],
        [8, 0, 0, 0, 6, 0, 0, 0, 3],
        [4, 0, 0, 8, 0, 3, 0, 0, 1],
        [7, 0, 0, 0, 2, 0, 0, 0, 6],
        [0, 6, 0, 0, 0, 0, 2, 8, 0],
        [0, 0, 0, 4, 1, 9, 0, 0, 5]
      ]
    }""".fromJson[SudokuGrid]

    assert(verifyJson(grid))
    assert(!verifyJson(grid2))
    assert(!verifyJson(grid3))
    assert(!verifyJson(grid4))
  }
}
