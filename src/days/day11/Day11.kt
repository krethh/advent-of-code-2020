package days.day11

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

object Day11 {
    @JvmStatic
    @Throws(IOException::class)
    fun solve(input: Path?) {
        var matrix = Matrix(Files.readAllLines(input))

        while (true) {
            val newMatrix = transformOne(matrix)

            if (!newMatrix.equals(matrix)) {
                matrix = newMatrix
                continue
            } else {
                val occupiedSeats = newMatrix.rows.map { it.chunked(1) }.flatten().filter { it == "#" }.size
                println (occupiedSeats)
                break
            }
        }

        matrix = Matrix(Files.readAllLines(input))

        while (true) {
            val newMatrix = transformTwo(matrix)

            if (!newMatrix.equals(matrix)) {
                matrix = newMatrix
                continue
            } else {
                val occupiedSeats = newMatrix.rows.map { it.chunked(1) }.flatten().filter { it == "#" }.size
                println (occupiedSeats)
                break
            }
        }

    }

    fun transformTwo(matrix: Matrix): Matrix {
        var newLines = mutableListOf<String>()
        for (row in matrix.rows.indices) {
            var newLine = ""
            for (column in matrix.rows[row].indices) {
                val seat = matrix.getIndex(row, column)

                if (seat == ".") {
                    newLine += "."
                    continue
                }

                val adjacents = matrix.getFirstVisibles(row, column)

                if (seat == "L") {
                    if (adjacents.filter { it == "#" }.isEmpty()) {
                        newLine += "#"
                        continue
                    }
                }
                if (seat == "#") {
                    if (adjacents.filter { it == "#" }.size > 4) {
                        newLine += "L"
                        continue
                    }
                }
                newLine += seat
            }
            newLines.add(newLine)
        }
        return Matrix(newLines)
    }

    fun transformOne(matrix: Matrix): Matrix {
        var newLines = mutableListOf<String>()
        for (row in matrix.rows.indices) {
            var newLine = ""
            for (column in matrix.rows[row].indices) {
                val seat = matrix.getIndex(row, column)

                if (seat == ".") {
                    newLine += "."
                    continue
                }

                val adjacents = matrix.getAdjacents(row, column)

                if (seat == "L") {
                    if (adjacents.filter { it == "#" }.isEmpty()) {
                        newLine += "#"
                        continue
                    }
                }
                if (seat == "#") {
                    if (adjacents.filter { it == "#" }.size > 3) {
                        newLine += "L"
                        continue
                    }
                }
                newLine += seat
            }
            newLines.add(newLine)
        }
        return Matrix(newLines)
    }

}