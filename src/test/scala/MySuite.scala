package sudoku

import zio._
import zio.json._
import sudoku.Main.verifyJson
// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {

  test("test of SudokuGrid valid function") {
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
  }

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

    val grid3: Either[String, SudokuGrid] = """{
      "cell": [
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

    val grid4: Either[String, SudokuGrid] = """{
      "cell": [
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
