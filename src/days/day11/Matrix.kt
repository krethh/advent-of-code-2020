package days.day11

import kotlin.math.max

class Matrix(lines: List<String>) {

    var rows: List<String> = lines

    fun getRowSize(): Int = rows.size
    fun getColumnSize(): Int = rows[0].length

    fun isNotFloor(row: Int, column: Int): Boolean {
        return this.hasIndex(row, column) && getIndex(row, column) != "."
    }

    fun equals(matrix: Matrix): Boolean {
        for (i in rows.indices) {
            for (j in rows[i].indices) {
                if (this.getIndex(i, j) != matrix.getIndex(i, j)) {
                    return false
                }
            }
        }
        return true
    }

    fun getIndex(row: Int, column: Int): String? {
        if (!this.hasIndex(row, column)) {
            return null
        }

        return rows[row][column].toString()
    }

    fun hasIndex(row: Int, column: Int): Boolean {
        return row >= 0 && row < rows.size && column >= 0 && rows[row].length > column
    }

    fun getAdjacents(row: Int, column: Int): List<String> {
        val right = IntRange(column + 1, column + 1).map { Pair(row, it) }
        val left = (max(column - 1, 0) downTo max(column - 1, 0)).map { Pair(row, it) }
        val up = (max(row -1, 0) downTo max(row - 1, 0)).map { Pair(it, column) }
        val down = IntRange(row + 1, row + 1).map { Pair(it, column ) }
        val rightUp = right.zip(up).map { Pair(it.second.first, it.first.second) }
        val rightDown = right.zip(down).map { Pair(it.second.first, it.first.second) }
        val leftUp = left.zip(up).map { Pair(it.second.first, it.first.second) }
        val leftDown = left.zip(down).map { Pair(it.second.first, it.first.second) }

        val allTogether = listOf(right, left, up, down, rightUp, rightDown, leftUp, leftDown)
            .flatten()
            .filter { !(it.first == row && it.second == column) }
            .distinct()

        return allTogether
            .filter { hasIndex(it.first, it.second) }
            .map { getIndex(it.first, it.second)!! }
    }

    fun getFirstVisibles(row: Int, column: Int): List<String> {
        val right = IntRange(column + 1, getColumnSize()).map { Pair(row, it) }.filter { !(it.first == row && it.second == column) }
        val left = (max(column - 1, 0) downTo 0).map { Pair(row, it) }.filter { !(it.first == row && it.second == column) }
        val up = (max(row -1, 0) downTo 0).map { Pair(it, column) }.filter { !(it.first == row && it.second == column) }
        val down = IntRange(row + 1, getRowSize()).map { Pair(it, column ) }.filter { !(it.first == row && it.second == column) }
        val rightUp = right.zip(up).map { Pair(it.second.first, it.first.second) }.filter { !(it.first == row && it.second == column) }
        val rightDown = right.zip(down).map { Pair(it.second.first, it.first.second) }.filter { !(it.first == row && it.second == column) }
        val leftUp = left.zip(up).map { Pair(it.second.first, it.first.second) }.filter { !(it.first == row && it.second == column) }
        val leftDown = left.zip(down).map { Pair(it.second.first, it.first.second) }.filter { !(it.first == row && it.second == column) }

        val firsts = mutableListOf<Pair<Int, Int>>()

        val listOfLists = listOf(right, left, up, down, rightUp, rightDown, leftUp, leftDown)

        listOfLists.forEach { list ->
            for (point in list) {
                if (isNotFloor(point.first, point.second)) {
                    firsts.add(point)
                    break
                }
            }
        }

        val filteredFirsts = firsts
            .filter { !(it.first == row && it.second == column) }
            .filter { hasIndex(it.first, it.second) }
            .map { getIndex(it.first, it.second)!! }

        return filteredFirsts
    }

}